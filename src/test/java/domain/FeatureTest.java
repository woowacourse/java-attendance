package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Nested
public class FeatureTest {

    @Nested
    @DisplayName("함수 정보 생성 테스트")
    class createFeatureTest {

        @Test
        @DisplayName("출석 확인 기능을 확인한다.")
        void createAttendanceCheck() {
            String input = "1";
            Feature feature = Feature.of(input);
            assertThat(feature).isEqualTo(Feature.ATTENDANCE_CHECK);
        }

        @Test
        @DisplayName("출석 수정 기능을 확인한다.")
        void createAttendanceEdit() {
            String input = "2";
            Feature feature = Feature.of(input);
            assertThat(feature).isEqualTo(Feature.ATTENDANCE_EDIT);
        }

        @Test
        @DisplayName("크루 출석 확인 기능을 확인한다.")
        void createCrewRecordsCheck() {
            String input = "3";
            Feature feature = Feature.of(input);
            assertThat(feature).isEqualTo(Feature.CREW_RECORDS_CHECK);
        }

        @Test
        @DisplayName("제적 위험자 확인 기능을 확인한다.")
        void createExpelledWarningCheck() {
            String input = "4";
            Feature feature = Feature.of(input);
            assertThat(feature).isEqualTo(Feature.EXPELLED_WARNING_CHECK);
        }

        @Test
        @DisplayName("종료 기능을 확인한다.")
        void createExit() {
            String input = "Q";
            Feature feature = Feature.of(input);
            assertThat(feature).isEqualTo(Feature.EXIT);
        }
    }

    @Nested
    @DisplayName("제공되는 기능 확인 테스트")
    class checkProvidedTest {

        @ParameterizedTest
        @ValueSource(strings = {"1", "2", "3", "4", "Q"})
        @DisplayName("제공되는 기능을 확인한다.")
        void checkProvided(String input) {
            assertThatNoException().isThrownBy(() -> Feature.validateProvided(input));
        }

        @ParameterizedTest
        @ValueSource(strings = {"6", "ok", "go", "y"})
        @DisplayName("제공되지 않는 기능을 확인한다.")
        void checkNotProvided(String input) {
            assertThatThrownBy(() -> Feature.validateProvided(input));
        }
    }
}