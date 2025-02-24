package domain;

import domain.rule.AbsentRule;
import util.FormatUtil;
import util.TimeMachine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<String, Attendances> attendanceBook;

    private AttendanceBook(Map<String, Attendances> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public static AttendanceBook initialize(List<String> attendanceData) {
        Map<String, Attendances> nicknameToAttendances = new HashMap<>();
        loadData(attendanceData, nicknameToAttendances);
        return new AttendanceBook(nicknameToAttendances);
    }

    private static void loadData(List<String> attendanceData,
                                 Map<String, Attendances> nicknameToAttendances) {
        attendanceData.stream()
                .map(attendance -> attendance.split(FormatUtil.ATTENDANCE_DATA_DELIMITER))
                .forEach(data -> writeData(data, nicknameToAttendances));
    }

    private static void writeData(String[] data,
                                  Map<String, Attendances> nicknameToAttendances) {
        String nickname = data[FormatUtil.NICKNAME_INDEX];
        LocalDateTime attendanceDateTime = FormatUtil.parseLocalDateTIme(data[FormatUtil.DATE_TIME_INDEX]);

        AttendanceDate attendanceDate = AttendanceDate.from(attendanceDateTime.toLocalDate());
        AttendanceTime attendanceTime = AttendanceTime.from(attendanceDateTime.toLocalTime());

        nicknameToAttendances.computeIfAbsent(nickname, incomingNickname -> Attendances.create());
        nicknameToAttendances.get(nickname).add(attendanceDate, attendanceTime);
    }

    public Attendance add(String nickname, Attendance attendance) {
        if (!existsByNickname(nickname)) {
            attendanceBook.put(nickname, Attendances.create());
        }

        Attendances attendances = attendanceBook.get(nickname);
        return attendances.add(attendance.attendanceDate(), attendance.attendanceTime());
    }

    public Attendances findAllByNickname(String nickname) {
        if (existsByNickname(nickname)) {
            return attendanceBook.get(nickname);
        }
        throw new IllegalArgumentException("해당 닉네임으로 출석된 기록이 없습니다.");
    }

    public ExpulsionCandidates findExpulsionCandidates() {
        return ExpulsionCandidates.from(attendanceBook.entrySet().stream()
                .map(expulsionCandidateAttendances ->
                        expulsionCandidateAttendances.getValue().calculateStatistics(expulsionCandidateAttendances.getKey(), TimeMachine.dateOfNow()))
                .filter(attendanceStatistics -> AbsentRule.calculateAbsentPolicy(attendanceStatistics).isRiskOfExpulsion())
                .toList());
    }

    private boolean existsByNickname(String nickname) {
        return attendanceBook.containsKey(nickname);
    }
}
