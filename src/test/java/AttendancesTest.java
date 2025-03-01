import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.entry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @DisplayName("크루들의 출석 기록을 가질 수 있다.")
    @Test
    void create() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        //when
        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        //then
        assertThat(attendances.getAttendances())
                .hasSize(2);
    }

    @DisplayName("닉네임과 일치하는 크루의 출석부를 반환한다.")
    @Test
    void findCrewBy() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";

        //when
        Attendance attendance = attendances.findCrewBy(name);

        //then
        assertThat(attendance).isEqualTo(attendances1);
    }

    @DisplayName("닉네임과 등교시간을 받아 출석을 한다.")
    @Test
    void attendanceCheckBy() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDateTime time = LocalDateTime.of(2024, 12, 12, 10, 10);

        //when //then
        assertThatCode(() -> attendances.checkAttendance(name, time))
                .doesNotThrowAnyException();
    }

    @DisplayName("닉네임과 수정날짜(일), 수정시간을 받아 출석 기록을 수정 한다.")
    @Test
    void updateAttendance() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 3, 10, 10);

        //when //then
        assertThatCode(() -> attendances.updateAttendance(name, dateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("수정 후, 출석 기록을 가져온다.")
    @Test
    void getAfterAttendance() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 10, 10);

        //when
        attendances.updateAttendance(name, time);
        LocalDateTime actual = attendances.getAttendanceRecordBy(name, time.toLocalDate());

        //then
        assertThat(actual).isEqualTo(LocalDateTime.of(2024, 12, 3, 10, 10));
    }

    @DisplayName("수정을 하기 전, 출석 기록을 가져온다.")
    @Test
    void getBeforeAttendance() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDate time = LocalDate.of(2024, 12, 3);

        //when
        LocalDateTime actual = attendances.getAttendanceRecordBy(name, time);

        //then
        assertThat(actual).isEqualTo(LocalDateTime.of(2024, 12, 3, 11, 11));
    }

    @DisplayName("특정 크루의 전날까지의 출석 기록을 확인한다.")
    @Test
    void readAttendanceRecordByCrew() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDate dateTime = LocalDate.of(2024, 12, 10);

        //when
        Map<LocalDateTime, AttendanceState> actual = attendances.getHistory(name, dateTime);

        //then
        assertThat(actual)
                .hasSize(6)
                .containsExactly(
                        entry(LocalDateTime.of(2024, 12, 2, 10, 10), AttendanceState.ATTENDANCE),
                        entry(LocalDateTime.of(2024, 12, 3, 11, 11), AttendanceState.ABSENCE),
                        entry(LocalDateTime.of(2024, 12, 4, 0, 0), AttendanceState.ABSENCE),
                        entry(LocalDateTime.of(2024, 12, 5, 0, 0), AttendanceState.ABSENCE),
                        entry(LocalDateTime.of(2024, 12, 6, 0, 0), AttendanceState.ABSENCE),
                        entry(LocalDateTime.of(2024, 12, 9, 0, 0), AttendanceState.ABSENCE)
                );
    }

    @DisplayName("크루의 출석 기록을 바탕으로 출석, 지각, 결석 횟수를 계산한다.")
    @Test
    void calculateAttendanceCount() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        String name = "도기";
        LocalDate dateTime = LocalDate.of(2024, 12, 10);
        Map<LocalDateTime, AttendanceState> attendancesHistory = attendances.getHistory(name, dateTime);

        //when
        Map<AttendanceState, Integer> actual = attendances.calculate(attendancesHistory);

        //then
        assertThat(actual)
                .containsExactly(
                        entry(AttendanceState.ATTENDANCE, 1),
                        entry(AttendanceState.LATE, 0),
                        entry(AttendanceState.ABSENCE, 5)
                );
    }

    @DisplayName("전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 계산한다.")
    @Test
    void calculateAbsence() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        LocalDate dateTime = LocalDate.of(2024, 12, 10);
        Crew crew = attendances1.getCrew();
        Crew crew1 = attendances2.getCrew();

        //when
        Map<Crew, Map<AttendanceState, Integer>> actual = attendances.calculateAbsence(dateTime);

        //then
        assertThat(actual)
                .extractingByKeys(crew, crew1)
                .flatExtracting(Map::entrySet)
                .containsExactly(
                        entry(AttendanceState.ATTENDANCE, 1),
                        entry(AttendanceState.LATE, 0),
                        entry(AttendanceState.ABSENCE, 5),
                        entry(AttendanceState.ATTENDANCE, 1),
                        entry(AttendanceState.LATE, 0),
                        entry(AttendanceState.ABSENCE, 5)
                );
    }

    private Attendance createAttendance(final String name) {
        Crew crew = Crew.of(name);

        List<LocalDateTime> attendanceTime = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 10, 10);
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 3, 11, 11);

        attendanceTime.add(localDateTime);
        attendanceTime.add(localDateTime1);

        return new Attendance(crew, attendanceTime);
    }

}
