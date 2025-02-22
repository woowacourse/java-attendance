package domain;

public enum ExpelRisk {
    
    정상(1),
    경고(2),
    면담(3),
    제적(4),
    ;
    
    private final int seriousness;
    
    ExpelRisk(int seriousness) {
        this.seriousness = seriousness;
    }
    
    public static final int NORMAL_THRESHOLD = 1;
    public static final int WARNING_THRESHOLD = 2;
    public static final int INTERVIEW_THRESHOLD = 5;
    
    public static ExpelRisk of(int absentCount, int lateCount) {
        int expelRiskMeasureValue = calculateExpelRiskMeasureValue(absentCount, lateCount);
        if (expelRiskMeasureValue <= NORMAL_THRESHOLD) {
            return 정상;
        }
        if (expelRiskMeasureValue <= WARNING_THRESHOLD) {
            return 경고;
        }
        if (expelRiskMeasureValue <= INTERVIEW_THRESHOLD) {
            return 면담;
        }
        return 제적;
    }
    
    private static int calculateExpelRiskMeasureValue(int absentCount, int lateCount) {
        return absentCount + lateCount / 3;
    }
    
    public int getSeriousness() {
        return seriousness;
    }
}
