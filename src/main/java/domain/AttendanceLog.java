package domain;

import java.time.LocalDateTime;

public record AttendanceLog(Nickname nickname, LocalDateTime localDateTime) {
}
