package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendance;
import attendance.model.AttendanceStartTime;
import attendance.model.AttendanceTimeline;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.model.Crew;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Attendances attendances;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        attendances = new AttendancesFactory().initialize();
    }

    public void run() {
        boolean isQuit;
        do {
            LocalDateTime now = LocalDateTime.now();
            isQuit = start(now);
        } while (isQuit);
    }

    private boolean start(LocalDateTime now) {
        Command command = null;
        try {
            command = Command.from(inputView.inputCommand(now.toLocalDate()));
            logic(command, now);
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        return command != Command.QUIT;
    }

    private void logic(Command command, LocalDateTime now) {
        if (command == Command.ATTENDANCE) {
            doAttendance(now);
        }
        if (command == Command.ATTENDANCE_UPDATE) {
            doUpdateAttendance(now);
        }
        if (command == Command.ATTENDANCE_TIMELINE) {
            doAttendanceTimeline(now);
        }
        if (command == Command.EMERGENCY_CHECK) {
            doEmergencyCheck(now);
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
        outputView.printCheckAttendance(attendanceDateTime, calculateAttendanceType(attendanceDateTime));
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
        Optional<Attendance> beforeAttendance = findAttendanceByCrewAndDate(crew, updateDateTime);
        attendances.update(new Attendance(crew, updateDateTime));
        Optional<Attendance> afterAttendance = findAttendanceByCrewAndDate(crew, updateDateTime);
        printModifiedAttendance(beforeAttendance, afterAttendance);
    }

    private Optional<Attendance> findAttendanceByCrewAndDate(Crew crew, LocalDateTime updateDateTime) {
        return attendances.findByCrewAndDate(crew, updateDateTime.toLocalDate());
    }

    private LocalDateTime getUpdateDateTime(LocalDateTime now) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        String rawTimeForUpdate = inputView.inputTimeForUpdateAttendance();
        LocalDate updateDate = LocalDate.of(now.getYear(), now.getMonth(), targetUpdateDate);
        return LocalDateTime.of(updateDate, toLocalTime(rawTimeForUpdate));
    }

    private void printModifiedAttendance(Optional<Attendance> beforeAttendance, Optional<Attendance> afterAttendance) {
        if (beforeAttendance.isPresent()) {
            outputView.printModifiedAttendance(beforeAttendance.get(), afterAttendance.get(),
                    calculateAttendanceType(beforeAttendance.get().getDateTime()),
                    calculateAttendanceType(afterAttendance.get().getDateTime()));
            return;
        }
        outputView.printModifiedAttendance(null, afterAttendance.get(),
                AttendanceType.ABSENCE, calculateAttendanceType(afterAttendance.get().getDateTime()));
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
        outputView.printAttendanceTimelineInMonth(nickname, attendanceTimeline);
        printCountOfAttendanceType(attendanceTimeline);
        outputView.printWarningLevel(judgeWarningLevel(attendanceTimeline));
    }

    private void printCountOfAttendanceType(AttendanceTimeline attendanceTimeline) {
        int okCount = attendanceTimeline.countByAttendanceType(AttendanceType.OK);
        int lateCount = attendanceTimeline.countByAttendanceType(AttendanceType.LATE);
        int absenceCount = attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE);
        outputView.printCountOfAttendanceType(okCount, lateCount, absenceCount);
    }

    private AttendanceWarningLevel judgeWarningLevel(AttendanceTimeline attendanceTimeline) {
        return AttendanceWarningLevel.judge(
                attendanceTimeline.countByAttendanceType(AttendanceType.LATE),
                attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE)
        );
    }

    private void doEmergencyCheck(LocalDateTime now) {
        Map<Crew, Set<Attendance>> allAttendanceHistory = attendances.findAllByMonth(now.getMonth());
        List<CrewAttendanceSummary> crewAttendanceSummaries = createAllCrewAttendanceSummaries(
                now, allAttendanceHistory);
        List<CrewAttendanceSummary> sortedList = sortCrewAttendanceSummaries(crewAttendanceSummaries);
        outputView.printEmergencyCrews(sortedList);
    }

    private List<CrewAttendanceSummary> createAllCrewAttendanceSummaries(LocalDateTime now,
                                                                         Map<Crew, Set<Attendance>> allAttendanceHistory) {
        List<CrewAttendanceSummary> crewAttendanceSummaries = new ArrayList<>();
        for (Crew crew : allAttendanceHistory.keySet()) {
            AttendanceTimeline attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(
                    allAttendanceHistory.get(crew), now.toLocalDate());
            int lateCount = attendanceTimeline.countByAttendanceType(AttendanceType.LATE);
            int absenceCount = attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE);
            AttendanceWarningLevel level = AttendanceWarningLevel.judge(
                    lateCount,
                    absenceCount
            );
            crewAttendanceSummaries.add(new CrewAttendanceSummary(crew, lateCount, absenceCount, level));
        }
        return crewAttendanceSummaries;
    }

    private List<CrewAttendanceSummary> sortCrewAttendanceSummaries(
            List<CrewAttendanceSummary> crewAttendanceSummaries) {
        return crewAttendanceSummaries.stream()
                .sorted(Comparator
                        .comparing(CrewAttendanceSummary::level)
                        .reversed()
                        .thenComparing(summary -> summary.absenceCount() + (summary.lateCount() / 3),
                                Comparator.reverseOrder())
                        .thenComparing(summary -> summary.crew().getNickname())
                )
                .toList();
    }

    public record CrewAttendanceSummary(
            Crew crew,
            int lateCount,
            int absenceCount,
            AttendanceWarningLevel level
    ) {
    }

    private LocalTime toLocalTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }
}
