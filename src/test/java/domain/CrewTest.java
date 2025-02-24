package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewTest {

    @Test
    @DisplayName("닉네임과 등교 시간으로 출석한다")
    void attendanceTest() {
        // given
        CrewRepository crewRepository = CrewRepository.generate();

        // when
        crewRepository.add(new Crew("pobi"));

        // then
        Crew crew = crewRepository.get("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 1));
    }

    @Test
    @DisplayName("이미_출석한_경우_예외를_던진다")
    void attendanceExceptionTest() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 00));

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(LocalDate.now(), LocalTime.of(10, 01));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주말 혹은 공휴일에는 출석 기록을 남길 수 없다")
    void offdayExceptionTest() {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(LocalDate.of(2025, 12, 25), LocalTime.of(10, 00));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("닉네임과 수정날짜와 등교시간으로 기록을 수정한다")
    void modifyAttendanceTest() {
        // given
        CrewRepository crewRepository = CrewRepository.generate();
        crewRepository.add(new Crew("pobi"));

        // when
        Crew crew = crewRepository.get("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 00));
        crew.modifyAttendance(LocalDate.now(), LocalTime.of(10, 10));

        // then
        assertThat(crew.getAttendanceTimeByDate(LocalDate.now())).isEqualTo(LocalTime.of(10, 10));
    }

    @ParameterizedTest
    @CsvSource({
        "2025,02,17,13,06,LATE",
        "2025,02,18,10,05,ATTENDANCE",
        "2025,02,19,10,31,ABSENT",
    })
    @DisplayName("날짜와 시간으로 출석 상태를 계산한다")
    void getAttendanceStatusByDateTest(int year, int month, int day, int hour, int minute, String statusName) {
        // given
        CrewRepository crewRepository = CrewRepository.generate();
        crewRepository.add(new Crew("pobi"));

        // when
        Crew crew = crewRepository.get("pobi");
        crew.attendance(LocalDate.of(year, month, day), LocalTime.of(hour, minute));

        // then
        assertThat(crew.getAttendanceStatusByDate(LocalDate.of(year, month, day)).name()).isEqualTo(statusName);
    }

    @Test
    @DisplayName("출석 상태별 횟수를 계산한다")
    void getAttendanceStatusCounterTest() {
        // given
        Crew crew = new Crew("pobi");

        // when
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06)); // LATE
        crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 05)); // ATTENDANCE
        crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 31)); // ABSENT
        // 6일, 7일은 미기록 -> ABSENT

        // then
        Map<AttendanceStatus, Integer> statusCounter = crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 8));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(statusCounter.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(statusCounter.get(AttendanceStatus.LATE)).isEqualTo(1);
            softly.assertThat(statusCounter.get(AttendanceStatus.ABSENT)).isEqualTo(3);
        });
    }
}
