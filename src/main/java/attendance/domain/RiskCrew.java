package attendance.domain;

import java.util.Comparator;

public class RiskCrew {
    private final String name;
    private final int absenceCount;
    private final int lateCount;
    private final PenaltyType penaltyType;

    public RiskCrew(String name, int absenceCount, int lateCount, PenaltyType penaltyType) {
        this.name = name;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.penaltyType = penaltyType;
    }

    public static RiskCrew from(Crew crew, AttendanceRecord attendanceRecord) {
        PenaltyType penaltyType = attendanceRecord.checkPenaltyStatus();
        if (!penaltyType.equals(PenaltyType.NONE)) {
            return new RiskCrew(crew.getName(), attendanceRecord.checkAbsenceCounts(),
                    attendanceRecord.checkLateCounts(), penaltyType);
        }
        return null;
    }

    public static final Comparator<RiskCrew> RISK_COMPARATOR = new Comparator<RiskCrew>() {
        @Override
        public int compare(RiskCrew r1, RiskCrew r2) {
            int absenceComparison = Integer.compare(r2.getAbsenceCount(), r1.getAbsenceCount());
            if (absenceComparison == 0) {
                int lateComparison = Integer.compare(r2.getLateCount(), r1.getLateCount());
                if (lateComparison == 0) {
                    return r1.getName().compareTo(r2.getName());
                }
                return lateComparison;
            }
            return absenceComparison;
        }
    };

    public String getName() {
        return name;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public PenaltyType getPenaltyType() {
        return penaltyType;
    }
}
