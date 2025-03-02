package domain;

import java.time.LocalDateTime;
import util.DateTimeConvertor;

public class AttendanceBook {

    private final CrewGroup crewGroup;

    private AttendanceBook(final CrewGroup crewGroup) {
        this.crewGroup = crewGroup;
    }

    public static AttendanceBook create() {
        return new AttendanceBook(new CrewGroup(CrewsGenerator.generate()));
    }

    public AttendanceRecord addAttendance(final String crewName, final LocalDateTime localDateTime) {
        final Crew crew = crewGroup.findByName(crewName);
        validateExistAttendance(localDateTime, crew);
        return crew.putAttendance(localDateTime);
    }

    public void validateExistCrew(final String crewName) {
        if (!crewGroup.containsCrew(crewName)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void validateExistAttendance(final LocalDateTime localDateTime, final Crew crew) {
        if (crew.existAttendance(localDateTime)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s 출석 기록이 존재합니다. 수정 기능을 이용해주세요", DateTimeConvertor.convertToLocalDateKoreanFormat(
                            localDateTime.toLocalDate())));
        }
    }

    public AttendanceRecord modifyAttendance(final String name, final LocalDateTime localDateTime) {
        return null;
    }
}
