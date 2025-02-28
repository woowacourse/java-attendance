package domain.crew;

import exception.ErrorException;
import java.util.Arrays;

public enum CrewStatus {

    EXPEL("제적", 6),
    CONSULT("면담", 3),
    WARNING("경고", 2),
    PASS("정상", 0);

    private final String description;
    private final int minimumAbsence;

    CrewStatus(String description, int minimumAbsence) {
        this.description = description;
        this.minimumAbsence = minimumAbsence;
    }

    public static CrewStatus findStatus(int lateCount, int absentCount) {
        int absenceCount = lateCount / 3 + absentCount;
        return Arrays.stream(CrewStatus.values())
                .filter(crewStatus -> crewStatus.minimumAbsence <= absenceCount)
                .findFirst()
                .orElseThrow(() -> new ErrorException("해당하는 크루 상태가 없습니다."));
    }

    public String getDescription() {
        return description;
    }
}
