package attendance.domain;

import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceManagerTest {
    @DisplayName("주어진_닉네임의_크루가_존재하는지_여부를_반환할_수_있다")
    @CsvSource(value = {"레오:True", "오레오:False"}, delimiterString = ":")
    @ParameterizedTest
    void should_ReturnTrue_WhenCrewExists(String nickname, boolean expected) {
        //given
        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.addCrew(new Nickname("레오"));

        //when
        boolean result = attendanceManager.isCrewExists(new Nickname(nickname));

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주어진_날짜에_크루의_출석을_반환할_수_있다")
    @Test
    void findAttendance() {
        //given
        AttendanceManager attendanceManager = new AttendanceManager();
        Nickname crewNickname = new Nickname("레오");
        attendanceManager.addCrew(crewNickname);
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        attendanceManager.addAttendance(crewNickname, attendanceDate, attendanceTime);

        //when
        Optional<Attendance> attendance = attendanceManager.findAttendance(crewNickname, attendanceDate);

        //then
        assertAll(
                () -> assertThat(attendance.isPresent()).isTrue(),
                () -> assertThat(attendance.get().isDateEquals(attendanceDate)).isTrue(),
                () -> assertThat(attendance.get().getStatus()).isEqualTo(ATTENDANCE)
        );
    }

    @DisplayName("주어진_날짜에_크루의_출석이_없을_경우_empty_를_반환한다")
    @Test
    void should_ReturnEmpty_WhenAttendanceNotExists() {
        //given
        AttendanceManager attendanceManager = new AttendanceManager();
        Nickname crewNickname = new Nickname("레오");
        attendanceManager.addCrew(crewNickname);
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);

        //when
        Optional<Attendance> attendance = attendanceManager.findAttendance(crewNickname, attendanceDate);

        //then
        assertThat(attendance.isEmpty()).isTrue();
    }

    @DisplayName("주어진_크루와_출석_날짜_시간을_기반으로_출석을_저장하고_반환할_수_있다")
    @Test
    void addAttendance() {
        //given
        AttendanceManager attendanceManager = new AttendanceManager();
        Nickname crewNickname = new Nickname("레오");
        attendanceManager.addCrew(crewNickname);
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        //when
        Attendance result = attendanceManager.addAttendance(crewNickname, attendanceDate, attendanceTime);

        //then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.isDateEquals(attendanceDate)).isTrue(),
                () -> assertThat(result.getStatus()).isEqualTo(ATTENDANCE),
                () -> {
                    Attendance expected = attendanceManager.findAttendance(crewNickname, attendanceDate).get();
                    assertThat(result).isEqualTo(expected);
                }
        );
    }

    @DisplayName("주어진_크루의_출석_기록을_수정하고_수정된_출석을_반환할_수_있다")
    @Test
    void modifyAttendance() {
        //given
        AttendanceManager attendanceManager = new AttendanceManager();
        Nickname crewNickname = new Nickname("레오");
        attendanceManager.addCrew(crewNickname);
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        attendanceManager.addAttendance(crewNickname, attendanceDate, attendanceTime);

        LocalTime modificationTime = LocalTime.of(10, 6);

        //when
        Attendance result = attendanceManager.modifyAttendance(crewNickname, attendanceDate, modificationTime);

        //then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.isDateEquals(attendanceDate)).isTrue(),
                () -> assertThat(result.getStatus()).isEqualTo(LATE),
                () -> {
                    Attendance expected = attendanceManager.findAttendance(crewNickname, attendanceDate).get();
                    assertThat(result).isEqualTo(expected);
                }
        );
    }
}
