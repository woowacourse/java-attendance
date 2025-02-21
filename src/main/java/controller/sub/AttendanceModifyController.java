package controller.sub;

import service.AttendanceModifyService;
import service.dto.AttendanceModifyResponse;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;

public class AttendanceModifyController implements SubController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceModifyService modifyService;


    public AttendanceModifyController(InputView inputView, OutputView outputView, AttendanceModifyService modifyService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.modifyService = modifyService;
    }

    @Override
    public void run() {
        String crewName = inputView.readName();
        int modifyDate = inputView.readModifyDate();
        LocalTime modifyTime = inputView.readModifyTime();
        AttendanceModifyResponse response = modifyService.modify(crewName,
                modifyDate,
                modifyTime.getHour(),
                modifyTime.getMinute()
        );
        outputView.printModifyResult(response);
    }
}
