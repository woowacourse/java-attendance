package attendance;

import attendance.domain.AttendanceManager;
import attendance.domain.Nickname;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceFileParser {
    private static final String SPLIT_DELIMITER = ",";
    private static final int INDEX_AS_CREW_NICKNAME = 0;
    private static final int INDEX_AS_ATTENDANCE_DATE_TIME = 1;
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";

    private AttendanceFileParser() {
    }

    public static void initAttendances(final BufferedReader bufferedReader,
                                       final AttendanceManager attendanceManager) throws IOException {
        bufferedReader.readLine();
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(SPLIT_DELIMITER);
            Nickname crewNickname = new Nickname(split[INDEX_AS_CREW_NICKNAME]);
            Nickname addedCrewNickname = attendanceManager.addCrew(crewNickname);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
            String attendanceDateTime = split[INDEX_AS_ATTENDANCE_DATE_TIME];
            LocalDate attendanceDate = LocalDate.parse(attendanceDateTime, dateTimeFormatter);
            LocalTime attendanceTime = LocalTime.parse(attendanceDateTime, dateTimeFormatter);
            attendanceManager.addAttendance(addedCrewNickname, attendanceDate, attendanceTime);
        }
    }
}
