package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import attendance.exception.AttendanceArgumentException;

public record AttendanceBook(Map<Nickname, Attendances> attendancesBook, SystemDateTime systemDateTime) {
    private static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";

    public AttendanceBook(SystemDateTime systemDateTime) {
        this(new HashMap<>(), systemDateTime);
    }

    public void put(String name, LocalDateTime dateTime) {
        var nickname = new Nickname(name);
        Optional<Attendances> attendances = Optional.ofNullable(attendancesBook.get(nickname));
        attendances.ifPresentOrElse(
            eixstAttendances -> eixstAttendances.addAttendance(dateTime),
            () -> putNewAttendances(dateTime, nickname)
        );
    }

    private void putNewAttendances(LocalDateTime dateTime, Nickname nickname) {
        var newAttendances = new Attendances(systemDateTime);
        newAttendances.addAttendance(dateTime);
        attendancesBook.put(nickname, newAttendances);
    }

    public void attendance(Nickname nickname, LocalDateTime dateTime) {
        var attendances = getAttendances(nickname);
        attendances.addAttendance(dateTime);
    }

    public Attendance getAttendance(Nickname nickname, LocalDate date) {
        var attendances = getAttendances(nickname);
        return attendances.getAttendance(date);
    }

    private Attendances getAttendances(Nickname nickname) {
        return Optional.ofNullable(attendancesBook.get(nickname))
            .orElseThrow(() -> new AttendanceArgumentException(NOT_REGISTERED_NICKNAME));
    }

    public void modify(Nickname nickname, LocalDateTime dateTime) {
        var attendances = getAttendances(nickname);
        attendances.modifyAttendance(dateTime);
    }
}
