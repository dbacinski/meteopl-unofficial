package pl.dariuszbacinski.meteo.diagram;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import pl.dariuszbacinski.meteo.R;
import uk.co.senab.photoview.PhotoView;

public class DiagramFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private DiagramLinkProvider diagramLinkProvider = new DiagramLinkProvider();

    public static DiagramFragment newInstance(DiagramCoordinates sectionNumber) {
        DiagramFragment fragment = new DiagramFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        DiagramCoordinates diagramCoordinates = getArguments().getParcelable(ARG_SECTION_NUMBER);
        PhotoView weatherDiagram = (PhotoView) rootView.findViewById(R.id.weather_diagram);
        weatherDiagram.setScaleLevels(1f, 1.5f, 2f);
        Picasso.with(getActivity()).load(diagramLinkProvider.createDiagramLink(diagramCoordinates)).memoryPolicy(MemoryPolicy.NO_STORE).fit().centerInside().noFade().into(weatherDiagram);
        return rootView;
    }
}