package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import attendance.domain.Attendance;
import attendance.domain.AttendanceLog;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Subject;
import attendance.domain.dto.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceLogTest {

    private AttendanceLog attendanceLog;
    @BeforeEach
    void setup() {
        List<Attendance> sampleAttendances = new ArrayList<>();
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 9, 50)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 14, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 10, 10)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 9, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 9, 15, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 10, 18, 30)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 12, 10, 10)));
        sampleAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 13, 9, 30)));
        attendanceLog = new AttendanceLog(sampleAttendances);
    }


    @Test
    @DisplayName(" 출석한다면 출석 기록에 더해진다")
    void testAttendanceWithNicknameAndTime() {
        //given && when
        Attendance attendance = attendanceLog.registerAttendance(LocalDateTime.of(2024, 12, 16, 13, 0));
        //then
        assertSoftly(softly -> {
            softly.assertThat(attendance.getAttendanceDate()).isEqualTo(LocalDate.of(2024, 12, 16));
            softly.assertThat(attendance.getAttendanceTime()).isEqualTo(LocalTime.of(13, 0));
            softly.assertThat(attendance.getAttendanceStatus()).isEqualTo("출석");
        });
    }


    @Test
    @DisplayName("출석 수정 테스트")
    void modifyAttendanceTest() {
        //given
        Crew modifier = new Crew("Lemon");
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 4, 9, 50);

        //expected
        Assertions.assertDoesNotThrow(() -> attendanceLog.modifyAttendanceRecord(attendanceDateTime));
    }

    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    void checkCrewAllAttendanceRecord() {

        //given
        List<AttendanceResult> attendancesResult =
            attendanceLog.getAttendanceLog().stream()
                .map(AttendanceResult::from)
                .toList();

        //expected
        assertSoftly(SoftlyExtension -> {
            assertThat(attendancesResult.getFirst().attendanceMonth()).isEqualTo(12);
            assertThat(attendancesResult.getFirst().attendanceDay()).isEqualTo(2);
            assertThat(attendancesResult.getFirst().attendanceHour()).isEqualTo(13);
            assertThat(attendancesResult.getFirst().attendanceMinute()).isEqualTo(0);
            assertThat(attendancesResult.getFirst().attendanceStatus()).isEqualTo("출석");
        });

        assertSoftly(SoftlyExtension -> {
            assertThat(attendancesResult.get(2).attendanceMonth()).isEqualTo(12);
            assertThat(attendancesResult.get(2).attendanceDay()).isEqualTo(4);
            assertThat(attendancesResult.get(2).attendanceHour()).isEqualTo(14);
            assertThat(attendancesResult.get(2).attendanceMinute()).isEqualTo(30);
            assertThat(attendancesResult.get(2).attendanceStatus()).isEqualTo("결석");
        });
    }

    @Test
    @DisplayName("지각,출석,결석에 따라 대상자를 선정한다.")
    void checkSubjectStatusTest() {
        // given
        int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
        int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
        int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);

        // when
        AttendanceStatus attendanceStatus = new AttendanceStatus(attendanceCount, lateCount, absentCount);
        String subjectStatus = attendanceStatus.getSubjectStatus(); // 이제 상태는 객체에 캡슐화돼 있음

        // then
        assertThat(subjectStatus).isEqualTo("면담");
    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석,지각,결석 횟수가 증가한다")
    void countAttendanceStatusTest() {

        //expected
        assertSoftly(softly -> {
            softly.assertThat(attendanceLog.countAttendanceStatus(Subject.ATTENDANCE)).isEqualTo(4);
            softly.assertThat(attendanceLog.countAttendanceStatus(Subject.LATE)).isEqualTo(3);
            softly.assertThat(attendanceLog.countAttendanceStatus(Subject.ABSENT)).isEqualTo(3);
        });
    }
}
