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
import java.time.DateTimeException;
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
import java.util.function.Function;

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
        } catch (DateTimeException e) {
            outputView.printDateTimeErrorMessage();
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
        Attendance attendance = createAttendance(now);
        attendances.add(attendance);
        outputView.printCheckAttendance(attendance.getDateTime(), calculateAttendanceType(attendance.getDateTime()));
    }

    private Attendance createAttendance(LocalDateTime now) {
        Crew crew = new Crew(inputExistNickname());
        LocalDateTime attendanceDateTime = inputAttendanceDateTime(now);
        return new Attendance(crew, attendanceDateTime);
    }

    private String inputExistNickname() {
        String nickname = inputView.inputNickname();
        attendances.validateExistNickname(nickname);
        return nickname;
    }

    private LocalDateTime inputAttendanceDateTime(LocalDateTime now) {
        String rawAttendanceTime = inputView.inputAttendanceTime();
        LocalTime attendanceTime = toLocalTime(rawAttendanceTime);
        return LocalDateTime.of(now.toLocalDate(), attendanceTime);
    }

    private void doUpdateAttendance(LocalDateTime now) {
        Crew crew = new Crew(inputExistNicknameForUpdate());
        LocalDateTime updateDateTime = inputUpdateDateTime(now);
        Optional<Attendance> beforeAttendance = findAttendanceByCrewAndDate(crew, updateDateTime);
        Attendance modifidedAttendance = attendances.update(new Attendance(crew, updateDateTime));
        printModifiedAttendance(beforeAttendance.orElse(null), modifidedAttendance);
    }

    private String inputExistNicknameForUpdate() {
        String nickname = inputView.inputNicknameForUpdateAttendance();
        attendances.validateExistNickname(nickname);
        return nickname;
    }

    private LocalDateTime inputUpdateDateTime(LocalDateTime now) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        String rawTimeForUpdate = inputView.inputTimeForUpdateAttendance();
        LocalDate updateDate = LocalDate.of(now.getYear(), now.getMonth(), targetUpdateDate);
        LocalDateTime updateDateTime = LocalDateTime.of(updateDate, toLocalTime(rawTimeForUpdate));
        validateFutureDate(now, updateDateTime);
        return updateDateTime;
    }

    private void validateFutureDate(LocalDateTime now, LocalDateTime updateDateTime) {
        boolean isFutureDate = updateDateTime.toLocalDate().isAfter(now.toLocalDate());
        if (isFutureDate) {
            throw new IllegalArgumentException("미래 날짜의 출석을 수정할 수 없습니다.");
        }
    }

    private Optional<Attendance> findAttendanceByCrewAndDate(Crew crew, LocalDateTime updateDateTime) {
        return attendances.findByCrewAndDate(crew, updateDateTime.toLocalDate());
    }

    private void printModifiedAttendance(Attendance beforeAttendance, Attendance modifidedAttendance) {
        outputView.printModifiedAttendance(beforeAttendance, modifidedAttendance,
                getAttendanceType(beforeAttendance),
                getAttendanceType(modifidedAttendance)
        );
    }

    private AttendanceType getAttendanceType(Attendance attendance) {
        if (attendance == null) {
            return AttendanceType.ABSENCE;
        }
        return calculateAttendanceType(attendance.getDateTime());
    }

    private AttendanceType calculateAttendanceType(LocalDateTime dateTime) {
        LocalTime startTime = AttendanceStartTime.findDayOfWeek(dateTime.getDayOfWeek());
        return AttendanceType.judge(startTime, dateTime.toLocalTime());
    }

    private void doAttendanceTimeline(LocalDateTime now) {
        Crew crew = new Crew(inputExistNickname());
        AttendanceTimeline attendanceTimeline = generateAttendanceTimelineByCrew(now, crew);
        outputView.printAttendanceTimelineInMonth(crew.getNickname(), attendanceTimeline);
        printCountOfAttendanceType(attendanceTimeline);
        outputView.printWarningLevel(judgeWarningLevel(attendanceTimeline));
    }

    private AttendanceTimeline generateAttendanceTimelineByCrew(LocalDateTime now, Crew crew) {
        Set<Attendance> attendanceHistory = attendances.findAllByCrewAndMonth(crew, now.getMonth());
        return AttendanceTimeline.generateAttendanceTimelineUntilDate(attendanceHistory, now.toLocalDate());
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
        List<CrewAttendanceSummary> crewAttendanceSummaries = createAllAttendanceSummaries(now);
        outputView.printEmergencyCrews(sortCrewAttendanceSummaries(crewAttendanceSummaries));
    }

    private List<CrewAttendanceSummary> createAllAttendanceSummaries(LocalDateTime now) {
        Map<Crew, Set<Attendance>> allAttendanceHistory = attendances.findAllByMonth(now.getMonth());
        return createAllCrewAttendanceSummaries(now, allAttendanceHistory);
    }

    private List<CrewAttendanceSummary> createAllCrewAttendanceSummaries(LocalDateTime now,
                                                                         Map<Crew, Set<Attendance>> allAttendanceHistory) {
        List<CrewAttendanceSummary> crewAttendanceSummaries = new ArrayList<>();
        for (Crew crew : allAttendanceHistory.keySet()) {
            AttendanceTimeline attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(
                    allAttendanceHistory.get(crew), now.toLocalDate());
            int lateCount = attendanceTimeline.countByAttendanceType(AttendanceType.LATE);
            int absenceCount = attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE);
            AttendanceWarningLevel level = judgeWarningLevel(attendanceTimeline);
            crewAttendanceSummaries.add(new CrewAttendanceSummary(crew, lateCount, absenceCount, level));
        }
        return crewAttendanceSummaries;
    }

    private List<CrewAttendanceSummary> sortCrewAttendanceSummaries(List<CrewAttendanceSummary> summaries) {
        return summaries.stream()
                .sorted(sortWarningLevelDesc()
                        .thenComparing(calculateTotalAbsentCount(), Comparator.reverseOrder())
                        .thenComparing(getCrewNickname())
                )
                .toList();
    }

    private Comparator<CrewAttendanceSummary> sortWarningLevelDesc() {
        return Comparator
                .comparing(CrewAttendanceSummary::level)
                .reversed();
    }

    private Function<CrewAttendanceSummary, Integer> calculateTotalAbsentCount() {
        return summary -> summary.absenceCount() + AttendanceWarningLevel.calculateLateToAbsent(summary.lateCount);
    }

    private Function<CrewAttendanceSummary, String> getCrewNickname() {
        return summary -> summary.crew().getNickname();
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
