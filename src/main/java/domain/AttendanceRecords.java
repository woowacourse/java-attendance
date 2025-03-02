package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AttendanceRecords {

    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public AttendanceRecords() {
    }

    public AttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords.addAll(attendanceRecords);
    }

    public void addIfAbsent(AttendanceRecord attendanceRecord) {
        if (existsByCrewAndDate(attendanceRecord.getCrew(), attendanceRecord.getDate())) {
            throw new IllegalArgumentException("이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
        attendanceRecords.add(attendanceRecord);
    }

    public void overwriteAttendanceRecord(AttendanceRecord after) {
        attendanceRecords.removeIf(record ->
                after.getCrew().equals(record.getCrew()) && after.getDate().equals(record.getDate()));
        attendanceRecords.add(after);
    }

    public boolean existsByCrewAndDate(Crew crew, LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch(record -> crew.equals(record.getCrew())
                        && date.equals(record.getDate()));
    }

    public AttendanceRecord getOneByCrewAndDate(Crew crew, LocalDate date) {
        return attendanceRecords.stream()
                .filter(record -> crew.equals(record.getCrew())
                        && date.equals(record.getDate()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public AbstractAttendanceRecord findOneByCrewAndDate(Crew crew, LocalDate date) {
        if (existsByCrewAndDate(crew, date)) {
            return getOneByCrewAndDate(crew, date);
        }
        return EmptyAttendanceRecord.of(crew, date);
    }

    public List<Crew> findAllDistinctCrews() {
        return attendanceRecords.stream()
                .map(AttendanceRecord::getCrew)
                .distinct()
                .toList();
    }

    public List<AbstractAttendanceRecord> getByCrewFromTo(Crew crew, LocalDate from, LocalDate to) {
        return Stream.iterate(from, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to) + 1)
                .filter(LectureTime::isLectureDate)
                .map(date -> findOneByCrewAndDate(crew, date))
                .toList();
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatusCount(Crew crew, LocalDate from, LocalDate to) {
        List<AbstractAttendanceRecord> records = getByCrewFromTo(crew, from, to);
        Map<AttendanceStatus, Integer> statusCount = new HashMap<>();
        Arrays.stream(AttendanceStatus.values()).forEach(status -> statusCount.put(status, 0));
        for (AbstractAttendanceRecord record : records) {
            int count = statusCount.get(record.getStatus());
            statusCount.put(record.getStatus(), count + 1);
        }
        return statusCount;
    }
}
