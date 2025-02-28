package service;

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

    public void checkAttendance(Function function, AttendanceBook attendanceBook) {
        if (function == Function.CHECK_ATTENDANCE) {
            String name = inputView.askNameToCheckAttendance();
            // 이름 검증 추가

            LocalTime time = LocalTime.parse(inputView.askTimeToCheckAttendance());
            // 시간 검증 추가

            CheckAttendanceResponse response = attendanceBook.checkAttendance(name, LocalDate.now(), time);
            outputView.displayCheckAttendanceResult(response);
        }
    }

    public void modifyAttendance(Function function, AttendanceBook attendanceBook) {
        if (function == Function.MODIFY_ATTENDANCE) {
            String name = inputView.askNameToModifyAttendance();
            // 이름 검증 추가

            String day = inputView.askDateToModifyAttendance();
            // 날짜 검증 추가

            LocalDate date = LocalDate.of(2024, 12, Integer.parseInt(day));
            LocalTime time = LocalTime.parse(inputView.askTimeToModifyAttendance());

            ModifyAttendanceResponse response = attendanceBook.modifyAttendance(name, date, time);
            outputView.displayModifyAttendanceResult(response);
        }
    }

    public void checkAttendanceRecord(Function function, AttendanceBook attendanceBook) {
        if (function == Function.CHECK_ATTENDANCE_RECORD_BY_CREW_NAME) {
            String name = inputView.askNameToCheckAttendanceRecord();
            // 이름 검증 추가

            List<CheckAttendanceRecordResponse> responses = attendanceBook.checkAttendanceRecord(name);
            PenaltyResponse response = PenaltyStatus.judgeCrewAttendanceRecord(responses);

            outputView.displayCheckAttendanceRecord(name, responses);
            outputView.displayCheckAttendanceRecordResult(response);
        }
    }

    public void checkPenaltyCrews(Function function, AttendanceBook attendanceBook) {
        if (function == Function.CHECK_PENALTY_CREWS) {
            List<PenaltyCrewResponse> responses = attendanceBook.checkPenaltyCrew();

            outputView.displayCheckPenaltyCrewResult(responses);
        }
    }
}