package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendance;
import attendance.model.AttendanceStartTime;
import attendance.model.AttendanceTimeline;
import attendance.model.AttendanceTimeline.AttendanceLog;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.model.Crew;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

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
            if (command == Command.ATTENDANCE_TIMELINE) {
                doAttendanceTimeline(now);
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

    private AttendanceType calculateAttendanceType(LocalDateTime dateTime) {
        LocalTime startTime = AttendanceStartTime.findDayOfWeek(dateTime.getDayOfWeek());
        return AttendanceType.judge(startTime, dateTime.toLocalTime());
    }

    private void doAttendanceTimeline(LocalDateTime now) {
        String nickname = inputView.inputNickname();
        attendances.validateExistNickname(nickname);

        Crew crew = new Crew(nickname);
        Set<Attendance> attendanceHistory = attendances.findAllByCrewAndMonth(crew, now.getMonth());
        AttendanceTimeline attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(
                attendanceHistory, now.toLocalDate());
        System.out.printf("이번달 %s의 출석 기록입니다.%n%n", nickname);
        for(AttendanceLog attendanceLog: attendanceTimeline.attendanceLogs()) {
            if(attendanceLog.time() == null) {
                System.out.printf("%s --:-- (%s)%n",
                        attendanceLog.date().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                        displayAttendanceType(attendanceLog.attendanceType()));
                continue;
            }
            System.out.printf("%s %s (%s)%n",
                    attendanceLog.date().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                    attendanceLog.time().format(DateTimeFormatter.ofPattern("HH:mm")),
                    displayAttendanceType(attendanceLog.attendanceType()));
        }

        System.out.printf("%n출석: %d%n지각: %d%n결석: %d%n",
                attendanceTimeline.countByAttendanceType(AttendanceType.OK),
                attendanceTimeline.countByAttendanceType(AttendanceType.LATE),
                attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE));

        AttendanceWarningLevel level = AttendanceWarningLevel.judge(
                attendanceTimeline.countByAttendanceType(AttendanceType.LATE),
                attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE)
        );
        if (level != AttendanceWarningLevel.CLEAN) {
            System.out.printf("%n%s 대상자입니다.", displayAttendanceWarningLevel(level));
        }
    }

    private String displayAttendanceWarningLevel(AttendanceWarningLevel level) {
        if (level == AttendanceWarningLevel.WARNING) {
            return "경고";
        }
        if (level == AttendanceWarningLevel.MEETING) {
            return "면담";
        }
        return "제적";
    }

    private LocalTime toLocalTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
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
}
