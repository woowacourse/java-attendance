import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceSystemManager {

    private final AttendanceHistories attendanceHistories;
    private final Crews crews;

    public AttendanceSystemManager(AttendanceHistories attendanceHistories, Crews crews) {
        this.attendanceHistories = attendanceHistories;
        this.crews = crews;
    }

    public void create(String nickname, LocalDateTime requestedAt) {
        Crew crew = crews.findCrewByName(nickname);

        boolean existedHistory = attendanceHistories.checkExistenceByCrewAndDate(crew, requestedAt.toLocalDate());
        if (existedHistory) {
            throw new IllegalArgumentException("이미 존재하는 출석 기록입니다. 수정 기능을 이용해주세요.");
        }

        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, requestedAt);
        attendanceHistories.addNewHistory(attendanceHistory);

        AttendanceType attendanceType = AttendanceType.findAttendanceTypeByDateTime(requestedAt);
    }

    public void update(String nickname, LocalDateTime requestedAt) {
        Crew crew = crews.findCrewByName(nickname);

        AttendanceHistory oldAttendanceHistory = attendanceHistories.findByCrewAndDate(crew, requestedAt.toLocalDate());
        AttendanceHistory newAttendanceHistory = new AttendanceHistory(crew, requestedAt);

        attendanceHistories.update(oldAttendanceHistory, newAttendanceHistory);
    }

    public Map<LocalDateTime, AttendanceType> findAllHistoriesOfCrew(String nickname, LocalDateTime requestedAt) {
        Crew crew = crews.findCrewByName(nickname);

        List<AttendanceHistory> historiesOfCrew = attendanceHistories.findAllHistoriesOfCrewDateBefore(
                crew, requestedAt);

        return AttendanceTypeCounter.count(requestedAt.toLocalDate(), historiesOfCrew);
    }

}
