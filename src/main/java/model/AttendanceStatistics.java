package model;

import java.util.Map;
import java.util.Objects;

public class AttendanceStatistics implements Comparable<AttendanceStatistics> {

    private final Crew crew;
    private final Map<AttendanceType, Integer> statistics;

    private AttendanceStatistics(Crew crew, Map<AttendanceType, Integer> statistics) {
        this.crew = crew;
        this.statistics = statistics;
    }

    public static AttendanceStatistics of(Crew crew, Map<AttendanceType, Integer> attendanceTypeCounts) {
        return new AttendanceStatistics(crew, attendanceTypeCounts);
    }

    public boolean isDanger() {
        PunishmentType punishmentType = getPunishmentType();
        return punishmentType.equals(PunishmentType.WARNING) ||
                punishmentType.equals(PunishmentType.EXPULSION) ||
                punishmentType.equals(PunishmentType.MEETING);
    }

    public PunishmentType getPunishmentType() {
        return PunishmentType.calculateType(getConvertedAbsenceCount());
    }

    private int getConvertedAbsenceCount() {
        return statistics.get(AttendanceType.BE_LATE) / 3 + statistics.get(AttendanceType.ABSENCE);
    }

    public Map<AttendanceType, Integer> getStatistics() {
        return statistics;
    }

    public Crew getCrew() {
        return crew;
    }

    @Override
    public int compareTo(AttendanceStatistics o) {
        if (compareWithPunishmentType(o) == 0 && compareWithAbsenceCount(o) == 0) {
            return crew.compareTo(o.crew);
        }
        if (compareWithPunishmentType(o) == 0) {
            return compareWithAbsenceCount(o);
        }
        return compareWithPunishmentType(o);
    }

    private int compareWithAbsenceCount(AttendanceStatistics o) {
        int thisAbsenceCount = AttendanceType.calculateConvertedAbsenceCount(statistics);
        int otherAbsenceCount = AttendanceType.calculateConvertedAbsenceCount(o.statistics);
        return thisAbsenceCount - otherAbsenceCount;
    }

    private int compareWithPunishmentType(AttendanceStatistics o) {
        return getPunishmentType().comparePriority(o.getPunishmentType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceStatistics that = (AttendanceStatistics) o;
        return Objects.equals(crew, that.crew) && Objects.equals(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, statistics);
    }
}
