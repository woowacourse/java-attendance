package domain;

import dto.AttendanceRecord;
import dto.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import util.DateTimeUtil;

public class Attendance {

    //    private final AttendanceResult attendanceResult;
    private final Map<Crew, List<AttendanceRecord>> attendanceMap;

    public Attendance(final Map<Crew, List<AttendanceRecord>> attendanceMap) {
//        this.attendanceResult = attendanceResult;
        this.attendanceMap = attendanceMap;
    }

    public Crew getCrewByName(String name) {
        return attendanceMap.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 입니다."));
    }

    public List<AttendanceRecord> getRecordByCrew(Crew crew) {
        return attendanceMap.get(crew);
    }

    public Map<Crew, List<AttendanceRecord>> getAttendanceMap() {
        return attendanceMap;
    }

    public void save(final Crew crew, final String schoolStartTime, LocalDate localDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<AttendanceRecord> records = attendanceMap.get(crew);

        String today = String.format("%d-%02d-%02d %s",
                DateTimeUtil.getYearBy(localDate),
                DateTimeUtil.getMonthBy(localDate),
                DateTimeUtil.getDateBy(localDate),
                schoolStartTime);
        LocalDate todayLocalDate = LocalDateTime.parse(today, formatter).toLocalDate();
        LocalTime todayLocalTime = LocalDateTime.parse(today, formatter).toLocalTime();
        AttendanceState state = AttendanceState.findStateBy(todayLocalTime, todayLocalDate);

        Time time = new Time(todayLocalTime, state);
        AttendanceRecord record = new AttendanceRecord(todayLocalDate, time);

        // 수정 필요
        validateDuplicateSave(24, records);
//        validateDuplicateSave(DateTimeUtil.getDateBy(LocalDate.now()), localDateTimes);

//        localDateTimes.add(todayLocalDateTime);
//        attendanceMap.put(crew, localDateTimes);
        records.add(record);
        attendanceMap.put(crew, records);
    }

    private void validateDuplicateSave(final int todayDay, final List<AttendanceRecord> records) {

        for (AttendanceRecord record : records) {
            LocalDate date = record.date();
            int dayOfMonth = DateTimeUtil.getDateBy(date);
//            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayOfMonth == todayDay) {
                throw new IllegalArgumentException("이미 출석한 크루입니다.");
            }
        }
    }

    public LocalTime update(final Crew crew, final String updateTime, final int date) {
        DateTimeUtil.validateHolyDay(date);

        List<AttendanceRecord> attendanceRecords = attendanceMap.get(crew);
        int i;
        LocalTime beforeLocalTime = null;
        for (i = 0; i < attendanceRecords.size(); i++) {
            AttendanceRecord record = attendanceRecords.get(i);
            int dayOfMonth = DateTimeUtil.getDateBy(record.date());
//            int dayOfMonth = localDateTime.getDayOfMonth();
            if (dayOfMonth == date) {
                beforeLocalTime = record.time().time();
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String today = String.format("2024-12-%02d %s", date, updateTime);
        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        LocalDate todayLocalDate = todayLocalDateTime.toLocalDate();
        LocalTime todayLocalTime = todayLocalDateTime.toLocalTime();
        AttendanceState state = AttendanceState.findStateBy(todayLocalTime, todayLocalDate);

        Time time = new Time(todayLocalTime, state);
        AttendanceRecord record = new AttendanceRecord(todayLocalDate, time);
        attendanceRecords.set(i, record);

        return beforeLocalTime;
    }
}
