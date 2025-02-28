package service;

import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecords;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Crews;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import service.dto.SaveAttendanceRecordResponse;

class AttendanceServiceTest {
    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        @DisplayName("닉네임과 등교시간을 받아 출석 기록을 저장한다")
        void saveAttendanceRecordTest() {
            // given
            String nickname = "시소";
            Crew crew = new Crew(nickname);
            LocalDate monday = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 30);
            Crews crews = new Crews(List.of(crew));
            AttendanceRecords attendanceRecords = new AttendanceRecords();
            AttendanceService attendanceService = new AttendanceService(crews, attendanceRecords);
            SaveAttendanceRequest request = new SaveAttendanceRequest(nickname, monday, time);

            // when
            SaveAttendanceRecordResponse response = attendanceService.saveAttendanceRecord(request);

            // then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.dateTime())
                        .isEqualTo(LocalDateTime.of(monday, time));
                softAssertions.assertThat(response.status())
                        .isEqualTo(AttendanceStatus.LATE);
            });
        }


    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {

    }
}