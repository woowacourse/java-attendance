package domain;

import java.util.Arrays;

public enum ExpelRisk {
    
    정상(1, (absentCount, lateCount) -> absentCount + lateCount / 3 < 2),
    경고(2, (absentCount, lateCount) -> absentCount + lateCount / 3 == 2),
    면담(3, (absentCount, lateCount) -> absentCount + lateCount / 3 > 2 && absentCount + lateCount / 3 < 5),
    제적(4, (absentCount, lateCount) -> absentCount + lateCount / 3 > 5),
    ;
    
    private final int seriousness;
    private final ExpelRiskMeasurement expelRiskMeasurement;
    
    ExpelRisk(int seriousness, ExpelRiskMeasurement expelRiskMeasurement) {
        this.seriousness = seriousness;
        this.expelRiskMeasurement = expelRiskMeasurement;
    }
    
    public static ExpelRisk of(int absentCount, int lateCount) {
        return Arrays.stream(ExpelRisk.values())
                .filter(risk -> risk.expelRiskMeasurement.measure(absentCount, lateCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("제적 위험도를 생성할 수 없습니다."));
    }
    
    @FunctionalInterface
    private interface ExpelRiskMeasurement {
        boolean measure(int absentCount, int lateCount);
    }
    
    public int getSeriousness() {
        return seriousness;
    }
}
