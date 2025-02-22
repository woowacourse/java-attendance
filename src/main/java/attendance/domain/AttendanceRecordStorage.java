package attendance.domain;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceRecordStorage {

    private final Map<String, List<AttendanceRecord>> attendanceRecords = new HashMap<>();

    public void add(AttendanceRecord record) {
        validateNotSaved(record.getNickname(), record.getDate());
        if (!record.isExpulsion()) {
            List<AttendanceRecord> records = findRecordsByNickname(record.getNickname());
            records.add(record);
            attendanceRecords.put(record.getNickname(), records);
        }
    }

    public void update(AttendanceRecord newRecord) {
        remove(newRecord.getNickname(), newRecord.getDate());
        add(newRecord);
    }

    public Optional<AttendanceRecord> find(String nickname, LocalDate date) {
        List<AttendanceRecord> records = findRecordsByNickname(nickname);
        return records.stream().filter(record -> record.checkSameDate(date))
                .findAny();
    }

    public List<AttendanceRecord> findUnmodifiedRecordsByNickname(String nickname, Month month) {
        List<AttendanceRecord> records = findRecordsByNickname(nickname);
        return records.stream().filter(record -> record.isInMonth(month)).toList();
    }

    public int calculateAttendanceCount(String nickname, LocalDate startDate, LocalDate endDate) { // TODO: 테스트 추가
        List<AttendanceRecord> records = findRecordsByNickname(nickname);
        List<AttendanceRecord> inPeriod = records.stream()
                .filter(record -> record.isInPeriod(startDate, endDate)).toList();
        return (int) inPeriod.stream()
                .filter(record -> record.getType() == AttendanceStatusType.ATTENDANCE).count();
    }

    public int calculateLateCount(String nickname, LocalDate startDate, LocalDate endDate) {// TODO: 테스트 추가
        List<AttendanceRecord> records = findRecordsByNickname(nickname);
        List<AttendanceRecord> inPeriod = records.stream()
                .filter(record -> record.isInPeriod(startDate, endDate)).toList();
        return (int) inPeriod.stream()
                .filter(record -> record.getType() == AttendanceStatusType.LATE).count();
    }


    private void remove(String nickname, LocalDate date) {
        Optional<AttendanceRecord> originRecord = find(nickname, date);
        if (originRecord.isPresent()) {
            List<AttendanceRecord> originRecords = attendanceRecords.getOrDefault(nickname, new ArrayList<>());
            originRecords.remove(originRecord.get());
        }
    }

    private List<AttendanceRecord> findRecordsByNickname(String nickname) {
        return attendanceRecords.getOrDefault(nickname, new ArrayList<>());
    }

    private void validateNotSaved(String nickname, LocalDate date) {
        Optional<AttendanceRecord> record = find(nickname, date);
        if (record.isPresent()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }
}
