package attendance.domain.attendance;

import static attendance.common.utill.DateTimeFormatterWrapper.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import attendance.common.exception.AttendanceArgumentException;
import attendance.domain.StatusStatistic;

public record AttendanceBook(Map<String, Attendances> attendances, List<StatusStatistic> statusStatistics) {

    public static AttendanceBook from(List<String> lines) {
        Map<String, Attendances> attendances = new HashMap<>();
        for (String line : lines) {
            addAttendance(line, attendances);
        }
        return new AttendanceBook(attendances, new ArrayList<>());
    }

    private static void addAttendance(String line, Map<String, Attendances> attendances) {
        var lines = line.split(Constant.REGEX);
        var nickname = lines[0];

        Attendances attendanceList = attendances.computeIfAbsent(nickname, k -> new Attendances());

        var dateTime = LocalDateTime.parse(lines[1], getFormatter(Constant.DATETIME_FORMAT));
        var attendance = new Attendance(dateTime);

        attendanceList.add(attendance);
    }

    public Attendance findAttendance(String nickname, Attendance attendance) {
        var attendanceList = getAttendanceList(nickname);
        return attendanceList.findAttendance(attendance);
    }

    public Optional<Attendance> findAttendance(String nickname, LocalDate date) {
        var attendanceList = getAttendanceList(nickname);
        return attendanceList.findAttendance(date);
    }

    public Attendances getAttendanceList(String nickname) {
        if (!attendances.containsKey(nickname)) {
            throw new AttendanceArgumentException(Constant.NOT_REGISTERED_NICKNAME);
        }

        return attendances.get(nickname);
    }

    public void updateStatusStatistics() {
        for (String name : attendances.keySet()) {
            var statistic = StatusStatistic.of(attendances.get(name), name);
            statusStatistics.add(statistic);
        }

        Collections.sort(statusStatistics);
    }

    private static final class Constant {
        private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
        private static final String REGEX = ",";
        private static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";

        private Constant() {
        }
    }
}
