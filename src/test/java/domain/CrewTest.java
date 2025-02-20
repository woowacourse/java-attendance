package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("닉네임과 등교 시간으로 출석한다")
    void attendanceTest() {
        CrewRepository crewRepository = new CrewRepository(false);
        crewRepository.add(new Crew("pobi"));
        Crew crew = crewRepository.get("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 1));
    }

    @Test
    @DisplayName("이미_출석한_경우_예외를_던진다")
    void attendanceExceptionTest() {
        Crew crew = new Crew("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 00));
        assertThatThrownBy(() -> {
            crew.attendance(LocalDate.now(), LocalTime.of(10, 01));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주말 혹은 공휴일에는 출석 기록을 남길 수 없다")
    void offdayExceptionTest() {
        Crew crew = new Crew("pobi");
        assertThatThrownBy(() -> {
            crew.attendance(LocalDate.of(2025, 12, 25), LocalTime.of(10, 00));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("닉네임과 수정날짜와 등교시간으로 기록을 수정한다")
    void modifyAttendanceTest() {
        CrewRepository crewRepository = new CrewRepository(false);
        crewRepository.add(new Crew("pobi"));
        Crew crew = crewRepository.get("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 00));
        crew.modifyAttendance(LocalDate.now(), LocalTime.of(10, 10));
        assertThat(crew.getAttendanceTimeByDate(LocalDate.now())).isEqualTo(LocalTime.of(10, 10));
    }

    @Test
    @DisplayName("날짜와 시간으로 출석 상태를 계산한다")
    void getAttendanceStatusByDateTest() {
        CrewRepository crewRepository = new CrewRepository(false);
        crewRepository.add(new Crew("pobi"));
        Crew crew = crewRepository.get("pobi");
        crew.attendance(LocalDate.of(2025, 02, 17), LocalTime.of(13, 06));
        crew.attendance(LocalDate.of(2025, 02, 18), LocalTime.of(10, 05));
        crew.attendance(LocalDate.of(2025, 02, 19), LocalTime.of(10, 31));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 03)))
                .isEqualTo(AttendanceStatus.ABSENT);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 17)))
                .isEqualTo(AttendanceStatus.LATE);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 18)))
                .isEqualTo(AttendanceStatus.ATTENDANCE);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 19)))
                .isEqualTo(AttendanceStatus.ABSENT);
        });
    }

    @Test
    @DisplayName("출석 상태별 횟수를 계산한다")
        void getAttendanceStatusCounterTest () {
            Crew crew = new Crew("pobi");
            // 지각
            crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06));
            // 출석
            crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 05));
            // 결석
            crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 31));

            Map<AttendanceStatus, Integer> statusCounter = crew.getAttendanceStatusCounter(
                LocalDate.of(2025, 02, 10));
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(statusCounter.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
                softly.assertThat(statusCounter.get(AttendanceStatus.LATE)).isEqualTo(1);
                softly.assertThat(statusCounter.get(AttendanceStatus.ABSENT)).isEqualTo(3);
            });
        }
    }
