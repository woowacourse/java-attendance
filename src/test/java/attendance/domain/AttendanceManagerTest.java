package attendance.domain;

import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceManagerTest {
    @Nested
    class isCrewExists {
        @DisplayName("주어진_닉네임의_크루가_존재하면_true_를_반환한다")
        @Test
        void should_ReturnTrue_WhenCrewExists() {
            //given
            AttendanceManager attendanceManager = new AttendanceManager();
            String crewNickname = "레오";
            attendanceManager.addCrew(new Crew(crewNickname));

            //when
            boolean result = attendanceManager.isCrewExists(new Crew(crewNickname));

            //then
            assertThat(result).isTrue();
        }

        @DisplayName("주어진_닉네임의_크루가_존재하지_않으면_false_를_반환한다")
        @Test
        void should_ReturnFalse_WhenCrewNotExists() {
            //given
            AttendanceManager attendanceManager = new AttendanceManager();

            //when
            boolean result = attendanceManager.isCrewExists(new Crew("레오"));

            //then
            assertThat(result).isFalse();
        }
    }

    @Nested
    class findAttendance {
        @DisplayName("주어진_날짜에_크루의_출석을_반환할_수_있다")
        @Test
        void can_ReturnAttendance_WhenAttendanceExists() {
            //given
            AttendanceManager attendanceManager = new AttendanceManager();
            String crewNickname = "레오";
            Crew crew = new Crew(crewNickname);
            attendanceManager.addCrew(crew);
            LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
            LocalTime attendanceTime = LocalTime.of(10, 0);
            attendanceManager.addAttendance(crew, attendanceDate, attendanceTime);

            //when
            Optional<Attendance> attendance = attendanceManager.findAttendance(crew, attendanceDate);

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
            String crewNickname = "레오";
            Crew crew = new Crew(crewNickname);
            attendanceManager.addCrew(crew);
            LocalDate attendanceDate = LocalDate.of(2024, 12, 26);

            //when
            Optional<Attendance> attendance = attendanceManager.findAttendance(crew, attendanceDate);

            //then
            assertThat(attendance.isEmpty()).isTrue();
        }
    }

    @Nested
    class addAttendance {
        @DisplayName("주어진_크루와_출석_날짜_시간을_기반으로_출석을_저장하고_반환할_수_있다")
        @Test
        void can_SaveAndReturnAttendance_WhenDoAttendance() {
            //given
            AttendanceManager attendanceManager = new AttendanceManager();
            String crewNickname = "레오";
            Crew crew = new Crew(crewNickname);
            attendanceManager.addCrew(crew);
            LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
            LocalTime attendanceTime = LocalTime.of(10, 0);

            //when
            Attendance result = attendanceManager.addAttendance(crew, attendanceDate, attendanceTime);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.isDateEquals(attendanceDate)).isTrue(),
                    () -> assertThat(result.getStatus()).isEqualTo(ATTENDANCE),
                    () -> {
                        Attendance expected = attendanceManager.findAttendance(crew, attendanceDate).get();
                        assertThat(result).isEqualTo(expected);
                    }
            );
        }
    }

    @Nested
    class modifyAttendance {
        @DisplayName("주어진_크루의_출석_기록을_수정하고_수정된_출석을_반환할_수_있다")
        @Test
        void should_SaveAndReturnAttendance_WhenModifyAttendance() {
            //given
            AttendanceManager attendanceManager = new AttendanceManager();
            String crewNickname = "레오";
            Crew crew = new Crew(crewNickname);
            attendanceManager.addCrew(crew);
            LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
            LocalTime attendanceTime = LocalTime.of(10, 0);
            attendanceManager.addAttendance(crew, attendanceDate, attendanceTime);

            LocalTime modificationTime = LocalTime.of(10, 6);

            //when
            Attendance result = attendanceManager.modifyAttendance(crew, attendanceDate, modificationTime);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.isDateEquals(attendanceDate)).isTrue(),
                    () -> assertThat(result.getStatus()).isEqualTo(LATE),
                    () -> {
                        Attendance expected = attendanceManager.findAttendance(crew, attendanceDate).get();
                        assertThat(result).isEqualTo(expected);
                    }
            );
        }
    }
}
