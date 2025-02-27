package attendance.controller;

import attendance.configuration.ApplicationConfiguration;
import attendance.domain.AttendanceSystem;
import attendance.domain.initializer.AttendanceSystemInitializer;
import attendance.domain.record.AttendanceRecord;
import attendance.dto.AttendanceState;
import attendance.dto.RecordUpdateResult;
import attendance.exception.ExceptionMessage;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final AttendanceSystem attendanceSystem;
    private final AttendanceSystemInitializer initializer;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(ApplicationConfiguration configuration) {
        attendanceSystem = configuration.getAttendanceSystem();
        inputView = configuration.getInputView();
        outputView = configuration.getOutputView();
        initializer = configuration.getInitializer();
        initializer.initialize();
    }

    public void run() {
        while (true) {
            try {
                LocalDateTime now = LocalDateTime.now();
                MenuCommand menuCommand = inputView.readMenuCommand(now);
                if (menuCommand == MenuCommand.FIRST) {
                    addAttendanceRecord(now);
                }
                if (menuCommand == MenuCommand.SECOND) {
                    updateAttendanceRecord(now);
                }
                if (menuCommand == MenuCommand.THIRD) {
                    findRecordsInMonth(now);
                }
                if (menuCommand == MenuCommand.FOURTH) {
                    findRiskCrew(now);
                }
                if (menuCommand == MenuCommand.QUIT) {
                    return;
                }
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addAttendanceRecord(LocalDateTime now) {
        String nickname = inputView.readNickname();
        validateRegisteredCrew(nickname);
        LocalTime arrivalTime = inputView.readArrivalTime();
        LocalDateTime arrivalDateTime = LocalDateTime.of(now.toLocalDate(), arrivalTime);
        AttendanceRecord record = attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime);
        outputView.printRecord(record);
    }

    private void updateAttendanceRecord(LocalDateTime now) {
        String nickname = inputView.readNicknameForUpdate();
        validateRegisteredCrew(nickname);
        int dayForUpdate = inputView.readDayForUpdate();
        LocalTime newTime = inputView.readArrivalTimeForUpdate();
        LocalDate dateForUpdate = now.withDayOfMonth(dayForUpdate).toLocalDate();
        RecordUpdateResult updateResult = attendanceSystem.updateAttendance(nickname, dateForUpdate, newTime);
        outputView.printRecordUpdateResult(updateResult);
    }

    private void findRecordsInMonth(LocalDateTime now) {
        String nickname = inputView.readNickname();
        validateRegisteredCrew(nickname);
        List<AttendanceRecord> records = attendanceSystem.findRecordsInMonth(nickname, now.toLocalDate());
        AttendanceState state = attendanceSystem.calculateAttendanceStateInMonth(nickname, now.toLocalDate());
        outputView.printRecordSearchResult(records);
        outputView.printAttendanceState(state);
    }

    private void findRiskCrew(LocalDateTime now) {
        List<AttendanceState> states = attendanceSystem.findRiskCrew(now.toLocalDate());
        outputView.printRiskCrews(states);
    }

    private void validateRegisteredCrew(String nickname) {
        boolean isNotRegistered = !attendanceSystem.checkRegisteredCrew(nickname);
        if (isNotRegistered) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CREW.getMessage());
        }
    }
}
