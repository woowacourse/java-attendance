package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.model.Crew;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceController {

    private final InputView inputView;
    private final Attendances attendances;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
        attendances = new AttendancesFactory().initialize();
    }

    public void run() {
        LocalDateTime now = LocalDateTime.now();
        Command command = Command.from(inputView.inputCommand(now.toLocalDate()));
        try {
            if (command == Command.ATTENDANCE) {
                doAttendance(now);
            }
            if (command == Command.ATTENDANCE_UPDATE) {
                doUpdateAttendance(now);
            }
        } catch (RuntimeException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void doAttendance(LocalDateTime now) {
        String nickname = inputView.inputNickname();
        attendances.validateExistNickname(nickname);

        String rawAttendanceTime = inputView.inputAttendanceTime();
        LocalTime attendanceTime = toLocalTime(rawAttendanceTime);
        Crew crew = new Crew(nickname);
        LocalDateTime attendanceDateTime = LocalDateTime.of(now.toLocalDate(), attendanceTime);
        attendances.add(new Attendance(crew, attendanceDateTime));
        System.out.printf("%02d월 %2d일 %s요일 %02d:%02d (출석)%n",
                attendanceDateTime.getMonth().getValue(),
                attendanceDateTime.getDayOfMonth(),
                attendanceDateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                attendanceDateTime.getHour(),
                attendanceTime.getMinute());
    }

    private void doUpdateAttendance(LocalDateTime now) {
        String nickname = inputView.inputNicknameForUpdateAttendance();
        attendances.validateExistNickname(nickname);
        LocalDateTime updateDateTime = getUpdateDateTime(now);
        attendances.update(new Attendance(new Crew(nickname), updateDateTime));
    }

    private LocalDateTime getUpdateDateTime(LocalDateTime now) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        String rawTimeForUpdate = inputView.inputTimeForUpdateAttendance();
        LocalDate updateDate = LocalDate.of(now.getYear(), now.getMonth(), targetUpdateDate);
        return LocalDateTime.of(updateDate, toLocalTime(rawTimeForUpdate));
    }

    private LocalTime toLocalTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }
}
