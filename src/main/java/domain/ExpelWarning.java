package domain;

public enum ExpelWarning {
    정상,
    경고,
    면담,
    제적,
    ;
    
    public static ExpelWarning of(final int lateCount, final int absentCount) {
        int expelWarningPoint = absentCount + (lateCount / 3);
        if (expelWarningPoint > 5) return 제적;
        if (expelWarningPoint >= 3) return 면담;
        if (expelWarningPoint == 2) return 경고;
        return 정상;
    }
}
