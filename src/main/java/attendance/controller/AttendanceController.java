package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendance;
import attendance.model.AttendanceStartTime;
import attendance.model.AttendanceType;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.model.Crew;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
        System.out.printf("%s (%s)%n",
                attendanceDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                displayAttendanceType(calculateAttendanceType(attendanceDateTime))
        );
    }

    private void doUpdateAttendance(LocalDateTime now) {
        String nickname = inputView.inputNicknameForUpdateAttendance();
        attendances.validateExistNickname(nickname);
        LocalDateTime updateDateTime = getUpdateDateTime(now);
        boolean isFutureDate = updateDateTime.toLocalDate().isAfter(now.toLocalDate());
        if (isFutureDate) {
            throw new IllegalArgumentException("미래 날짜의 출석을 수정할 수 없습니다.");
        }
        Crew crew = new Crew(nickname);
        Optional<Attendance> optionalAttendance = attendances.findByCrewAndDate(crew, updateDateTime.toLocalDate());
        attendances.update(new Attendance(crew, updateDateTime));

        if (optionalAttendance.isPresent()) {
            Attendance attendance = optionalAttendance.get();
            System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                    attendance.getDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                    displayAttendanceType(calculateAttendanceType(attendance.getDateTime())),
                    updateDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    displayAttendanceType(calculateAttendanceType(updateDateTime))
            );
            return;
        }
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                updateDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--")),
                displayAttendanceType(null),
                updateDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                displayAttendanceType(calculateAttendanceType(updateDateTime))
        );
    }

    private LocalDateTime getUpdateDateTime(LocalDateTime now) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        String rawTimeForUpdate = inputView.inputTimeForUpdateAttendance();
        LocalDate updateDate = LocalDate.of(now.getYear(), now.getMonth(), targetUpdateDate);
        return LocalDateTime.of(updateDate, toLocalTime(rawTimeForUpdate));
    }

    private String displayAttendanceType(AttendanceType type) {
        if (type == AttendanceType.OK) {
            return "출석";
        }
        if (type == AttendanceType.LATE) {
            return "지각";
        }
        return "결석";
    }

    private AttendanceType calculateAttendanceType(LocalDateTime dateTime) {
        LocalTime startTime = AttendanceStartTime.findDayOfWeek(dateTime.getDayOfWeek());
        return AttendanceType.judge(startTime, dateTime.toLocalTime());
    }

    private LocalTime toLocalTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }
}
