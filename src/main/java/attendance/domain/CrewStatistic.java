package attendance.domain;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CrewStatistic {
    private static final int WARNING_COUNT_LIMIT = 2;
    private static final int MEETING_COUNT_LIMIT = 3;
    private static final int EXPEL_COUNT_LIMIT = 5;

    private static final String MONTH_FORMAT = "%02d";

    private final Crew crew;
    private final AttendanceCount attendanceCount;
    private final List<Attendance> crewAttendances;
    private CrewStatus status;

    public CrewStatistic(Crew crew, List<Attendance> crewAttendances) {
        this.crew = crew;
        this.attendanceCount = new AttendanceCount();
        this.crewAttendances = crewAttendances;
        this.status = CrewStatus.NONE;
    }

    public void initCrewsStatus() {
        for (Attendance crewAttendance : crewAttendances) {
            initCrewStatus(crewAttendance);
        }
    }

    private void initCrewStatus(Attendance crewAttendance) {
        if (crewAttendance.getType() == AttendanceType.SAFE) {
            attendanceCount.incrementSafeCount();
        }
        if (crewAttendance.getType() == AttendanceType.LATE) {
            attendanceCount.incrementLateCount();
        }
        if (crewAttendance.getType() == AttendanceType.ABSENT) {
            attendanceCount.incrementAbsentCount();
        }
    }

    public void resetCrewStatus() {
        attendanceCount.resetAttendanceCount();
    }

    public void calculatePenalty() {
        int absentCount = attendanceCount.calculatePenalty();
        checkCrewStatus(absentCount);
    }

    private void checkCrewStatus(int absentCount) {
        if (absentCount > EXPEL_COUNT_LIMIT) {
            status = CrewStatus.EXPEL;
            return;
        }
        if (absentCount >= MEETING_COUNT_LIMIT) {
            status = CrewStatus.MEETING;
            return;
        }
        if (absentCount == WARNING_COUNT_LIMIT) {
            status = CrewStatus.WARNING;
        }
    }

    public List<List<String>> crewAttendanceHistoryInfo() {
        List<Attendance> sortedAttendances = sortCrewAttendances();
        List<List<String>> crewStatisticInfo = new ArrayList<>();

        for (Attendance crewAttendance : sortedAttendances) {
            LocalDate crewLocalDate = crewAttendance.getDate();
            crewStatisticInfo.add(
                    createCrewAttendanceInfo(crewLocalDate, crewAttendance)
            );
        }
        return crewStatisticInfo;
    }

    private List<Attendance> sortCrewAttendances() {
        return crewAttendances.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();
    }

    private List<String> createCrewAttendanceInfo(LocalDate crewLocalDate, Attendance crewAttendance) {
        return List.of(
                String.valueOf(crewLocalDate.getMonthValue()),
                String.format(MONTH_FORMAT, crewLocalDate.getDayOfMonth()),
                crewLocalDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                crewAttendance.getTimeValue(),
                crewAttendance.getType().toString()
        );
    }

    public List<String> crewStatisticStatusInfo() {
        return List.of(
                String.valueOf(attendanceCount.getSafeCount()),
                String.valueOf(attendanceCount.getLateCount()),
                String.valueOf(attendanceCount.getAbsentCount()),
                status.toString()
        );
    }

    public List<String> crewExpelExpectedInfo() {
        return List.of(
                crew.getName(),
                String.valueOf(attendanceCount.getAbsentCount()),
                String.valueOf(attendanceCount.getLateCount()),
                status.toString()
        );
    }

    public int getPenaltyCount() {
        return attendanceCount.checkPenaltyCount();
    }

    public String getCrewName() {
        return crew.getName();
    }
}
