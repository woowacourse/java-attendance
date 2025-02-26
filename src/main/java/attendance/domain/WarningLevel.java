package attendance.domain;

public enum WarningLevel {
    WARNING;


    public static WarningLevel from(int lateCount, int absentCount) {
        int totalAbsent = lateCount / 3 + absentCount;
        if (totalAbsent >= 2) {
            return WARNING;
        }
        return null;
    }
}
