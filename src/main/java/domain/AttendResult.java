package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

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

    public WarningStatus judgeWarningStatus(int targetDay) {
        AttendCount attendCount = countAttendStatus(targetDay);
        return WarningStatus.judgeWarningStatus(attendCount);
    }

    public AttendCount countAttendStatus(final int targetDay) {
        List<Attend> attends = getAttendResult(targetDay);
        List<AttendStatus> attendStatus = attends.stream()
                .map(Attend::checkStatus)
                .toList();
        return AttendCount.createCount(attendStatus);
    }

    public List<Attend> getAttendResult(final int targetDay) {
        return IntStream.range(1, targetDay)
                .filter(OperationTime::isOperationDate)
                .mapToObj(this::findAttendByDay)
                .toList();
    }

    public Attend findAttendByDay(int day) {
        LocalDate date = LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), day);
        return findAttendByDay(date);
    }

    private Attend findAttendByDay(LocalDate date) {
        return attendResult.stream()
                .filter(attend -> attend.getDate().equals(date))
                .findAny()
                .orElse(new Attend(date));
    }

    public Attend edit(final Attend targetAttend) {
        Attend before = findAttendByDay(targetAttend.getDate());
        attendResult.remove(before);
        attendResult.add(targetAttend);
        return before;
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
