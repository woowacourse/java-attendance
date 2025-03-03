package attendance;

import java.io.Closeable;

import attendance.domain.AttendanceStatus;
import attendance.domain.SanctionLevel;
import attendance.view.InputView;
import attendance.view.OutputView;
import attendance.view.converter.LevelTextConverter;
import attendance.view.converter.StatusTextConverter;

public class DependencyConfig implements Closeable {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceController controller;

    public DependencyConfig() {
        inputView = new InputView();
        outputView = new OutputView();

        initializeDomainConverters();

        controller = new AttendanceController(inputView, outputView);
    }

    private void initializeDomainConverters() {
        AttendanceStatus.setConverter(new StatusTextConverter());
        SanctionLevel.setConverter(new LevelTextConverter());
    }

    public AttendanceController getController() {
        return controller;
    }

    @Override
    public void close() {
        inputView.close();
    }
}
