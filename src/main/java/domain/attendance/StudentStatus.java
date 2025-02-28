package domain.attendance;

import java.util.Arrays;
import java.util.Comparator;

public enum StudentStatus {
    DISMISSAL("제적",6),
    COUNSELED("면담",3),
    WARNING("경고",2),
    NONE("",0);

    private final String description;
    private final int limitCount;

    StudentStatus(String description, int limitCount) {
        this.description = description;
        this.limitCount = limitCount;
    }

    public static StudentStatus calcStudentStatus(int absenceCountIncludingTardy){
        return Arrays.stream(StudentStatus.values())
                .sorted(Comparator.comparingInt(StudentStatus::getLimitCount).reversed())
                .filter(val -> val.getLimitCount() <= absenceCountIncludingTardy)
                .findFirst()
                .orElse(NONE);
    }

    public String getDescription() {
        return description;
    }

    public int getLimitCount() {
        return limitCount;
    }
}
