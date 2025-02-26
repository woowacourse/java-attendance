import java.time.LocalDateTime;

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
    }
}
