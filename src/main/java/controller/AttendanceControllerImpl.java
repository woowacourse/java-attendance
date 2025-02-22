package controller;

import domain.AttendanceBook;
import dto.requeset.AttendanceBookDecision;
import io.AttendanceFactory;
import io.view.InputView;
import io.view.OutputView;
import util.dataTimeProvider.DateProvider;

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
        while (true) {
            AttendanceBookDecision decision = inputView.inputDecision();
            
            if (decision == AttendanceBookDecision.출석) {
                handleAttend(attendanceBook);
                continue;
            }
            if (decision == AttendanceBookDecision.출석_기록_수정) {
                handleAttendanceModify(attendanceBook);
                continue;
            }
            if (decision == AttendanceBookDecision.출석_기록_확인) {
                handleAttendanceResultFind(attendanceBook);
                continue;
            }
            if (decision == AttendanceBookDecision.제적_위험자_확인) {
                handleExpelWarnings(attendanceBook);
                continue;
            }
            if (decision == AttendanceBookDecision.종료) {
                break;
            }
        }
    }
    
    private void handleAttend(AttendanceBook attendanceBook) {
        var attendRequest = inputView.getAttendRequest();
        var attendResult = attendanceBook.addAttendance(attendRequest.name(), LocalDateTime.of(dateProvider.getCurrentDate(), attendRequest.attendTime()));
        outputView.handleAttendResult(attendResult);
    }
    
    private void handleAttendanceModify(AttendanceBook attendanceBook) {
        var modifyRequest = inputView.getAttendanceModifyRequest();
        var memberAttendanceModifyResult = attendanceBook.editAttendance(modifyRequest.name(), modifyRequest.targetDate(), modifyRequest.modifyTo());
        outputView.handleAttendanceModifyResult(memberAttendanceModifyResult);
    }
    
    private void handleAttendanceResultFind(AttendanceBook attendanceBook) {
        var resultFindRequest = inputView.getAttendanceResultFindRequest();
        var attendanceResult = attendanceBook.getAttendanceResult(resultFindRequest.name());
        outputView.handleMemberAttendanceResult(attendanceResult);
    }
    
    private void handleExpelWarnings(AttendanceBook attendanceBook) {
        var expelMeasurementResults = attendanceBook.createExpelWarnings();
        outputView.handleExpelMeasurementResults(expelMeasurementResults);
    }
}
