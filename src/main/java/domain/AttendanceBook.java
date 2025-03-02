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
        final Crew crew = crewGroup.getCrewByName(crewName);
        return crew.putAttendance(localDateTime);
    }

    public void validateExistCrew(final String crewName) {
        if (!crewGroup.containsCrew(crewName)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void validateExistAttendance(final LocalDate localDate, final String crewName) {
        if (crewGroup.getCrewByName(crewName).existAttendance(localDate)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s 출석 기록이 존재합니다. 수정 기능을 이용해주세요", DateTimeConvertor.convertToLocalDateKoreanFormat(
                            localDate)));
        }
    }

    public AttendanceRecord modifyAttendance(final String name, final LocalDateTime localDateTime) {
        return null;
    }
}
