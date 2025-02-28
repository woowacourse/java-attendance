package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import util.DateTimeUtil;

public record AttendanceRecord(
        String nickname,
        LocalDate date,
        LocalTime time,
        AttendanceStatus status
) {
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    public AttendanceRecord {
        validateNickname(nickname);
        validateDate(date);
        validateTime(time);
        validateStatus(status);
    }

    public AttendanceRecord(String nickname, LocalDate date, LocalTime time) {
        this(nickname, date, time, AttendanceStatus.of(date, time));
    }

    private void validateNickname(String nickname) {
        if (nickname.isEmpty()) {
            throw new IllegalArgumentException("닉네임은 빈 값일 수 없습니다.");
        }
    }

    private void validateDate(LocalDate date) {
        validateOffDate(date);
    }

    private void validateTime(LocalTime time) {
        validateCampusTime(time);
    }

    private void validateOffDate(LocalDate date) {
        if (DateTimeUtil.isWeekend(date)
                || DateTimeUtil.isHoliday(date)) {
            throw new IllegalArgumentException(date + ": 주말 및 공휴일에는 출석을 기록할 수 없습니다.");
        }
    }

    private void validateCampusTime(LocalTime time) {
        if (!DateTimeUtil.isInRange(CAMPUS_OPEN_TIME, CAMPUS_CLOSE_TIME, time)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아니므로 출석을 기록할 수 없습니다.");
        }
    }

    private void validateStatus(AttendanceStatus status) {
        if (Objects.isNull(status)) {
            throw new IllegalArgumentException("출석 상태는 null일 수 없습니다.");
        }
    }
}
