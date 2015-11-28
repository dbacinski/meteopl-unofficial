package pl.dariuszbacinski.meteo.diagram.viewmodel;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import java.util.List;

import hugo.weaving.DebugLog;
import lombok.Setter;
import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;
import pl.dariuszbacinski.meteo.BR;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.diagram.model.Diagram;
import pl.dariuszbacinski.meteo.diagram.model.SelectedDiagram;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.Legend;
import pl.dariuszbacinski.meteo.location.model.Location;
import pl.dariuszbacinski.meteo.rx.Indexed;
import pl.dariuszbacinski.meteo.rx.NaturalNumbers;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

//TODO add tests
public class DiagramPagerViewModel {

    public ItemView itemView = ItemView.of(BR.item, R.layout.list_item_diagram);

    public List<DiagramItemViewModel> items;

    public PageTitles pageTitles;

    private Legend legendItem;

    public DiagramPagerViewModel(List<Location> locations, Legend legendItem) {
        items = createObservableList(Observable.from(locations).map(new Func1<Location, DiagramItemViewModel>() {
            @Override
            public DiagramItemViewModel call(Location location) {
                Diagram diagram = new Diagram(location);
                return DiagramItemViewModel.builder()
                        .title(diagram.getTitle())
                        .imageUrl(diagram.getDiagramLink())
                        .id(diagram.getId())
                        .build();
            }
        }).toList().toBlocking().single());
        pageTitles = new PageTitles(items);
        this.legendItem = legendItem;
    }

    public boolean addLegend() {
        if (!(items.get(items.size() - 1) instanceof Legend)) {
            items.add(legendItem);
            pageTitles.setItems(items);
            return true;
        }

        return false;
    }

    public int getCount() {
        return items.size();
    }

    @NonNull
    private ObservableArrayList<DiagramItemViewModel> createObservableList(List<DiagramItemViewModel> newItems) {
        ObservableArrayList<DiagramItemViewModel> observableItems = new ObservableArrayList<>();
        observableItems.addAll(newItems);
        return observableItems;
    }

    @DebugLog
    public void saveSelectedDiagramPosition(int position) {
        if (!items.isEmpty()) {
            new SelectedDiagram(getItemId(position)).saveSingle();
        }
    }

    @DebugLog
    public int loadSelectedDiagramPosition() {
        return SelectedDiagram.loadSingle().map(new DiagramToPositionFunction(items)).orElse(0);
    }

    private Long getItemId(int position) {
        return items.get(position).getId();
    }

    public static class PageTitles implements BindingViewPagerAdapter.PageTitles<DiagramItemViewModel> {

        @Setter
        private List<DiagramItemViewModel> items;

        public PageTitles(List<DiagramItemViewModel> items) {
            this.items = items;
        }

        @Override
        public CharSequence getPageTitle(int position, DiagramItemViewModel item) {
            return items.get(position).getTitle();
        }
    }

    private static class DiagramToPositionFunction implements Func1<SelectedDiagram, Integer> {

        List<DiagramItemViewModel> items;

        public DiagramToPositionFunction(List<DiagramItemViewModel> items) {
            this.items = items;
        }

        @Override
        public Integer call(final SelectedDiagram selectedDiagram) {
            return Observable.from(items).zipWith(new NaturalNumbers(), new Func2<DiagramItemViewModel, Integer, Indexed<DiagramItemViewModel>>() {
                @Override
                public Indexed<DiagramItemViewModel> call(DiagramItemViewModel diagramItemViewModel, Integer index) {
                    return new Indexed<>(index, index, diagramItemViewModel);
                }
            }).filter(new Func1<Indexed<DiagramItemViewModel>, Boolean>() {
                @Override
                public Boolean call(Indexed<DiagramItemViewModel> diagramItemViewModel) {
                    return diagramItemViewModel.getValue().getId().equals(selectedDiagram.getLocationId());
                }
            }).map(new Func1<Indexed<DiagramItemViewModel>, Integer>() {
                @Override
                public Integer call(Indexed<DiagramItemViewModel> diagramItemViewModelIndexed) {
                    return diagramItemViewModelIndexed.getIndex();
                }
            }).toBlocking().firstOrDefault(0);
        }
    }
}
