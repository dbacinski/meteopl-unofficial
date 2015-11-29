package pl.dariuszbacinski.meteo.component.log;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import io.fabric.sdk.android.Fabric;
import pl.dariuszbacinski.meteo.diagram.viewmodel.DiagramItemViewModel;


public class AnswersLogger {
    private static final String DIAGRAM = "diagram";

    public void logDiagramView(DiagramItemViewModel diagramItemViewModel) {
        if (Fabric.isInitialized()) {
            Answers.getInstance().logContentView(new ContentViewEvent().putContentId(diagramItemViewModel.getId().toString()).putContentName(diagramItemViewModel.getTitle()).putContentType(DIAGRAM));
        }
    }
}
