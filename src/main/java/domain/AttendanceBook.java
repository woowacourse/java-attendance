package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.DateTimeConvertor;

public class AttendanceBook {

    private final Map<String, Crew> crews;

    private AttendanceBook(final Map<String, Crew> crews) {
        this.crews = crews;
    }

    public static AttendanceBook create() {
        return new AttendanceBook(CrewsGenerator.generate());
    }

    public AttendanceRecord addAttendance(final String crewName, final LocalDateTime localDateTime) {
        final Crew crew = crews.get(crewName);
        return crew.putAttendance(localDateTime);
    }

    public void validateExistCrew(final String crewName) {
        if (!crews.containsKey(crewName)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void validateExistAttendance(final LocalDate localDate, final String crewName) {
        if (crews.get(crewName).existAttendance(localDate)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s 출석 기록이 존재합니다. 수정 기능을 이용해주세요", DateTimeConvertor.convertToLocalDateKoreanFormat(
                            localDate)));
        }
    }

    public void validateModificationAttendanceDate(final String name, final LocalDate localDate) {
        final Crew crew = crews.get(name);
        if (!crew.existAttendance(localDate)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다.");
        }
    }

    public AttendanceModification modifyAttendance(final String name, final LocalDate localDate, final AttendanceTime attendanceTime) {
        final Crew crew = crews.get(name);
        final LocalTime localtime = attendanceTime.getLocalTime();
        final AttendanceRecord beforeAttendanceRecord = crew.getAttendanceRecordByDate(localDate);
        crew.putAttendance(LocalDateTime.of(localDate, localtime));
        final AttendanceRecord afterAttendanceRecord = crew.getAttendanceRecordByDate(localDate);
        return new AttendanceModification(beforeAttendanceRecord, afterAttendanceRecord);
    }

    public List<AttendanceRecord> lookUpAttendanceHistory(final String name) {
        return crews.get(name).getAttendanceRecords();
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(final String name) {
        return null;
    }
}
