package attendance.domain;

import attendance.domain.dto.ModifyAttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberAttendanceTest {

    private List<Attendance> attendances = new ArrayList<>();
    private Crew crew = new Crew("Lemon");

    @BeforeEach
    void setup() {
        attendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 9, 50)));
        attendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 14, 30)));
        attendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 10, 10)));
        attendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 9, 30)));

    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석할 수 있다.")
    void testAttendanceWithNicknameAndTime() {


        //when
        MemberAttendance memberAttendance = new MemberAttendance(crew, attendances);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberAttendance.getCrew().getName()).isEqualTo("Lemon");
            softly.assertThat(memberAttendance.getAttendances().get(0).getAttendanceDate())
                    .isEqualTo(LocalDate.of(2024, 12, 4));
            softly.assertThat(memberAttendance.getAttendances().get(0).getAttendanceTime())
                    .isEqualTo(LocalTime.of(9, 50));
            softly.assertThat(memberAttendance.getAttendances().get(0).getAttendanceStatus()).isEqualTo("출석");
        });
    }

    @Test
    @DisplayName("출석 수정 테스트")
    void modifyAttendanceTest() {
        //given
        Crew modifier = new Crew("Lemon");
        LocalDateTime attendanceDateTime =  LocalDateTime.of(2024, 12, 4, 9, 50);

        //when
        MemberAttendance memberAttendance = new MemberAttendance(crew, attendances);

        //then
        Assertions.assertDoesNotThrow(() -> memberAttendance.modifyAttendanceRecord(attendanceDateTime));
    }

}
