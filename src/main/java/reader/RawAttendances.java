package reader;

import util.FormatUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record RawAttendances(
        List<RawAttendance> data
) {
    public static final String ATTENDANCE_DATA_DELIMITER = ",";
    public static final int NICKNAME_INDEX = 0;
    public static final int DATE_TIME_INDEX = 1;

    public static RawAttendances from(List<RawAttendance> data) {
        return new RawAttendances(data);
    }

    public Map<String, Map<LocalDate, LocalTime>> parseData() {
        Map<String, Map<LocalDate, LocalTime>> nicknameToDateTime = new HashMap<>();

        data.stream()
                .map(attendance -> attendance.splitByDelimiter(ATTENDANCE_DATA_DELIMITER))
                .forEach(data -> groupingData(data, nicknameToDateTime));

        return nicknameToDateTime;
    }

    private void groupingData(String[] data,
                                     Map<String, Map<LocalDate, LocalTime>> nicknameToDateTime) {
        String nickname = data[NICKNAME_INDEX];
        LocalDateTime attendanceDateTime = LocalDateTime.parse(data[DATE_TIME_INDEX], FormatUtil.DATE_TIME_FORMATTER);

        nicknameToDateTime.computeIfAbsent(nickname, incomingNickname -> new HashMap<>());
        nicknameToDateTime.get(nickname)
                .put(attendanceDateTime.toLocalDate(), attendanceDateTime.toLocalTime());
    }
}
