package service;

import static domain.AttendanceBook.validateAlreadyAttendance;
import static domain.AttendanceBook.validateTimeIsInTheRangeOfOperation;
import static domain.AttendanceBook.validateTrainingDay;
import static utils.RetryUtils.retryUntilValid;

import domain.AttendanceBook;
import domain.PenaltyStatus;
import dto.CheckAttendanceRecordResponse;
import dto.CheckAttendanceResponse;
import dto.ModifyAttendanceResponse;
import dto.PenaltyCrewResponse;
import dto.PenaltyResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import utils.ParsingUtils;
import view.ErrorMessage;
import view.input.Function;
import view.input.InputView;
import view.output.OutputView;

public class FunctionService {
    private final OutputView outputView;
    private final InputView inputView;

    public FunctionService(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    // 기능 1
    public void checkAttendance(Function function, AttendanceBook attendanceBook) {
        if (function == Function.CHECK_ATTENDANCE) {
            validateTrainingDay(LocalDate.now(), LocalTime.of(10, 0)); // 등교일이 맞는지?
            String name = retryUntilValid(() -> inputNameToCheckAttendance(attendanceBook));

            LocalTime time = retryUntilValid(this::inputTimeToCheckAttendance);

            CheckAttendanceResponse response = attendanceBook.checkAttendance(name, LocalDate.now(), time);
            outputView.displayCheckAttendanceResult(response);
        }
    }

    private String inputNameToCheckAttendance(AttendanceBook attendanceBook) {
        String name = inputView.askNameToCheckAttendance();
        if (!attendanceBook.checkCrewExisted(name)) { // 해당 이름이 존재하는지?
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat());
        }
        validateAlreadyAttendance(attendanceBook.findCrewByName(name), LocalDate.now()); // 해당 크루가 출석을 이미 했는지?
        return name;
    }

    private LocalTime inputTimeToCheckAttendance() {
        LocalTime time = ParsingUtils.parseTimeInput(inputView.askTimeToCheckAttendance());
        validateTimeIsInTheRangeOfOperation(time); // 운영시간 인지?
        return time;
    }

    // 기능 2
    public void modifyAttendance(Function function, AttendanceBook attendanceBook) {
        if (function == Function.MODIFY_ATTENDANCE) {
            String name = retryUntilValid(() -> inputNameToModifyAttendance(attendanceBook));

            LocalDate date = retryUntilValid(() -> inputDayToModifyAttendance(attendanceBook));

            LocalTime time = retryUntilValid(this::inputTimeToModifyAttendance);

            ModifyAttendanceResponse response = attendanceBook.modifyAttendance(name, date, time);
            outputView.displayModifyAttendanceResult(response);
        }
    }

    private String inputNameToModifyAttendance(AttendanceBook attendanceBook) {
        String name = inputView.askNameToModifyAttendance();
        if (!attendanceBook.checkCrewExisted(name)) { // 해당 이름이 존재하는지?
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat());
        }
        return name;
    }

    private LocalDate inputDayToModifyAttendance(AttendanceBook attendanceBook) {
        String day = inputView.askDateToModifyAttendance();
        LocalDate date = LocalDate.of(2024, 12, Integer.parseInt(day));
        validateTrainingDay(date, LocalTime.of(10, 0)); // 등교일이 맞는지?
        attendanceBook.validateIsDateFuture(date);
        return date;
    }

    private LocalTime inputTimeToModifyAttendance() {
        LocalTime time = ParsingUtils.parseTimeInput(inputView.askTimeToModifyAttendance());
        validateTimeIsInTheRangeOfOperation(time); // 운영시간 인지?
        return time;
    }

    // 기능 3
    public void checkAttendanceRecord(Function function, AttendanceBook attendanceBook) {
        if (function == Function.CHECK_ATTENDANCE_RECORD_BY_CREW_NAME) {
            String name = retryUntilValid(() -> inputNameToCheckAttendanceRecord(attendanceBook));

            List<CheckAttendanceRecordResponse> responses = attendanceBook.checkAttendanceRecord(name);
            PenaltyResponse response = PenaltyStatus.judgeCrewAttendanceRecord(responses);

            outputView.displayCheckAttendanceRecord(name, responses);
            outputView.displayCheckAttendanceRecordResult(response);
        }
    }

    private String inputNameToCheckAttendanceRecord(AttendanceBook attendanceBook) {
        String name = inputView.askNameToCheckAttendanceRecord();
        if (!attendanceBook.checkCrewExisted(name)) { // 해당 이름이 존재하는지?
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat());
        }
        return name;
    }

    // 기능 4
    public void checkPenaltyCrews(Function function, AttendanceBook attendanceBook) {
        if (function == Function.CHECK_PENALTY_CREWS) {
            List<PenaltyCrewResponse> responses = attendanceBook.checkPenaltyCrew();

            outputView.displayCheckPenaltyCrewResult(responses);
        }
    }
}