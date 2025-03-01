package domain.crew;

import static domain.testdata.AttendanceTestData.AttendanceTimesData.createCounselingAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createDismissAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createNormalAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createWarnedAttendanceTimes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import domain.attendance.AttendanceTime;
import domain.attendance.AttendanceTimes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceTest {

    @Test
    @DisplayName("CrewAttendance 객체 생성 성공")
    void createCrewAttendanceTest() {
        // given
        Crew crew = Crew.of("차니");
        AttendanceTimes attendanceTimes = createAttendanceTimes();

        // when, then
        assertThatCode(() -> CrewAttendance.of(crew, attendanceTimes))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("크루의 출석 횟수를 카운트")
    void countCrewAttendance() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );
        LocalDate today = LocalDate.of(2024, 12, 14);

        // when
        int count = crewAttendance.getAttendanceBeforeDate(today);

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("크루의 지각 횟수를 카운트")
    void countCrewLate() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );
        LocalDate today = LocalDate.of(2024, 12, 14);

        // when
        int count = crewAttendance.getLateBeforeDate(today);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("크루의 결석 횟수를 카운트")
    void countCrewAbsence() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );
        LocalDate today = LocalDate.of(2024, 12, 14);

        // when
        int count = crewAttendance.getAbsenceBeforeDate(today);

        // then
        assertThat(count).isEqualTo(7);
    }

    @Test
    @DisplayName("해당 크루가 출석부의 대상자인지 판단")
    void belongsToCrewTest() {
        // given
        Crew owner = Crew.of("차니");
        Crew other = Crew.of("포비");
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );

        // when
        boolean b1 = crewAttendance.belongsTo(owner);
        boolean b2 = crewAttendance.belongsTo(other);

        // then
        assertThat(b1).isTrue();
        assertThat(b2).isFalse();
    }

    @Test
    @DisplayName("출석 시 기록 추가")
    void attendanceTest() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );
        AttendanceTime attendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 20),
                LocalTime.of(10, 1)
        );

        // when, then
        assertThatCode(() -> crewAttendance.attend(attendanceTime))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출석 기록 수정")
    void modifyAttendanceTest() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );
        AttendanceTime attendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 20),
                LocalTime.of(10, 1)
        );

        // when
        assertThatCode(() -> crewAttendance.modify(attendanceTime))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출석 기록 수정 시 이전 기록 반환")
    void modifyAttendanceThenReturnPreviousTimeTest() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );
        AttendanceTime noPreviousAttendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 20),
                LocalTime.of(10, 1)
        );
        AttendanceTime attendanceTime = AttendanceTime.of( // 이전 기록 10시 5분
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 1)
        );

        // when
        Optional<AttendanceTime> previous1 = crewAttendance.modify(noPreviousAttendanceTime);
        Optional<AttendanceTime> previous2 = crewAttendance.modify(attendanceTime);

        // then
        AttendanceTime expected2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        assertThat(previous1.isEmpty()).isTrue();
        assertThat(previous2.isPresent()).isTrue();
        assertThat(previous2.get()).isEqualTo(expected2);
    }

    @Test
    @DisplayName("날짜로부터 존재하는 출석 기록 반환")
    void readPresentAttendanceLogTest() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );

        // when
        Optional<AttendanceTime> log = crewAttendance.readLog(
                LocalDate.of(2024, 12, 13)
        );

        // then
        AttendanceTime expected = AttendanceTime.of(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(11, 6)
        );
        assertThat(log).isPresent();
        assertThat(log.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("날짜로부터 존재하지 않는 출석 기록 반환")
    void readEmptyAttendanceLogTest() {
        // given
        CrewAttendance crewAttendance = CrewAttendance.of(
                Crew.of("차니"), createAttendanceTimes()
        );

        // when
        Optional<AttendanceTime> log = crewAttendance.readLog(
                LocalDate.of(2024, 12, 15)
        );

        // then
        assertThat(log).isEmpty();
    }

    @Test
    @DisplayName("해당 크루의 출석부를 보고 정상 학생 판단 후 반환")
    void getDisciplinaryStatusNormalTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 10);
        Crew crew = Crew.of("차니");
        AttendanceTimes attendanceTimes = createNormalAttendanceTimes();
        CrewAttendance crewAttendance = CrewAttendance.of(crew, attendanceTimes);

        // when
        DisciplinaryStatus disciplinaryStatus = crewAttendance.getDisciplinaryStatus(today);

        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.NORMAL);
    }

    @Test
    @DisplayName("해당 크루의 출석부를 보고 경고 학생 판단 후 반환")
    void getDisciplinaryStatusWarnedTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 10);
        Crew crew = Crew.of("차니");
        AttendanceTimes attendanceTimes = createWarnedAttendanceTimes();
        CrewAttendance crewAttendance = CrewAttendance.of(crew, attendanceTimes);

        // when
        DisciplinaryStatus disciplinaryStatus = crewAttendance.getDisciplinaryStatus(today);

        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.WARNED);
    }

    @Test
    @DisplayName("해당 크루의 출석부를 보고 상담 학생 판단 후 반환")
    void getDisciplinaryStatusCounselingTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 10);
        Crew crew = Crew.of("차니");
        AttendanceTimes attendanceTimes = createCounselingAttendanceTimes();
        CrewAttendance crewAttendance = CrewAttendance.of(crew, attendanceTimes);

        // when
        DisciplinaryStatus disciplinaryStatus = crewAttendance.getDisciplinaryStatus(today);

        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.COUNSELING);
    }

    @Test
    @DisplayName("해당 크루의 출석부를 보고 제적 학생 판단 후 반환")
    void getDisciplinaryStatusDismissTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 10);
        Crew crew = Crew.of("차니");
        AttendanceTimes attendanceTimes = createDismissAttendanceTimes();
        CrewAttendance crewAttendance = CrewAttendance.of(crew, attendanceTimes);

        // when
        DisciplinaryStatus disciplinaryStatus = crewAttendance.getDisciplinaryStatus(today);

        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.DISMISSED);
    }

    private AttendanceTimes createAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(11, 6)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4)
        );
    }
}
