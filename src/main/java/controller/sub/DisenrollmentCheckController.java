package controller.sub;

import service.DisenrollmentCheckService;
import service.dto.DisenrollmentCheckResponse;
import view.OutputView;

import java.util.List;

public class DisenrollmentCheckController implements SubController {
    private final OutputView outputView;
    private final DisenrollmentCheckService disenrollmentCheckService;

    public DisenrollmentCheckController(
            OutputView outputView,
            DisenrollmentCheckService disenrollmentCheckService
    ) {
        this.outputView = outputView;
        this.disenrollmentCheckService = disenrollmentCheckService;
    }

    @Override
    public void run() {
        List<DisenrollmentCheckResponse> responses = disenrollmentCheckService.getDisenrollmentCheckResult();
        outputView.printDisenrollmentCheckResult(responses);
    }
}
