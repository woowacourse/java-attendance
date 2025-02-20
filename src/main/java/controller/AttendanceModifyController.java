package controller;

import service.AttendanceModifyService;
import service.dto.AttendanceModifyResponse;
import view.InputView;
import view.OutputView;

import java.util.List;

public class AttendanceModifyController implements Controller{
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
        List<Integer> hourAndMinute = inputView.readModifyTime();
        AttendanceModifyResponse response = modifyService.modify(crewName,
                modifyDate,
                hourAndMinute.get(0),
                hourAndMinute.get(1)
        );
        outputView.printModifyResult(response);
    }
}
