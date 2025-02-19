package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

public class Student {

    public LinkedHashMap<LocalDateTime, AttendanceStatus> record = new LinkedHashMap<>();
    public String name;
    public int absent;
    public int attendance;
    public int late;

    public int getLate() {
        return late;
    }

    public String getName() {
        return name;
    }

    public int getAbsent() {
        return absent;
    }

    public Student(String name) {
        this.name = name;
    }

    public void isStartTime(LocalTime localTime) {
        if (localTime.isAfter(LocalTime.of(23,0)) || localTime.isBefore(LocalTime.of(8,0))){
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public void updateState(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        int day = dayOfWeek.getValue();
        if (day == 6 || day == 7 || localDateTime.getDayOfMonth() == 25) {
            return;
        }

        AttendanceStatus attendanceStatus = AttendanceCalculatorByDay.
                attendanceCalculator(day, LocalTime.from(localDateTime));

        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1, localDateTime)) {
                if (record.get(localDateTime1).equals(AttendanceStatus.ATTENDANCE)) {
                    attendance --;
                    record.remove(localDateTime1);
                    record.putIfAbsent(localDateTime, attendanceStatus);
                    return;
                }

                if (record.get(localDateTime1).equals(AttendanceStatus.LATE)) {
                    late --;
                    record.remove(localDateTime1);
                    record.putIfAbsent(localDateTime, attendanceStatus);
                    return;
                }

                if (record.get(localDateTime1).equals(AttendanceStatus.ABSENT)) {
                    absent --;
                    record.remove(localDateTime1);
                    record.putIfAbsent(localDateTime, attendanceStatus);
                    return;
                }
            }
        }

        record.putIfAbsent(localDateTime, attendanceStatus);

        assert attendanceStatus != null;
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)){
            attendance++;
            return;
        }
        if (attendanceStatus.equals(AttendanceStatus.LATE)){
            late++;
            return;
        }
        absent++;
    }

    public LinkedHashMap<LocalDateTime, AttendanceStatus> getRecord() {
        return record;
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        LocalDateTime dayDate1 = localDateTime1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = localDateTime2.truncatedTo(ChronoUnit.DAYS);

        return (dayDate1.compareTo(dayDate2) == 0);
    }

    public LocalDateTime findLocalDateTime(LocalDateTime localDateTime) {
        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1,localDateTime)) {
                return localDateTime1;
            }
        }
        return null;
    }

    public String findStateByLocalDateTime(LocalDateTime localDateTime) {
        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1,localDateTime)) {
                return record.get(localDateTime1).getState();
            }
        }
        return null;
    }

    public int calculateAbsent() {
        return absent + late/3;
    }

}
