package attendance.domain;

import attendance.dto.response.WarnedStudent;
import attendance.dto.response.WarnedStudents;
import attendance.utility.DateGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class AttendanceManager {

    private final Map<String, Attendances> attendances;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;

    public AttendanceManager(Holiday holiday, DateGenerator dateGenerator) {
        this.attendances = new HashMap<>();
        this.holiday = holiday;
        this.dateGenerator = dateGenerator;
    }

    public void addCrew(String name) {
        int dayAllCount = dateGenerator.now().lengthOfMonth();

        Attendances newAttendances = new Attendances();
        IntStream.range(1, dayAllCount + 1)
                .mapToObj(index -> LocalDateTime.of(dateGenerator.now().withDayOfMonth(index), LocalTime.MIN))
                .filter(dateTime -> !holiday.isHoliday(dateTime.toLocalDate()))
                .forEach(dateTime -> newAttendances.add(new Attendance(dateTime)));

        attendances.put(name, newAttendances);
    }

    public Attendance processAttendanceCheck(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        return attendances.checkAndUpdateAttendance(dateTime);
    }

    public Attendance processAttendanceUpdate(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        return attendances.updateAttendance(dateTime);
    }

    public void validateNicknameExists(String nickname) {
        if (!containsNickname(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public WarnedStudents searchWarnedCrews(LocalDate date) {
        Set<String> nicknames = attendances.keySet();
        List<WarnedStudent> responses = nicknames.stream()
                .map(nickname -> new WarnedStudent(nickname,
                        attendances.get(nickname).createCountUntilYesterday(date)))
                .filter(response -> !response.groupByStatus().warning()
                        .equals(AttendanceWarningType.NONE.getName()))
                .toList();

        return new WarnedStudents(responses.stream().sorted().toList());
    }

    public boolean containsNickname(String nickname) {
        return attendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(String nickname) {
        return attendances.get(nickname);
    }
}
