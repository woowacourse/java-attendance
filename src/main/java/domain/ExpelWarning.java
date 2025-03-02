package domain;

import java.util.Arrays;
import java.util.Comparator;

public enum ExpelWarning {
    정상(1, 0),
    경고(2, 2),
    면담(3, 3),
    제적(4, 6),
    ;
    
    private final int importance;
    private final int minExpelWarningPoint;
    
    ExpelWarning(final int importance, final int minExpelWarningPoint) {
        this.importance = importance;
        this.minExpelWarningPoint = minExpelWarningPoint;
    }
    
    public static ExpelWarning of(final int lateCount, final int absentCount) {
        int expelWarningPoint = (lateCount / 3) + absentCount;
        
        return Arrays.stream(ExpelWarning.values())
                .filter(expelWarning -> expelWarning.minExpelWarningPoint <= expelWarningPoint)
                .max(Comparator.comparingInt(expelWarning -> expelWarning.importance))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 제적 위험치가 없습니다."));
    }
}
