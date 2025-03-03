package fixture;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import domain.AttendanceHistories;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistoriesFixture {
    public static AttendanceHistories createWithSingleAttendance(Crew crew, LocalDateTime dateTime) {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(dateTime);
        AttendanceDateTimes attendanceDateTimes = new AttendanceDateTimes(new ArrayList<>(List.of(attendanceDateTime)));
        return new AttendanceHistories(new HashMap<>(Map.of(crew, attendanceDateTimes)));
    }

    public static AttendanceHistories createWithMultipleAttendance(Crew crew, LocalDate startDate,
                                                                   int presentCount,
                                                                   int tardyCount,
                                                                   int absentCount) {
        AttendanceDateTimes attendanceDateTimes = AttendanceDateTimesFixture.of(startDate, presentCount, tardyCount,
                absentCount);
        return new AttendanceHistories(new HashMap<>(Map.of(crew, attendanceDateTimes)));
    }

    public static AttendanceHistories createDisciplinedCrewsHistory(LocalDate startDate, int totalDate) {
        Map<Crew, AttendanceDateTimes> history = new HashMap<>();
        history.put(new Crew("경고크루"), AttendanceDateTimesFixture.of(startDate, totalDate - 2, 0, 2));
        history.put(new Crew("제적크루"), AttendanceDateTimesFixture.of(startDate, totalDate - 6, 0, 6));
        history.put(new Crew("면담크루"), AttendanceDateTimesFixture.of(startDate, totalDate - 3, 0, 3));
        history.put(new Crew("해당사항없음"), AttendanceDateTimesFixture.of(startDate, totalDate, 0, 0));
        return new AttendanceHistories(history);
    }
}
