package controller;

import domain.AttendanceBook;
import domain.AttendanceFactory;
import domain.CrewName;
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
                    registerAttendance(attendanceBook);
                    break;
                case "2":
                    modifyAttendance(attendanceBook);
                    break;
                case "3":
                    checkCrewAttendacne(attendanceBook);
                    break;
                case "4":

                    checkRiskExpelled(attendanceBook);
                    break;
                case "Q":
                    continueFlag = false;
                    break;
                default:
                    outputView.handleMissDecision();
            }
        }
    }

    private void registerAttendance(AttendanceBook attendanceBook) {
        AttendRequest attendRequest = inputView.getAttendRequest();
        var attendResult = attendanceBook.addAttendance(attendRequest.name(), LocalDateTime.of(dateProvider.now(), attendRequest.attendTime()));
        outputView.handleAttendResult(attendResult);
    }

    private void modifyAttendance(AttendanceBook attendanceBook) {
        AttendanceModifyRequest modifyRequest = inputView.getAttendanceModifyRequest();
        var memberAttendanceModifyResult = attendanceBook.editAttendance(modifyRequest.name(), modifyRequest.targetDate(), modifyRequest.modifyTo());
        outputView.handleAttendanceModifyResult(memberAttendanceModifyResult);
    }

    private void checkCrewAttendacne(AttendanceBook attendanceBook) {
        AttendanceResultFindRequest resultFindRequest = inputView.getAttendanceResultFindRequest();
        var attendanceResult = attendanceBook.getAttendanceResult(resultFindRequest.name());
        outputView.handleMemberAttendanceResult(attendanceResult);
    }

    private void checkRiskExpelled(AttendanceBook attendanceBook) {
        var expelMeasurementResults = attendanceBook.checkExpelWarnings();
        outputView.handleExpelMeasurementResults(expelMeasurementResults);
    }
}
