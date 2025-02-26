package attendance.controller;

import attendance.configuration.ApplicationConfiguration;
import attendance.domain.AttendanceSystem;
import attendance.domain.dto.AttendanceState;
import attendance.record.AttendanceRecord;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final AttendanceSystem attendanceSystem;
    private final InputView inputView;

    public AttendanceController(ApplicationConfiguration configuration) {
        attendanceSystem = configuration.getAttendanceSystem();
        inputView = configuration.getInputView();
    }

    public void run() {
        while (true) {
            try {
                LocalDateTime now = LocalDateTime.now();
                MenuCommand menuCommand = inputView.readMenuCommand();
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
        LocalTime arrivalTime = inputView.readArrivalTime();
        LocalDateTime arrivalDateTime = LocalDateTime.of(now.toLocalDate(), arrivalTime);
        attendanceSystem.addAttendanceRecord(nickname, arrivalDateTime);
    }

    private void updateAttendanceRecord(LocalDateTime now) {
        String nickname = inputView.readNicknameForUpdate();
        int dayForUpdate = inputView.readDayForUpdate();
        LocalTime newTime = inputView.readArrivalTimeForUpdate();
        LocalDate dateForUpdate = now.withDayOfMonth(dayForUpdate).toLocalDate();
        attendanceSystem.updateAttendance(nickname, dateForUpdate, newTime);
    }

    private void findRecordsInMonth(LocalDateTime now) {
        String nickname = inputView.readNickname();
        List<AttendanceRecord> records = attendanceSystem.findRecordsInMonth(nickname, now.toLocalDate());
        AttendanceState state = attendanceSystem.calculateAttendanceStateInMonth(nickname, now.toLocalDate());
    }

    private void findRiskCrew(LocalDateTime now) {
        List<AttendanceState> states = attendanceSystem.findRiskCrew(now.toLocalDate());
    }
}
