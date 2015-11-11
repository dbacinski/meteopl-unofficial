package pl.dariuszbacinski.meteo.diagram.viewmodel;

import android.databinding.ObservableArrayList;

import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;
import pl.dariuszbacinski.meteo.BR;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.diagram.model.Diagram;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel.Legend;
import pl.dariuszbacinski.meteo.location.Location;
import rx.Observable;
import rx.functions.Func1;

public class DiagramPagerViewModel {

    public ItemView itemView = ItemView.of(BR.item, R.layout.list_item_diagram);

    public List<DiagramItemViewModel> items;

    public BindingViewPagerAdapter.PageTitles<DiagramItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<DiagramItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, DiagramItemViewModel item) {
            return items.get(position).getTitle();
        }
    };
    private Legend legendItem;

    public DiagramPagerViewModel(List<Location> locations, Legend legendItem) {
        setItems(Observable.from(locations).map(new Func1<Location, DiagramItemViewModel>() {
            @Override
            public DiagramItemViewModel call(Location location) {
                Diagram diagram = new Diagram(location);
                return new DiagramItemViewModel(diagram.getTitle(), diagram.getDiagramLink());
            }
        }).toList().toBlocking().single());
        this.legendItem = legendItem;
    }

    public boolean addLegend() {
        if (!(items.get(items.size() - 1) instanceof Legend)) {
            items.add(legendItem);
            return true;
        }

        return false;
    }

    public int getCount() {
        return items.size();
    }

    public void setItems(List<DiagramItemViewModel> items) {
        ObservableArrayList<DiagramItemViewModel> diagramItemViewModels = new ObservableArrayList<>();
        diagramItemViewModels.addAll(items);
        this.items = diagramItemViewModels;
    }
}
