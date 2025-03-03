package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import util.DateTimeConvertor;

public class AttendanceBook {

    private static final int LATES_COUNT_PER_ABSENCE = 3;
    private final Map<String, AttendancePaper> attendancePapers;

    private AttendanceBook(final Map<String, AttendancePaper> attendancePapers) {
        this.attendancePapers = attendancePapers;
    }

    public static AttendanceBook create() {
        return new AttendanceBook(AttendancePaperGenerator.generate());
    }

    public AttendanceRecord addAttendance(final String crewName, final LocalDateTime localDateTime) {
        final AttendancePaper attendancePaper = attendancePapers.get(crewName);
        return attendancePaper.addAttendance(localDateTime);
    }

    public AttendancePaper getAttendancePaperByCrewName(final String crewName) {
        return Optional.ofNullable(attendancePapers.get(crewName))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public void validateExistCrew(final String crewName) {
        if (!attendancePapers.containsKey(crewName)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void validateExistAttendance(final LocalDate localDate, final String crewName) {
        if (attendancePapers.get(crewName).existAttendance(localDate)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s 출석 기록이 존재합니다. 수정 기능을 이용해주세요",
                            DateTimeConvertor.convertToLocalDateKoreanFormat(
                                    localDate)));
        }
    }

    public void validateModificationAttendanceDate(final String name, final LocalDate localDate) {
        final AttendancePaper attendancePaper = attendancePapers.get(name);
        if (!attendancePaper.existAttendance(localDate)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다.");
        }
    }

    public AttendanceModification modifyAttendance(final String name, final LocalDate localDate,
                                                   final AttendanceTime attendanceTime) {
        final AttendancePaper attendancePaper = attendancePapers.get(name);
        final LocalTime localtime = attendanceTime.getLocalTime();
        final AttendanceRecord beforeAttendanceRecord = attendancePaper.getAttendanceRecordByDate(localDate);
        attendancePaper.addAttendance(LocalDateTime.of(localDate, localtime));
        final AttendanceRecord afterAttendanceRecord = attendancePaper.getAttendanceRecordByDate(localDate);
        return new AttendanceModification(beforeAttendanceRecord, afterAttendanceRecord);
    }

    public List<AttendanceRecord> lookUpAttendanceHistory(final String name) {
        return attendancePapers.get(name).getAttendanceRecords();
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(final String name) {
        final List<AttendanceRecord> attendanceRecords = lookUpAttendanceHistory(name);
        return attendanceRecords.stream()
                .collect(Collectors.groupingBy(
                        AttendanceRecord::status,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }

    public Penalty calculatePenalty(final String name) {
        final Map<AttendanceStatus, Integer> countAttendanceStatus = countAttendanceStatus(name);
        return Penalty.findByAbsenceCount((countAttendanceStatus.get(AttendanceStatus.ABSENCE)
                + countAttendanceStatus.get(AttendanceStatus.LATE) / LATES_COUNT_PER_ABSENCE));
    }
}
