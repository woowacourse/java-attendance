package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 매니저 테스트")
class CrewAttendanceManagerTest {

    private final CrewAttendanceManager attendanceManager = new CrewAttendanceManager();

    @Test
    @DisplayName("닉네임과 입력 시간으로 크루의 출석을 등록한다")
    void 닉네임과_입력_시간으로_크루의_출석을_등록한다() {
        // given
        String nickname = "비타";
        LocalDate nowDate = LocalDate.now();
        LocalDateTime attendanceDateTime = LocalDateTime.of(nowDate, LocalTime.of(10, 0));

        Attendance attendance = new Attendance(LocalDateTime.of(nowDate, LocalTime.MAX));
        Attendances attendances = new Attendances(List.of(attendance));

        attendanceManager.addNewCrew(nickname, attendances);

        // when
        Attendance result = attendanceManager.processAttendanceCheck(nickname, attendanceDateTime);

        // then
        assertThat(result.getDateTime())
                .isEqualTo(attendanceDateTime);
    }
}
