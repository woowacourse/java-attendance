package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttendResult {

    private final List<Attend> attendResult;

    public AttendResult(final List<Attend> attendResult) {
        this.attendResult = new ArrayList<>(attendResult);
    }

    public AttendResult() {
        this.attendResult = new ArrayList<>();
    }

    public void addAttend(Attend targetAttend) {
        checkAlreadyContain(targetAttend);
        checkOperationTime(targetAttend);
        attendResult.add(targetAttend);
    }

    private void checkAlreadyContain(Attend targetAttend) {
        if (contains(targetAttend)) {
            throw new IllegalArgumentException("이미 출석하였습니다.");
        }
    }

    private void checkOperationTime(Attend attend) {
        if (attend != null && !OperationTime.isContainsOperationTime(attend.getTime())) {
            throw new IllegalArgumentException("운영 시간 외입니다.");
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendResult that = (AttendResult) object;
        return Objects.equals(attendResult, that.attendResult);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendResult);
    }
}
