package domain;

import dto.AttendanceRecordsResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> dailyAttendances;

    private Crew(String name) {
        this.name = name;
        this.dailyAttendances = new HashMap<>();
    }

    public static Crew createByName(String name) {
        return new Crew(name);
    }

    public void addDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = dateAndTime.keySet().stream()
                .findAny()
                .orElseThrow();

        validateIsNotAlreadyAttended(date);

        dailyAttendances.putAll(dateAndTime);
    }

    private void validateIsNotAlreadyAttended(LocalDate date) {
        if (dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 날짜입니다. 수정 기능을 이용해주세요.");
        }
    }

    public boolean hasName(String value) {
        return Objects.equals(name, value);
    }

    public String getName() {
        return name;
    }

    public void modifyDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = dateAndTime.keySet().stream()
                .findAny()
                .orElseThrow();

        if (!dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] %02d일 기록이 존재하지 않습니다.", date.getDayOfMonth()));
        }

        dailyAttendances.putAll(dateAndTime);
    }

    public List<AttendanceRecordsResponse> getAttendanceRecords() {
        List<AttendanceRecordsResponse> records = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            if (!Calendar.checkIsWorkingDay(day)) {
                continue;
            }
            LocalTime time = dailyAttendances.get(day);

            if (time == null) {
                records.add(new AttendanceRecordsResponse(LocalDate.of(2024, 12, day), null,
                        AttendanceStatus.NONE));
                continue;
            }

            //  없는 날에 --:--

            if (Calendar.isMonday(day)) {
                records.add(new AttendanceRecordsResponse(LocalDate.of(2024, 12, day), time,
                        AttendanceStatus.getInMonday(time)));
            }
            if (!Calendar.isMonday(day)) {
                records.add(new AttendanceRecordsResponse(LocalDate.of(2024, 12, day), time,
                        AttendanceStatus.getExceptMonday(time)));
            }
        }

        return records;
    }


}