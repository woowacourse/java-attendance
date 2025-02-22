package attendance.domain;

import attendance.dto.WarnedStudentResponse;
import attendance.dto.WarnedStudentResponses;
import attendance.utility.DateGenerator;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class AttendanceManager {

    private final Map<String, Attendances> attendances;
    private final HolidayChecker holidayChecker;
    private final DateGenerator dateGenerator;

    public AttendanceManager(HolidayChecker holidayChecker, DateGenerator dateGenerator) {
        this.attendances = new HashMap<>();
        this.holidayChecker = holidayChecker;
        this.dateGenerator = dateGenerator;
    }

    public void addCrew(String name) {
        int dayAllCount = dateGenerator.now().lengthOfMonth();

        Attendances newAttendances = new Attendances();
        IntStream.range(1, dayAllCount + 1)
                .mapToObj(index -> dateGenerator.now().withDayOfMonth(index))
                .filter(date -> !holidayChecker.isHoliday(date))
                .forEach(date -> newAttendances.add(new Attendance(date)));

        attendances.put(name, newAttendances);
    }

    public WarnedStudentResponses searchWarnedCrews(LocalDate date) {
        Set<String> nicknames = attendances.keySet();
        List<WarnedStudentResponse> responses = nicknames.stream()
                .map(nickname -> new WarnedStudentResponse(nickname,
                        attendances.get(nickname).createCountUntilYesterday(date)))
                .filter(response -> !response.attendanceGroupByStatus().warning()
                        .equals(AttendanceWarningType.NONE.getName()))
                .toList();

        return new WarnedStudentResponses(responses.stream().sorted().toList());
    }

    public boolean isNotContainNickname(String nickname) {
        return !attendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(String nickname) {
        if (isNotContainNickname(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return attendances.get(nickname);
    }
}
