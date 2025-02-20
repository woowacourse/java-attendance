package attendance.domain;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CrewStatistic {
    private final Crew crew;
    private final List<Attendance> crewAttendances;
    private CrewStatus status;

    public CrewStatistic(Crew crew, List<Attendance> crewAttendances) {
        this.crew = crew;
        this.crewAttendances = crewAttendances;
        this.status = CrewStatus.NONE;
    }

    public void resetCrewStatus() {
        crew.resetCount();
    }

    public void initCrewsStatus() {
        for (Attendance crewAttendance : crewAttendances) {
            initCrewStatus(crewAttendance);
        }
    }

    private void initCrewStatus(Attendance crewAttendance) {
        if (crewAttendance.getType() == AttendanceType.SAFE) {
            crew.plusSafeCount();
        }
        if (crewAttendance.getType() == AttendanceType.LATE) {
            crew.plusLateCount();
        }
        if (crewAttendance.getType() == AttendanceType.ABSENT) {
            crew.plusAbsentCount();
        }
    }

    public void calculatePenalty() {
        int lateCount = crew.getLateCount();
        int absentCount = crew.getAbsentCount();

        int plusAbsentCount = lateCount / 3;
        absentCount += plusAbsentCount;

        if (absentCount > 5) {
            status = CrewStatus.EXPEL;
        }
        if (absentCount >= 3) {
            status = CrewStatus.MEETING;
        }
        if (absentCount >= 2) {
            status = CrewStatus.WARNING;
        }
    }

    public List<List<String>> getCrewAttendanceHistory() {
        List<Attendance> sortedAttendances = crewAttendances.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();

        List<List<String>> crewStatisticInfo = new ArrayList<>();

        for (Attendance crewAttendance : sortedAttendances) {
            LocalDate crewLocalDate = crewAttendance.getDate();

            crewStatisticInfo.add(
                    List.of(
                            String.valueOf(crewLocalDate.getMonthValue()),
                            String.format("%02d", crewLocalDate.getDayOfMonth()),
                            crewLocalDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                            crewAttendance.getTimeValue(),
                            crewAttendance.getType().toString()
                    )
            );
        }

        return crewStatisticInfo;
    }

    public List<String> getCrewStatisticStatus() {
        return List.of(
                String.valueOf(crew.getSafeCount()),
                String.valueOf(crew.getLateCount()),
                String.valueOf(crew.getAbsentCount()),
                status.toString()
        );
    }

    public int getPenaltyCount() {
        int absentCount = crew.getAbsentCount();
        int lateCount = crew.getLateCount();
        return absentCount + lateCount / 3;
    }

    public String getCrewName() {
        return crew.getName();
    }

    public List<String> crewExpelExpectedInfo() {
        return List.of(
                crew.getName(),
                String.valueOf(crew.getAbsentCount()),
                String.valueOf(crew.getLateCount()),
                status.toString()
        );
    }
}
