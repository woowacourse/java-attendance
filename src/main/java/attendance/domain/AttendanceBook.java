package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.exception.AttendanceArgumentException;
import attendance.interfaces.SystemDateTime;

public record AttendanceBook(Map<Nickname, Attendances> attendancesBook, SystemDateTime systemDateTime) {
    private static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";

    public static AttendanceBook createFromRecords(Map<String, LocalDateTime> records, SystemDateTime systemDateTime) {
        return new AttendanceBook(createAttendancesBook(records, systemDateTime), systemDateTime);
    }

    private static Map<Nickname, Attendances> createAttendancesBook(Map<String, LocalDateTime> records,
        SystemDateTime systemDateTime) {
        Map<Nickname, Attendances> result = new HashMap<>();
        for (Map.Entry<String, LocalDateTime> record : records.entrySet()) {
            var nickname = new Nickname(record.getKey());
            var dateTime = record.getValue();

            result.computeIfAbsent(nickname, nicknameKey -> new Attendances(systemDateTime))
                .addAttendance(dateTime);
        }
        return result;
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
        if (attendancesBook.containsKey(nickname)) {
            return attendancesBook.get(nickname);
        }
        throw new AttendanceArgumentException(NOT_REGISTERED_NICKNAME);
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
