package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.*;

import attendance.domain.dto.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberAttendanceRecordTest {

    private Crew crew = new Crew("Lemon");
    private AttendanceLog attendanceLog;
    private MemberAttendanceRecord memberAttendanceRecord;
    private Map<Crew, AttendanceLog> attendanceRecord = new HashMap<>();

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
        attendanceRecord.put(crew, attendanceLog);
        memberAttendanceRecord = new MemberAttendanceRecord(attendanceRecord);
    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석할 수 있다.")
    void testAttendanceWithNicknameAndTime() {
        //given && when
        Attendance attendance = memberAttendanceRecord.findAttendanceLogByCrew(crew).getAttendanceLog().get(0);

        //then
        assertSoftly(softly -> {

            softly.assertThat(attendance.getAttendanceDate()).isEqualTo(LocalDate.of(2024, 12, 2));
            softly.assertThat(attendance.getAttendanceTime()).isEqualTo(LocalTime.of(13, 0));
            softly.assertThat(attendance.getAttendanceStatus()).isEqualTo("출석");
        });
    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석,지각,결석 횟수가 증가한다")
    void countAttendanceStatusTest() {

        //when
        AttendanceLog attendanceLogByCrew = memberAttendanceRecord.findAttendanceLogByCrew(crew);

        //then
        assertSoftly(softly -> {
            softly.assertThat(attendanceLogByCrew.countAttendanceStatus(Subject.ATTENDANCE)).isEqualTo(4);
            softly.assertThat(attendanceLogByCrew.countAttendanceStatus(Subject.LATE)).isEqualTo(3);
            softly.assertThat(attendanceLogByCrew.countAttendanceStatus(Subject.ABSENT)).isEqualTo(3);
        });
    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석,지각,결석 횟수가 증가한다")
    void checkSubjectStatusTest() {

        //given
        AttendanceLog attendanceLogByCrew = memberAttendanceRecord.findAttendanceLogByCrew(crew);
        int attendanceCount = attendanceLogByCrew.countAttendanceStatus(Subject.ATTENDANCE);
        int lateCount =  attendanceLogByCrew.countAttendanceStatus(Subject.LATE);
        int absentCount = attendanceLogByCrew.countAttendanceStatus(Subject.ABSENT);

        //when
        String subjectStatus = memberAttendanceRecord.checkSubjectStatus(attendanceCount, lateCount, absentCount);

        //then
        assertThat(subjectStatus).isEqualTo("면담 대상자");
    }



    @Test
    @DisplayName("출석 수정 테스트")
    void modifyAttendanceTest() {
        //given
        Crew modifier = new Crew("Lemon");
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 4, 9, 50);
        AttendanceLog attendanceLogByCrew = memberAttendanceRecord.findAttendanceLogByCrew(crew);

        //expected
        Assertions.assertDoesNotThrow(() -> attendanceLogByCrew.modifyAttendanceRecord(attendanceDateTime));
    }


    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    void checkCrewAllAttendanceRecord() {
        //given
        AttendanceLog attendanceLogByCrew = memberAttendanceRecord.findAttendanceLogByCrew(crew);

        //then
        List<AttendanceResult> attendancesResult =
            attendanceLog.getAttendanceLog().stream()
            .map(AttendanceResult::from)
            .toList();

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

}
