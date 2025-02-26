package attendance.domain;

import attendance.domain.dto.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberAttendanceTest {

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석할 수 있다.")
    void testAttendanceWithNicknameAndTime() {

        //given
        Crew crew = new Crew("Lemon");
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 4, 9, 50));
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(attendance);
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

}
