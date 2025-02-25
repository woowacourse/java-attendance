package domain;

import dto.AttendanceRecord;
import dto.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import util.DateTimeUtil;

public class Attendance {

    private final Map<Crew, List<AttendanceRecord>> attendanceMap;

    public Attendance(final Map<Crew, List<AttendanceRecord>> attendanceMap) {
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
        // 결석인 날 '--:--' 추가
        List<AttendanceRecord> nonHolidaysAttendanceMap = modifyMap(attendanceMap.get(crew));
        List<AttendanceRecord> sortedAttendanceMap = sortByDate(nonHolidaysAttendanceMap);

        attendanceMap.put(crew, sortedAttendanceMap);

        return sortedAttendanceMap;
    }

    private List<AttendanceRecord> modifyMap(List<AttendanceRecord> attendanceRecords) {
        List<AttendanceRecord> modifiedRecords = new ArrayList<>(attendanceRecords);

        LocalDate today = LocalDate.of(2024, 12, DateTimeUtil.getTodayDate());
        List<LocalDate> workDays = IntStream.range(1, DateTimeUtil.getDateBy(today))
                .mapToObj(today::withDayOfMonth)
                .filter(date -> isWeekDay(date) && !containsAttendance(attendanceRecords, date))
                .toList();

        workDays.forEach(date -> modifiedRecords.add(
                new AttendanceRecord(date, new Time(LocalTime.of(0, 0), AttendanceState.ABSENCE))));

        return modifiedRecords;
    }

    private boolean isWeekDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private boolean containsAttendance(List<AttendanceRecord> attendanceRecords, LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch(record -> DateTimeUtil.getDateBy(record.date()) == date.getDayOfMonth());
    }


    private List<AttendanceRecord> sortByDate(List<AttendanceRecord> attendanceRecords) {
        return attendanceRecords.stream()
                .sorted(Comparator.comparing(AttendanceRecord::date))
                .toList();
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
        List<AttendanceRecord> attendanceRecords = new ArrayList<>(attendanceMap.get(crew)); // 불변 리스트를 가변 리스트로 복사

//        List<AttendanceRecord> attendanceRecords = attendanceMap.get(crew);
        int i;
        LocalTime beforeLocalTime = null;
        System.out.println(attendanceRecords.size());
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
        attendanceMap.put(crew, attendanceRecords);

        return beforeLocalTime;
    }
}
