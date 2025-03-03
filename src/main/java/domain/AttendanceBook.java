package domain;

import domain.policy.absent.AbsentRule;
import reader.AttendanceFileReader;
import reader.exception.FileReadException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
        Map<String, List<LocalDateTime>> rawAttendances = attendanceFileReader.read(filePath).parseData();

        writeNickname(rawAttendances);
        writeAttendances(rawAttendances);
    }

    public Attendances findByNickname(Nickname nickname) {
        if (hasNickname(nickname)) {
            return nicknameToAttendances.get(nickname);
        }
        throw new IllegalArgumentException("해당 닉네임으로 출석된 기록이 없습니다.");
    }

    public Attendance add(Nickname nickname, Attendance attendance) {
        if (!hasNickname(nickname)) {
            nicknameToAttendances.put(nickname, Attendances.initialize());
        }

        Attendances attendances = nicknameToAttendances.get(nickname);
        return attendances.add(attendance);
    }

    public AttendanceStatistics findExpulsionCandidates() {
        return AttendanceStatistics.from(
                nicknameToAttendances.keySet().stream()
                        .map(nickname -> nicknameToAttendances.get(nickname)
                                .calculateAttendanceCounts(nickname))
                        .filter(AbsentRule::isRiskOfExpulsion)
                        .toList());
    }


    private void writeNickname(Map<String, List<LocalDateTime>> rawAttendances) {
        rawAttendances.keySet().stream()
                .map(Nickname::from)
                .forEach(nickname -> nicknameToAttendances.put(nickname, Attendances.initialize()));
    }

    private void writeAttendances(Map<String, List<LocalDateTime>> rawAttendances) {
        rawAttendances.forEach((nickname, dateTime) ->
                writeAttendance(nicknameToAttendances, nickname, dateTime)
        );
    }

    private void writeAttendance(Map<Nickname, Attendances> nicknameToAttendances,
                                 String nickname,
                                 List<LocalDateTime> dateTimes) {
        dateTimes.forEach(dateTime -> {
                    Attendances attendances = nicknameToAttendances.get(Nickname.from(nickname));
                    attendances.add(Attendance.of(
                            AttendanceDate.from(dateTime.toLocalDate()),
                            AttendanceTime.from(dateTime.toLocalTime())));
                }
        );
    }

    private boolean hasNickname(Nickname nickname) {
        return nicknameToAttendances.containsKey(nickname);
    }
}
