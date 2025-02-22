package controller;

import service.AttendanceModifyService;
import service.dto.AttendanceModifyResponse;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

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
        LocalDate modifyDate = inputView.readModifyDate();
        LocalTime modifyTime = inputView.readModifyTime();
        AttendanceModifyResponse response = modifyService.modify(
                crewName,
                modifyDate,
                modifyTime
        );
        outputView.printModifyResult(response);
    }
}
