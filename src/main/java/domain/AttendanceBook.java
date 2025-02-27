package domain;

import domain.policy.AttendancePolicy;
import reader.AttendanceFileReader;
import reader.FileReadException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {

    private final Map<Nickname, Attendances> nicknameToAttendances;
    private final AttendancePolicy attendancePolicy;

    private AttendanceBook(Map<Nickname, Attendances> nicknameToAttendances, AttendancePolicy attendancePolicy) {
        this.nicknameToAttendances = nicknameToAttendances;
        this.attendancePolicy = attendancePolicy;
    }

    public static AttendanceBook initialize(AttendancePolicy attendancePolicy) {
        return new AttendanceBook(new HashMap<>(), attendancePolicy);
    }

    public void loadAttendance(AttendanceFileReader attendanceFileReader,
                               String filePath) throws FileReadException {

        Map<String, Map<LocalDate, LocalTime>> rawAttendances = attendanceFileReader.read(filePath).parseData();

        Map<Nickname, Attendances> nicknameToAttendances = new HashMap<>();
        writeNickname(rawAttendances, nicknameToAttendances);
        writeAttendances(rawAttendances, nicknameToAttendances);
    }

    private void writeNickname(Map<String, Map<LocalDate, LocalTime>> rawAttendances,
                               Map<Nickname, Attendances> nicknameToAttendances) {
        rawAttendances.keySet().stream()
                .map(Nickname::from)
                .forEach(nickname -> nicknameToAttendances.put(nickname, Attendances.create()));
    }

    private void writeAttendances(Map<String, Map<LocalDate, LocalTime>> rawAttendances,
                                  Map<Nickname, Attendances> nicknameToAttendances) {
        rawAttendances.forEach((nickname, dateTime) ->
                writeAttendance(nicknameToAttendances, nickname, dateTime)
        );
    }

    private void writeAttendance(Map<Nickname, Attendances> nicknameToAttendances,
                                 String nickname,
                                 Map<LocalDate, LocalTime> dateTime) {
        dateTime.forEach((date, time) -> {
            Attendances attendances = nicknameToAttendances.get(Nickname.from(nickname));
            attendances.add(Attendance.of(
                    AttendanceDate.of(date, attendancePolicy),
                    AttendanceTime.of(time, attendancePolicy)));
        });
    }
}
