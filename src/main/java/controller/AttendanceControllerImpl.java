package controller;

import domain.AttendanceBook;
import domain.AttendanceFactory;
import dto.requeset.AttendRequest;
import dto.requeset.AttendanceModifyRequest;
import dto.requeset.AttendanceResultFindRequest;
import util.dataTimeProvider.DateProvider;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.time.LocalDateTime;

public class AttendanceControllerImpl implements AttendanceController {
    
    private final DateProvider dateProvider;
    private final InputView inputView;
    private final OutputView outputView;
    
    public AttendanceControllerImpl(DateProvider dateProvider, InputView inputView, OutputView outputView) {
        this.dateProvider = dateProvider;
        this.inputView = inputView;
        this.outputView = outputView;
    }
    
    @Override
    public void run() throws IOException {
        AttendanceBook attendanceBook = AttendanceFactory.createAttendanceBook();
        boolean continueFlag = true;
        while (continueFlag) {
            switch (inputView.inputDecision()) {
                case "1":
                    AttendRequest attendRequest = inputView.getAttendRequest();
                    var attendResult = attendanceBook.addAttendance(attendRequest.name(), LocalDateTime.of(dateProvider.now(), attendRequest.attendTime()));
                    outputView.handleAttendResult(attendResult);
                    break;
                case "2":
                    AttendanceModifyRequest modifyRequest = inputView.getAttendanceModifyRequest();
                    var memberAttendanceModifyResult = attendanceBook.editAttendance(modifyRequest.name(), modifyRequest.targetDate(), modifyRequest.modifyTo());
                    outputView.handleAttendanceModifyResult(memberAttendanceModifyResult);
                    break;
                case "3":
                    AttendanceResultFindRequest resultFindRequest = inputView.getAttendanceResultFindRequest();
                    var attendanceResult = attendanceBook.getAttendanceResult(resultFindRequest.name());
                    outputView.handleMemberAttendanceResult(attendanceResult);
                    break;
                case "4":
                    var expelMeasurementResults = attendanceBook.checkExpelWarnings();
                    outputView.handleExpelMeasurementResults(expelMeasurementResults);
                    break;
                case "Q":
                    continueFlag = false;
                    break;
                default:
                    outputView.handleMissDecision();
            }
        }
    }
}
