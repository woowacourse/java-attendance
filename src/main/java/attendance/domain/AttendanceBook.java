package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import attendance.exception.AttendanceArgumentException;
import attendance.utility.DateTimeFormatterWrapper;

public record AttendanceBook(Map<String, Attendances> attendances, List<HistoryStatistic> historyStatistics) {
    private static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String REGEX = ",";

    public static AttendanceBook of(List<String> lines, SystemDateTime systemDateTime) {
        Map<String, Attendances> attendances = new HashMap<>();
        for (String line : lines) {
            addAttendance(line, attendances, systemDateTime);
        }
        return new AttendanceBook(attendances, new ArrayList<>());
    }

    private static void addAttendance(String line, Map<String, Attendances> attendances,
        SystemDateTime systemDateTime) {
        var lines = line.split(REGEX);
        var nickname = lines[0];

        Attendances attendanceList = attendances.computeIfAbsent(nickname, k -> new Attendances());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatterWrapper.getFormatter(DATETIME_FORMAT);
        var dateTime = LocalDateTime.parse(lines[1], dateTimeFormatter);
        var attendance = Attendance.of(dateTime, systemDateTime);

        attendanceList.add(attendance);
    }

    public Optional<Attendance> findAttendance(String nickname, LocalDate date) {
        var attendances = getAttendances(nickname);
        return attendances.findAttendance(date);
    }

    public Attendances getAttendances(String nickname) {
        if (!attendances.containsKey(nickname)) {
            throw new AttendanceArgumentException(NOT_REGISTERED_NICKNAME);
        }
        return attendances.get(nickname);
    }

    public Set<String> getNicknameSet() {
        return attendances.keySet();
    }

}
