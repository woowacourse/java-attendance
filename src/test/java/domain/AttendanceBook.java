package domain;

import java.time.LocalDateTime;
import util.DateTimeParser;

public class AttendanceBook {

    private final CrewGroup crewGroup;

    private AttendanceBook(final CrewGroup crewGroup) {
        this.crewGroup = crewGroup;
    }

    public static AttendanceBook create() {
        return new AttendanceBook(new CrewGroup(CrewsGenerator.generate()));
    }

    public void checkAttendance(final String crewName, final LocalDateTime localDateTime) {
        final Crew crew = crewGroup.findByName(crewName);
        validateExistAttendance(localDateTime, crew);
        crew.putAttendance(localDateTime);
    }

    private static void validateExistAttendance(final LocalDateTime localDateTime, final Crew crew) {
        if (crew.existAttendance(localDateTime)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s 출석 기록이 존재합니다. 수정 기능을 이용해주세요", DateTimeParser.parseToLocalDateKoreanFormat(
                            localDateTime.toLocalDate())));
        }
    }
}
