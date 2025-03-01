package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendanceBook {

    private final Crews crews;
    private final List<Attendance> attendances;

    public AttendanceBook(Crews crews, Attendance... attendance) {
        this(crews, new ArrayList<>(List.of(attendance)));
    }

    public AttendanceBook(Crews crews, List<Attendance> attendances) {
        this.crews = crews;
        this.attendances = attendances;
    }

    public void attend(String nickname, LocalDateTime attendanceDateTime) {
        if (!crews.contains(nickname)) {
            throw new IllegalArgumentException(nickname + "은 등록되지 않은 닉네임입니다.");
        }
        Attendance newAttendance = new Attendance(nickname, attendanceDateTime);
        if (isAlreadyAttend(newAttendance)) {
            throw new IllegalArgumentException("이미 출석한 경우 다시 출석할 수 없습니다.");
        }
        attendances.add(newAttendance);
    }

    private boolean isAlreadyAttend(Attendance newAttendance) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isAlreadyAttend(newAttendance));
    }

    public void updateAttendance(String nickname, LocalDateTime updateDateTime) {
        findAttendance(nickname, updateDateTime)
                .ifPresentOrElse(
                        attendance -> attendance.updateAttendanceTime(updateDateTime.toLocalTime()),
                        () -> attendances.add(new Attendance(nickname, updateDateTime))
                );
    }

    private Optional<Attendance> findAttendance(String nickname, LocalDateTime updateDateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttend(
                        nickname,
                        new AttendanceDate(updateDateTime.toLocalDate())))
                .findFirst();
    }

    public CrewAttendance findAttendancesByNickname(String nickname) {
        List<Attendance> attendances = this.attendances.stream()
                .filter(attendance -> attendance.isEqualNickname(nickname))
                .toList();
        return new CrewAttendance(nickname, attendances);
    }

    public CrewAttendances createCrewAttendances() {
        List<CrewAttendance> crewAttendances = attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getNickname))
                .entrySet()
                .stream()
                .map(entry -> new CrewAttendance(entry.getKey(), entry.getValue()))
                .toList();
        return new CrewAttendances(crewAttendances);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AttendanceBook that = (AttendanceBook) object;
        return attendances.equals(that.attendances);
    }

    @Override
    public int hashCode() {
        return attendances.hashCode();
    }
}
