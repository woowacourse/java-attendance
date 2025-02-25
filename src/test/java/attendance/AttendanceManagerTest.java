package attendance;

import static org.assertj.core.api.Assertions.assertThat;

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
}
