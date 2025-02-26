package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.DateTimeParser;

public class CrewTest {


    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @Test
        @DisplayName("크루 객체 생성 테스트")
        void test1() {
            //given
            final String name = "윌슨";

            //when
            final Crew crew = Crew.of(name, LocalDate.now());
            final CrewName crewName = crew.getName();

            //then
            assertThat(crewName.getName()).isEqualTo(name);
        }

        @Test
        @DisplayName("크루에 출석 시간을 추가하는 테스트")
        void test2() {
            //given
            final String name = "윌슨";
            final String time = "2024-12-13 10:08";
            final Crew crew = new Crew(name, new ArrayList<>());
            final LocalDateTime expectedTime = LocalDateTime.of(2024, 12, 13, 10, 8, 0);

            //when
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(time));
            final List<Attendance> attendances = crew.getAttendances();

            //then
            assertThat(attendances).isNotEmpty();
            assertThat(attendances.getFirst().getDateTime()).isEqualTo(expectedTime);
        }

        @Test
        @DisplayName("크루의 출석 통계를 반환하는 테스트")
        void test3() {
            //given
            final String name = "윌슨";
            final String attendancedTime = "2024-12-13 10:05";
            final String latedTime = "2024-12-17 10:30";
            final String absencedTime = "2024-12-18 10:35";
            final Crew crew = new Crew(name, new ArrayList<>());

            //when
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(attendancedTime));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(absencedTime));
            final Map<AttendanceStatus, Integer> actualStatistics = crew.calculateAttendanceStatistics();

            //then
            assertThat(actualStatistics.size()).isEqualTo(3);
            assertThat(actualStatistics)
                    .containsEntry(AttendanceStatus.ATTENDANCE, 1)
                    .containsEntry(AttendanceStatus.LATE, 1)
                    .containsEntry(AttendanceStatus.ABSENCE, 1);
        }

        @Test
        @DisplayName("크루의 제적 위험성을 계산하는 테스트")
        void test4() {
            //given
            final String name = "윌슨";
            final List<String> absences = List.of("2024-12-12 10:35", "2024-12-11 10:35", "2024-12-10 10:35");
            final Crew crew = new Crew(name, new ArrayList<>());
            absences.forEach(absence -> crew.addAttendance(DateTimeParser.parseToLocalDateTime(absence)));

            //when
            ExpulsionStatus actual = crew.calculateExpulsionStatus();

            //then
            assertThat(actual).isEqualTo(ExpulsionStatus.INTERVIEW);

        }

        @Test
        @DisplayName("오늘 출석 결과가 존재하는지 확인한다.")
        void existTodayAttendanceTest() {
            //given
            final LocalDate today = LocalDate.now().withYear(2024).withMonth(12).withDayOfMonth(15);
            final String name = "윌슨";
            final String attendancedTime = "2024-12-13 10:05";
            final String latedTime = "2024-12-14 10:30";
            final String absencedTime = "2024-12-15 10:35";
            final Crew crew = new Crew(name, new ArrayList<>());

            //when
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(attendancedTime));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(absencedTime));

            //then
            assertThat(crew.existTodayAttendance(today)).isTrue();
        }

        @Test
        @DisplayName("크루의 출석을 수정 한다.")
        void updateAttendanceByDateTimeTest() {
            //given
            final String time = "2024-12-15 11:00";
            final String name = "윌슨";
            final Crew crew = new Crew(name, new ArrayList<>());
            final List<Attendance> attendances = crew.getAttendances();

            //when
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(time));
            crew.updateAttendanceByDateTime(time);
            final List<Attendance> updatedAttendances = crew.getAttendances();
            final Attendance first = updatedAttendances.getFirst();

            //then
            assertThat(attendances).isEmpty();
            assertThat(updatedAttendances).isNotEmpty();
            assertThat(first.getDateTime()).isEqualTo(LocalDateTime.parse(time,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        @Test
        @DisplayName("크루 제적 상태를 카운트 한다.")
        void countExpulsionStatusTest() {
            //given
            final String name = "윌슨";
            final String attendancedTime = "2024-12-13 10:05";
            final String latedTime1 = "2024-12-17 10:30";
            final String latedTime2 = "2024-12-18 10:30";
            final String latedTime3 = "2024-12-19 10:30";
            final String absencedTime = "2024-12-20 10:35";
            final Crew crew = new Crew(name, new ArrayList<>());

            //when
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(attendancedTime));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime1));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime2));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime3));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(absencedTime));
            final int count = crew.countExpulsionStatus();

            //then
            assertThat(count).isEqualTo(2);
        }

        @Test
        @DisplayName("크루의 출석 기록을 날짜를 기준으로 찾는다.")
        void findAttendanceByDateTest() {
            //given
            final LocalDate target = LocalDate.of(2024, 12, 13);
            final String name = "윌슨";
            final String attendancedTime = "2024-12-13 10:05";
            final String latedTime1 = "2024-12-17 10:30";
            final String latedTime2 = "2024-12-18 10:30";
            final String latedTime3 = "2024-12-19 10:30";
            final String absencedTime = "2024-12-15 10:35";
            final Crew crew = new Crew(name, new ArrayList<>());

            //when
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(attendancedTime));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime1));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime2));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(latedTime3));
            crew.addAttendance(DateTimeParser.parseToLocalDateTime(absencedTime));
            final Attendance attendanceByDate = crew.findAttendanceByDate(target);

            //then
            assertThat(attendanceByDate.getDateTime().toLocalDate()).isEqualTo(target);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("출석이 존재하지 않는 날짜라면, 예외가 발생한다.")
        @Test
        void findAttendanceByDate() {
            // given
            final String name = "윌슨";
            final Crew crew = new Crew(name, new ArrayList<>());
            final LocalDate notExistDate = LocalDate.of(2024, 12, 13);

            // when & then
            assertThatThrownBy(() -> {
                crew.findAttendanceByDate(notExistDate);
            }).isInstanceOf(IllegalArgumentException.class);
        }

    }


}
