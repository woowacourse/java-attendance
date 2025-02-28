import java.util.ArrayList;
import java.util.List;

public class AttendResult {

    private final List<Attend> attendResult;

    public AttendResult(final List<Attend> attendResult) {
        this.attendResult = attendResult;
    }

    public AttendResult() {
        this.attendResult = new ArrayList<>();
    }

    public void addAttend(Attend targetAttend) {
        checkAlreadyContain(targetAttend);
    }

    private void checkAlreadyContain(Attend targetAttend) {
        if (attendResult.stream().anyMatch(attend -> attend.equalsDate(targetAttend))) {
            throw new IllegalArgumentException("이미 출석하였습니다.");
        }
    }
}
