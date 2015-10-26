package pl.dariuszbacinski.meteo.diagram;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.dariuszbacinski.meteo.databinding.FragmentDiagramBinding;

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
        DiagramCoordinates diagramCoordinates = getArguments().getParcelable(ARG_SECTION_NUMBER);
        String diagramLink = diagramLinkProvider.createDiagramLink(diagramCoordinates);
        FragmentDiagramBinding diagramBinding = FragmentDiagramBinding.inflate(inflater, container, false);
        diagramBinding.setImageUrl(diagramLink);
        return diagramBinding.getRoot();
    }
}
