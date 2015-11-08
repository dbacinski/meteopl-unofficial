package pl.dariuszbacinski.meteo.diagram.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.dariuszbacinski.meteo.WeatherApplication;
import pl.dariuszbacinski.meteo.databinding.FragmentDiagramBinding;
import pl.dariuszbacinski.meteo.diagram.model.Diagram;

public class DiagramFragment extends Fragment {

    private static final String ARG_DIAGRAM_LINK = "diagram_link";

    public static DiagramFragment newInstance(Diagram diagram) {
        DiagramFragment fragment = new DiagramFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DIAGRAM_LINK, diagram.getDiagramLink());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDiagramBinding diagramBinding = FragmentDiagramBinding.inflate(inflater, container, false);
        String diagramLink = getArguments().getString(ARG_DIAGRAM_LINK);
        diagramBinding.setImageUrl(diagramLink);
        return diagramBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WeatherApplication.getRefWatcher(getActivity()).watch(this);
    }
}
