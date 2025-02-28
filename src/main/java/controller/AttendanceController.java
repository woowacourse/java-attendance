package controller;

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
import service.CrewRegistrationService;
import view.input.Function;
import view.input.InputView;
import view.output.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewRegistrationService registration;

    public AttendanceController(InputView inputView, OutputView outputView, CrewRegistrationService registration) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.registration = registration;
    }

    public void start() {
        String filePath = "src/main/java/resources/attendances.csv";
        AttendanceBook attendanceBook = registration.registerCrews(filePath); // 초기화

        outputView.displayFunctionPrompt();
        Function function = Function.checkFunctionNumber(inputView.askFunctionSelection());

        if (function.equals(Function.CHECK_ATTENDANCE)) {
            String name = inputView.askNameToCheckAttendance();
            LocalTime time = LocalTime.parse(inputView.askTimeToCheckAttendance());
            CheckAttendanceResponse response = attendanceBook.checkAttendance(name, LocalDate.now(), time);
            outputView.displayCheckAttendanceResult(response);
        }

        if (function.equals(Function.MODIFY_ATTENDANCE)) {
            String name = inputView.askNameToModifyAttendance();
            LocalDate date = LocalDate.parse(inputView.askDateToModifyAttendance());
            LocalTime time = LocalTime.parse(inputView.askTimeToModifyAttendance());

            ModifyAttendanceResponse response = attendanceBook.modifyAttendance(name, date, time);
            outputView.displayModifyAttendanceResult(response);
        }

        if (function.equals(Function.CHECK_ATTENDANCE_RECORD_BY_CREW_NAME)) {
            String name = inputView.askNameToCheckAttendanceRecord();

            List<CheckAttendanceRecordResponse> responses = attendanceBook.checkAttendanceRecord(name);
            PenaltyResponse response = PenaltyStatus.judgeCrewAttendanceRecord(responses);

            outputView.displayCheckAttendanceRecord(name, responses);
            outputView.displayCheckAttendanceRecordResult(response);
        }

        if (function.equals(Function.CHECK_PENALTY_CREWS)) {
            List<PenaltyCrewResponse> responses = attendanceBook.checkPenaltyCrew();

            outputView.displayCheckPenaltyCrewResult(responses);
        }

        if (function.equals(Function.QUIT)) {
            System.exit(1);
        }
    }
}