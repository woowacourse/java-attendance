package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import attendance.exception.AttendanceArgumentException;
import attendance.interfaces.SystemDateTime;

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

    public String getConvertedAttendanceState(Nickname nickname, LocalDate date) {
        Attendance attendance = getAttendance(nickname, date);
        return attendance.getConvertedStatus();
    }

    public Attendances getAttendances(Nickname nickname) {
        return Optional.ofNullable(attendancesBook.get(nickname))
            .orElseThrow(() -> new AttendanceArgumentException(NOT_REGISTERED_NICKNAME));
    }

    public void modify(Nickname nickname, LocalDateTime dateTime) {
        var attendances = getAttendances(nickname);
        attendances.modifyAttendance(dateTime);
    }

    public List<StatusStatistics> getSanctionLevels() {
        List<StatusStatistics> sanctionLevels = new ArrayList<>();
        for (Nickname nickname : attendancesBook.keySet()) {
            var attendances = getAttendances(nickname);
            var statistics = new StatusStatistics(nickname);
            statistics.update(attendances.updateStatics());
            sanctionLevels.add(statistics);
        }
        return sanctionLevels;
    }
}
