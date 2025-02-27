package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.dto.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberAttendanceTest {

    private List<Attendance> sampleAttendances = new ArrayList<>();
    private Crew crew = new Crew("Lemon");

    @BeforeEach
    void setup() {
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

    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석할 수 있다.")
    void testAttendanceWithNicknameAndTime() {

        //when
        MemberAttendance memberAttendance = new MemberAttendance(crew, sampleAttendances);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberAttendance.getCrew().getName()).isEqualTo("Lemon");
            softly.assertThat(memberAttendance.getAttendances().get(0).getAttendanceDate()).isEqualTo(LocalDate.of(2024, 12, 4));
            softly.assertThat(memberAttendance.getAttendances().get(0).getAttendanceTime()).isEqualTo(LocalTime.of(9, 50));
            softly.assertThat(memberAttendance.getAttendances().get(0).getAttendanceStatus()).isEqualTo("출석");
        });
    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석,지각,결석 횟수가 증가한다")
    void countAttendanceStatusTest() {

        //when
        MemberAttendance memberAttendance = new MemberAttendance(crew, sampleAttendances);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberAttendance.countAttendanceStatus(Subject.ATTENDANCE)).isEqualTo(3);
            softly.assertThat(memberAttendance.countAttendanceStatus(Subject.LATE)).isEqualTo(1);
            softly.assertThat(memberAttendance.countAttendanceStatus(Subject.ABSENT)).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("닉네임과 등교시간을 입력하면 출석,지각,결석 횟수가 증가한다")
    void checkSubjectStatusTest() {

        //given
        MemberAttendance memberAttendance = new MemberAttendance(crew, sampleAttendances);
        int attendanceCount = memberAttendance.countAttendanceStatus(Subject.ATTENDANCE);
        int lateCount =  memberAttendance.countAttendanceStatus(Subject.LATE);
        int absentCount = memberAttendance.countAttendanceStatus(Subject.ABSENT);

        //when
        String subjectStatus = memberAttendance.checkSubjectStatus(attendanceCount, lateCount, absentCount);

        //then
        org.assertj.core.api.Assertions.assertThat(subjectStatus).isEqualTo("면담 대상자");
    }



    @Test
    @DisplayName("출석 수정 테스트")
    void modifyAttendanceTest() {
        //given
        Crew modifier = new Crew("Lemon");
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 4, 9, 50);
        MemberAttendance memberAttendance = new MemberAttendance(crew, sampleAttendances);

        //expected
        Assertions.assertDoesNotThrow(() -> memberAttendance.modifyAttendanceRecord(attendanceDateTime));
    }


    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    void checkCrewAllAttendanceRecord() {
        //given
        MemberAttendance memberAttendance = new MemberAttendance(crew, sampleAttendances);
        List<Attendance> attendances = memberAttendance.getAttendances();

        //then
        List<AttendanceResult> attendancesResult = attendances.stream()
            .map(AttendanceResult::from)
            .toList();

        SoftAssertions.assertSoftly(SoftlyExtension -> {
            assertThat(attendancesResult.getFirst().attendanceMonth()).isEqualTo(12);
            assertThat(attendancesResult.getFirst().attendanceDay()).isEqualTo(2);
            assertThat(attendancesResult.getFirst().attendanceHour()).isEqualTo(13);
            assertThat(attendancesResult.getFirst().attendanceMinute()).isEqualTo(0);
            assertThat(attendancesResult.getFirst().attendanceStatus()).isEqualTo("출석");
        });

        SoftAssertions.assertSoftly(SoftlyExtension -> {
            assertThat(attendancesResult.get(2).attendanceMonth()).isEqualTo(12);
            assertThat(attendancesResult.get(2).attendanceDay()).isEqualTo(4);
            assertThat(attendancesResult.get(2).attendanceHour()).isEqualTo(14);
            assertThat(attendancesResult.get(2).attendanceMinute()).isEqualTo(30);
            assertThat(attendancesResult.get(2).attendanceStatus()).isEqualTo("결석");
        });
    }

}
