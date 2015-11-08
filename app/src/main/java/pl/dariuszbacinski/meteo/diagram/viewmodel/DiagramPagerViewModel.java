package pl.dariuszbacinski.meteo.diagram.viewmodel;

import java.util.List;

import lombok.AccessLevel;
import lombok.Setter;
import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;
import pl.dariuszbacinski.meteo.BR;
import pl.dariuszbacinski.meteo.R;
import pl.dariuszbacinski.meteo.diagram.model.Diagram;
import pl.dariuszbacinski.meteo.location.Location;
import rx.Observable;
import rx.functions.Func1;

public class DiagramPagerViewModel {

    public ItemView itemView = ItemView.of(BR.item, R.layout.list_item_diagram);
    @Setter(AccessLevel.PRIVATE)
    public List<DiagramItemViewModel> items;

    public BindingViewPagerAdapter.PageTitles<DiagramItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<DiagramItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, DiagramItemViewModel item) {
            return items.get(position).getTitle();
        }
    };

    public DiagramPagerViewModel(List<Location> locations) {
        setItems(Observable.from(locations).map(new Func1<Location, DiagramItemViewModel>() {
            @Override
            public DiagramItemViewModel call(Location location) {
                Diagram diagram = new Diagram(location);
                return new DiagramItemViewModel(diagram.getTitle(), diagram.getDiagramLink());
            }
        }).toList().toBlocking().single());
    }

    public int getCount() {
        return items.size();
    }

}
