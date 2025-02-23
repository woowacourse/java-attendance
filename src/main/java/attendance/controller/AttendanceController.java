package attendance.controller;

import static attendance.util.DateTimeUtil.parseTime;

import attendance.model.Nickname;
import attendance.util.AttendancesFactory;
import attendance.dto.CrewAttendanceSummary;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    public void startAttendanceSystem(LocalDateTime baseDateTime) {
        boolean shouldContinue;
        do {
            displayStartMessage(baseDateTime);
            shouldContinue = processUserCommand(baseDateTime);
        } while (shouldContinue);
    }

    private boolean processUserCommand(LocalDateTime baseDateTime) {
        try {
            Command command = readCommand();
            executeCommand(command, baseDateTime);
            return command != Command.QUIT;
        } catch (DateTimeException e) {
            displayDateTimeFormatError();
        } catch (RuntimeException e) {
            displayError(e);
        }
        return true;
    }

    private Command readCommand() {
        return Command.from(inputView.inputCommand());
    }

    private void executeCommand(Command command, LocalDateTime baseDateTime) {
        if (command == Command.ATTENDANCE) {
            doAttendance(baseDateTime);
        }
        if (command == Command.ATTENDANCE_UPDATE) {
            doUpdateAttendance(baseDateTime);
        }
        if (command == Command.ATTENDANCE_TIMELINE) {
            doAttendanceTimeline(baseDateTime);
        }
        if (command == Command.EMERGENCY_CHECK) {
            doEmergencyCheck(baseDateTime);
        }
    }

    private void doAttendance(LocalDateTime baseDateTime) {
        Attendance attendance = createAttendance(baseDateTime);
        attendances.add(attendance);
        displayCheckAttendance(attendance);
    }

    private Attendance createAttendance(LocalDateTime baseDateTime) {
        Crew crew = new Crew(readExistingNickname());
        LocalDateTime attendanceDateTime = parseAttendanceDateTime(baseDateTime);
        return new Attendance(crew, attendanceDateTime);
    }

    private Nickname readExistingNickname() {
        Nickname nickname = new Nickname(inputView.inputNickname());
        attendances.validateExistNickname(nickname);
        return nickname;
    }

    private LocalDateTime parseAttendanceDateTime(LocalDateTime baseDateTime) {
        LocalTime attendanceTime = parseTime(inputView.inputAttendanceTime());
        return LocalDateTime.of(baseDateTime.toLocalDate(), attendanceTime);
    }

    private void doUpdateAttendance(LocalDateTime baseDateTime) {
        Crew crew = new Crew(readExistingNicknameForUpdate());
        LocalDateTime updateDateTime = readValidUpdateDateTime(baseDateTime);
        Attendance beforeAttendance = attendances.findByCrewAndDate(crew, updateDateTime.toLocalDate());
        Attendance modifiedAttendance = attendances.update(new Attendance(crew, updateDateTime));
        displayUpdatedAttendance(beforeAttendance, modifiedAttendance);
    }

    private Nickname readExistingNicknameForUpdate() {
        Nickname nickname = new Nickname(inputView.inputNicknameForUpdateAttendance());
        attendances.validateExistNickname(nickname);
        return nickname;
    }

    private LocalDateTime readValidUpdateDateTime(LocalDateTime baseDateTime) {
        LocalDateTime updateDateTime = parseUpdateDateTime(baseDateTime);
        validateFutureDate(baseDateTime, updateDateTime);
        return updateDateTime;
    }

    private LocalDateTime parseUpdateDateTime(LocalDateTime baseDateTime) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        LocalDate updateDate = LocalDate.of(baseDateTime.getYear(), baseDateTime.getMonth(), targetUpdateDate);
        LocalTime updateTime = parseTime(inputView.inputTimeForUpdateAttendance());
        return LocalDateTime.of(updateDate, updateTime);
    }

    private void validateFutureDate(LocalDateTime now, LocalDateTime updateDateTime) {
        boolean isFutureDate = updateDateTime.toLocalDate().isAfter(now.toLocalDate());
        if (isFutureDate) {
            throw new IllegalArgumentException("미래 날짜의 출석을 수정할 수 없습니다.");
        }
    }

    private AttendanceType determineAttendanceType(Attendance attendance) {
        if (attendance.isNotRecordedTime()) {
            return AttendanceType.ABSENCE;
        }
        return calculateAttendanceType(attendance.getDateTime());
    }

    private AttendanceType calculateAttendanceType(LocalDateTime dateTime) {
        LocalTime startTime = AttendanceStartTime.findDayOfWeek(dateTime.getDayOfWeek());
        return AttendanceType.judge(startTime, dateTime.toLocalTime());
    }

    private void doAttendanceTimeline(LocalDateTime baseDateTime) {
        Crew crew = new Crew(readExistingNickname());
        AttendanceTimeline attendanceTimeline = generateAttendanceTimelineByCrew(crew, baseDateTime);
        displayAttendanceTimeline(crew, attendanceTimeline);
    }

    private AttendanceTimeline generateAttendanceTimelineByCrew(Crew crew, LocalDateTime baseDateTime) {
        Set<Attendance> attendanceHistory = attendances.findAllByCrewAndMonth(crew, baseDateTime.getMonth());
        return AttendanceTimeline.generateAttendanceTimelineUntilDate(attendanceHistory, baseDateTime.toLocalDate());
    }

    private AttendanceWarningLevel determineWarningLevel(AttendanceTimeline attendanceTimeline) {
        return AttendanceWarningLevel.judge(
                attendanceTimeline.countByAttendanceType(AttendanceType.LATE),
                attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE)
        );
    }

    private void doEmergencyCheck(LocalDateTime baseDateTime) {
        List<CrewAttendanceSummary> crewAttendanceSummaries = createAllAttendanceSummaries(baseDateTime);
        displayEmergencyCrews(crewAttendanceSummaries);
    }

    private List<CrewAttendanceSummary> createAllAttendanceSummaries(LocalDateTime baseDateTime) {
        Map<Crew, Set<Attendance>> allAttendanceHistory = attendances.findAllByMonth(baseDateTime.getMonth());
        return createAllCrewAttendanceSummaries(baseDateTime, allAttendanceHistory);
    }

    private List<CrewAttendanceSummary> createAllCrewAttendanceSummaries(LocalDateTime baseDateTime,
                                                                         Map<Crew, Set<Attendance>> allAttendanceHistory) {
        List<CrewAttendanceSummary> crewAttendanceSummaries = new ArrayList<>();
        for (Crew crew : allAttendanceHistory.keySet()) {
            AttendanceTimeline attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(
                    allAttendanceHistory.get(crew), baseDateTime.toLocalDate());
            int lateCount = attendanceTimeline.countByAttendanceType(AttendanceType.LATE);
            int absenceCount = attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE);
            AttendanceWarningLevel level = determineWarningLevel(attendanceTimeline);
            crewAttendanceSummaries.add(new CrewAttendanceSummary(crew, lateCount, absenceCount, level));
        }
        return List.copyOf(crewAttendanceSummaries);
    }

    private void displayStartMessage(LocalDateTime baseDateTime) {
        outputView.printDate(baseDateTime.toLocalDate());
    }

    private void displayDateTimeFormatError() {
        outputView.printDateTimeErrorMessage();
    }

    private void displayError(RuntimeException e) {
        outputView.printErrorMessage(e.getMessage());
    }

    private void displayCheckAttendance(Attendance attendance) {
        outputView.printCheckAttendance(attendance.getDateTime(), calculateAttendanceType(attendance.getDateTime()));
    }

    private void displayUpdatedAttendance(Attendance beforeAttendance, Attendance modifidedAttendance) {
        outputView.printModifiedAttendance(
                beforeAttendance,
                modifidedAttendance,
                determineAttendanceType(beforeAttendance),
                determineAttendanceType(modifidedAttendance)
        );
    }

    private void displayAttendanceTimeline(Crew crew, AttendanceTimeline attendanceTimeline) {
        outputView.printAttendanceTimelineInMonth(crew.getNickname().getValue(), attendanceTimeline);
        displayAttendanceCounts(attendanceTimeline);
        outputView.printWarningLevel(determineWarningLevel(attendanceTimeline));
    }

    private void displayAttendanceCounts(AttendanceTimeline attendanceTimeline) {
        int okCount = attendanceTimeline.countByAttendanceType(AttendanceType.OK);
        int lateCount = attendanceTimeline.countByAttendanceType(AttendanceType.LATE);
        int absenceCount = attendanceTimeline.countByAttendanceType(AttendanceType.ABSENCE);
        outputView.printCountOfAttendanceType(okCount, lateCount, absenceCount);
    }

    private void displayEmergencyCrews(List<CrewAttendanceSummary> crewAttendanceSummaries) {
        outputView.printEmergencyCrews(sortCrewAttendanceSummaries(crewAttendanceSummaries, getDisplayComparator()));
    }

    private List<CrewAttendanceSummary> sortCrewAttendanceSummaries(List<CrewAttendanceSummary> summaries, Comparator<CrewAttendanceSummary> comparator) {
        return summaries.stream()
                .sorted(comparator)
                .toList();
    }

    private Comparator<CrewAttendanceSummary> getDisplayComparator() {
        return sortWarningLevelDesc()
                .thenComparing(calculateTotalAbsentCount(), Comparator.reverseOrder())
                .thenComparing(getCrewNickname());
    }

    private Comparator<CrewAttendanceSummary> sortWarningLevelDesc() {
        return Comparator.comparing(CrewAttendanceSummary::level)
                .reversed();
    }

    private Function<CrewAttendanceSummary, Integer> calculateTotalAbsentCount() {
        return summary -> summary.absenceCount() + AttendanceWarningLevel.calculateLateToAbsent(summary.lateCount());
    }

    private Function<CrewAttendanceSummary, String> getCrewNickname() {
        return summary -> summary.crew().getNickname().getValue();
    }
}
