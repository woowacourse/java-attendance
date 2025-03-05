package attendance;

import java.util.Arrays;

public enum ManagementStatus {
  EXPULSION(6,Integer.MAX_VALUE),
  INTERVIEW(3,5),
  WARNING(2,2),
  GENERAL(0,1);

  private final int conditionMin;
  private final int conditionMax;

  ManagementStatus(int conditionMin, int conditionMax) {
    this.conditionMin = conditionMin;
    this.conditionMax = conditionMax;
  }

  public static ManagementStatus from(int absenceCount) {
    return Arrays.stream(ManagementStatus.values())
        .filter(status -> status.matchesCondition(absenceCount))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("해당 상태가 존재하지 않습니다."));
  }

  public boolean matchesCondition(int absenceCount) {
    return conditionMin <= absenceCount && absenceCount <= conditionMax;
  }
}
