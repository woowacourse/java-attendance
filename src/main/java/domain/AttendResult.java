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
        if (attend.checkTimeNull() && !OperationTime.isContainsOperationTime(attend.getTime())) {
            throw new IllegalArgumentException("운영 시간 외입니다.");
        }
    }

    private boolean contains(Attend targetAttend) {
        return attendResult.stream()
                .anyMatch(attend -> attend.equals(targetAttend));
    }

    public Attend edit(final Attend targetAttend) {
        Attend before = findAttendByDay(targetAttend.getDate());
        attendResult.remove(before);
        attendResult.add(targetAttend);
        return new Attend(targetAttend.getDate());
    }

    public List<Attend> getAttendResult() {
        return attendResult;
    }

    private Attend findAttendByDay(LocalDate date) {
        return attendResult.stream()
                .filter(attend -> attend.getDate().equals(date))
                .findAny()
                .orElse(new Attend(date));
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
