package domain;

import domain.policy.absent.AbsentRule;
import reader.AttendanceFileReader;
import reader.FileReadException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {

    private final Map<Nickname, Attendances> nicknameToAttendances;

    private AttendanceBook(Map<Nickname, Attendances> nicknameToAttendances) {
        this.nicknameToAttendances = nicknameToAttendances;
    }

    public static AttendanceBook initialize() {
        return new AttendanceBook(new HashMap<>());
    }

    public void loadAttendance(AttendanceFileReader attendanceFileReader,
                               String filePath) throws FileReadException {

        Map<String, Map<LocalDate, LocalTime>> rawAttendances = attendanceFileReader.read(filePath).parseData();

        writeNickname(rawAttendances);
        writeAttendances(rawAttendances);
    }

    public Attendances findByNickname(Nickname nickname) {
        if (existsByNickname(nickname)) {
            return nicknameToAttendances.get(nickname);
        }
        throw new IllegalArgumentException("해당 닉네임으로 출석된 기록이 없습니다.");
    }

    public Attendance add(Nickname nickname, Attendance attendance) {
        if (!existsByNickname(nickname)) {
            nicknameToAttendances.put(nickname, Attendances.initialize());
        }

        Attendances attendances = nicknameToAttendances.get(nickname);
        return attendances.add(attendance);
    }

    public AttendanceStatistics findExpulsionCandidates() {
        return AttendanceStatistics.from(nicknameToAttendances.entrySet().stream()
                .map(attendancesByNickname ->
                        attendancesByNickname.getValue().calculateAttendanceCounts(attendancesByNickname.getKey()))
                .filter(attendanceStatistics -> AbsentRule.calculateAbsentPolicy(attendanceStatistics).isRiskOfExpulsion())
                .toList());
    }

    private void writeNickname(Map<String, Map<LocalDate, LocalTime>> rawAttendances) {
        rawAttendances.keySet().stream()
                .map(Nickname::from)
                .forEach(nickname -> nicknameToAttendances.put(nickname, Attendances.initialize()));
    }

    private void writeAttendances(Map<String, Map<LocalDate, LocalTime>> rawAttendances) {
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
                    AttendanceDate.from(date),
                    AttendanceTime.from(time)));
        });
    }

    private boolean existsByNickname(Nickname nickname) {
        return nicknameToAttendances.containsKey(nickname);
    }
}
