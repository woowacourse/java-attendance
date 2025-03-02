package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class AttendancesTest {

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_기록이_존재하는지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThat(attendances.hasAttendanceByLocalDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @Test
    void 날짜를_알려주면_출석_기록을_알려준다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThat(attendances.findSameDateAttendance(LocalDate.of(2025, 2, 26)))
                .isEqualTo(new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0)));
    }

    @Test
    void 기록이_존재하지_않는_날짜를_알려주면_출석_기록을_조회할_수_없다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThatThrownBy(() -> attendances.findSameDateAttendance(LocalDate.of(2025, 2, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수정_일자를_알려주면_해당_출석_기록을_수정한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        LocalDateTime modificationDateTime = LocalDateTime.of(2025, 2, 26, 9, 50);

        attendances.modifyByModificationDateTime(modificationDateTime);

        assertThat(attendances.findSameDateAttendance(modificationDateTime.toLocalDate()))
                .isEqualTo(new Attendance(modificationDateTime));
    }

    @Test
    void 출석을_저장한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        Attendance addedAttendance = new Attendance(LocalDateTime.of(2025, 2, 27, 9, 50));

        attendances.add(addedAttendance);

        assertThat(attendances.findSameDateAttendance(LocalDate.of(2025, 2, 27)))
                .isEqualTo(new Attendance(LocalDateTime.of(2025, 2, 27, 9, 50)));
    }

    @Test
    void 해당_날짜에_이미_존재하는_출석_기록은_저장할_수_없다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        Attendance addedAttendance = new Attendance(LocalDateTime.of(2025, 2, 26, 9, 50));

        assertThatThrownBy(() -> attendances.add(addedAttendance))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기준_날짜를_알려주면_해당_날짜까지의_모든_출석_기록을_알려준다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        Attendance addedAttendance = new Attendance(LocalDateTime.of(2025, 2, 27, 9, 50));
        attendances.add(addedAttendance);

        assertThat(attendances.findAllUntilStandardDate(LocalDate.of(2025, 2, 27))).hasSize(2);
    }

    @Test
    void 기준_날짜를_알려주면_해당_날짜까지의_출석_횟수를_알려준다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        LocalDate standardDate = LocalDate.of(2025, 2, 26);

        assertThat(attendances.calculateAttendanceCount(standardDate)).isEqualTo(1);
    }

    @Test
    void 기준_날짜를_알려주면_해당_날짜까지의_지각_횟수를_알려준다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendance lateAttendance = new Attendance(LocalDateTime.of(2025, 2, 27, 10, 6));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance, lateAttendance)));
        LocalDate standardDate = LocalDate.of(2025, 2, 27);

        assertThat(attendances.calculateLateCount(standardDate)).isEqualTo(1);
    }

    @Test
    void 기준_날짜를_알려주면_해당_날짜까지의_결석_횟수를_알려준다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendance lateAttendance = new Attendance(LocalDateTime.of(2025, 2, 27, 10, 31));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance, lateAttendance)));
        LocalDate standardDate = LocalDate.of(2025, 2, 27);

        assertThat(attendances.calculateAbsentCount(standardDate)).isEqualTo(1);
    }

    @MethodSource("provideAttendancesWithDateAndExpectedStatusInfo")
    @ParameterizedTest
    void 현재_저장된_이번달_출석_기록을_통해_제적_위험자_상태를_알려준다(List<Attendance> attendanceGroup, ExpulsionStatus expectedStatus) {
        Attendances attendances = new Attendances(attendanceGroup);

        assertThat(attendances.findExpulsionStatusUntilLastDate()).isEqualByComparingTo(expectedStatus);
    }

    private static Stream<Arguments> provideAttendancesWithDateAndExpectedStatusInfo() {
        List<Attendance> attendances = createOneAttendanceCompleteSixAbsent();
        return Stream.of(
                Arguments.of(attendances, ExpulsionStatus.EXPULSION),
                Arguments.of(attendances.subList(0, 6), ExpulsionStatus.INTERVIEW),
                Arguments.of(attendances.subList(0, 4), ExpulsionStatus.INTERVIEW),
                Arguments.of(attendances.subList(0, 3), ExpulsionStatus.WARNING),
                Arguments.of(attendances.subList(0, 2), ExpulsionStatus.NONE)
        );
    }

    private static List<Attendance> createOneAttendanceCompleteSixAbsent() {
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 17, 10, 30)));
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 18, 10, 31)));
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 19, 10, 31)));
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 20, 10, 31)));
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 21, 10, 31)));
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 24, 13, 31)));
        attendances.add(new Attendance(LocalDateTime.of(2025, 2, 25, 13, 31)));
        return attendances;
    }

}
