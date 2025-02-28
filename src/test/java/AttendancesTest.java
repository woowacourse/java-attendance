import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @DisplayName("크루들의 출석 기록을 가질 수 있다.")
    @Test
    void create() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        //when
        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        //then
        assertThat(attendances.getAttendances()).hasSize(2);
    }

    @DisplayName("닉네임과 일치하는 크루의 출석부를 반환한다.")
    @Test
    void findCrewBy() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";

        //when
        Attendance attendance = attendances.findCrewBy(name);

        //then
        assertThat(attendance).isEqualTo(attendances1);
    }

    @DisplayName("닉네임과 등교시간을 받아 출석을 한다.")
    @Test
    void attendanceCheckBy() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDateTime time = LocalDateTime.of(2024, 12, 12, 10, 10);

        //when //then
        assertThatCode(() -> attendances.checkAttendance(name, time))
                .doesNotThrowAnyException();
    }

    private Attendance createAttendance(final String name) {
        Crew crew = Crew.of(name);

        List<LocalDateTime> attendanceTime = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 10, 10);
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 11, 11);

        attendanceTime.add(localDateTime);
        attendanceTime.add(localDateTime1);

        return new Attendance(crew, attendanceTime);
    }

}
