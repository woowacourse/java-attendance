package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceLog {

    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Nickname nickname;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public AttendanceLog(Nickname nickname, LocalDate attendanceDate, LocalTime attendanceTime) {
        validateNickname(nickname);
        validateAttendanceDate(attendanceDate);
        validateAttendanceTime(attendanceTime);
        this.nickname = nickname;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    private void validateAttendanceTime(LocalTime attendanceTime) {
        if (attendanceTime.isBefore(OPEN_TIME) || attendanceTime.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("캠퍼스 운영시간(%s~%s) 외에는 출석할 수 없습니다."
                    .formatted(OPEN_TIME.format(TIME_FORMATTER), CLOSE_TIME.format(TIME_FORMATTER)));
        }
    }

    public AttendanceLog(Nickname nickname, LocalDate attendanceDate) {
        validateNickname(nickname);
        validateAttendanceDate(attendanceDate);
        this.nickname = nickname;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = null;
    }

    private void validateNickname(Nickname nickname) {
        if (nickname == null) {
            throw new IllegalArgumentException("닉네임은 null일 수 없습니다.");
        }
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (attendanceDate == null) {
            throw new IllegalArgumentException("출석 날짜는 null일 수 없습니다.");
        }
        if (PublicHoliday.isPublicHoliday(attendanceDate)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
        if (isWeekend(attendanceDate)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }

    private boolean isWeekend(LocalDate baseDate) {
        DayOfWeek dayOfWeek = baseDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public boolean isNotRecorded() {
        return attendanceTime == null;
    }

    public boolean isSameNicknameAndDate(Nickname comparedNickname, LocalDate comparedDate) {
        return nickname.equals(comparedNickname)
                && attendanceDate.isEqual(comparedDate);
    }

    public boolean isSameNicknameAndMonth(Nickname comparedNickname, LocalDate comparedDate) {
        return nickname.equals(comparedNickname)
                && attendanceDate.getYear() == comparedDate.getYear()
                && attendanceDate.getMonth() == comparedDate.getMonth();
    }

    public boolean isBefore(LocalDate baseDate) {
        return attendanceDate.isBefore(baseDate);
    }

    public LocalDateTime getAttendanceDateTime() {
        return LocalDateTime.of(attendanceDate, getAttendanceTime());
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        if (attendanceTime == null) {
            throw new IllegalArgumentException("등교 시간 기록이 존재하지 않습니다.");
        }
        return attendanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceLog that = (AttendanceLog) o;
        return Objects.equals(nickname, that.nickname)
                && Objects.equals(attendanceDate, that.attendanceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, attendanceDate);
    }
}
