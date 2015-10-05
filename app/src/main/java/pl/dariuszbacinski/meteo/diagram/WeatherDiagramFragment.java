package pl.dariuszbacinski.meteo.diagram;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import pl.dariuszbacinski.meteo.R;

public class WeatherDiagramFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private DiagramLinkProvider diagramLinkProvider = new DiagramLinkProvider();

    public static WeatherDiagramFragment newInstance(DiagramCoordinates sectionNumber) {
        WeatherDiagramFragment fragment = new WeatherDiagramFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        TextView selectionLabel = (TextView) rootView.findViewById(R.id.section_label);
        DiagramCoordinates diagramCoordinates = getArguments().getParcelable(ARG_SECTION_NUMBER);
        selectionLabel.setText(diagramCoordinates.getDate());
        ImageView weatherDiagram = (ImageView) rootView.findViewById(R.id.weather_diagram);
        Picasso.with(getActivity()).load(diagramLinkProvider.createDiagramLink(diagramCoordinates)).memoryPolicy(MemoryPolicy.NO_STORE).fit().centerInside().noFade().into(weatherDiagram);
        return rootView;
    }
}
