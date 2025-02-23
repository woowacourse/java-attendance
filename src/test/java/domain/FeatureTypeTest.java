package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class FeatureTypeTest {

    @DisplayName("입력한 값에 값에 일치하는 Featuer를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1:ATTENDANCE_CHECK",
            "2:ATTENDANCE_UPDATE",
            "3:ATTENDANCE_RECORD",
            "4:READ_ABSENCE"
    }, delimiter = ':')
    void findBy(String feature, FeatureType expected) {
        //given

        //when
        FeatureType actual = FeatureType.findBy(feature);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("입력한 값이 존재하지 않는 값이라면 예외가 발생한다.")
    @Test
    void isNotFind() {
        //given
        String feature = "5";

        //when && then
        assertThatThrownBy(() -> FeatureType.findBy(feature))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 기능입니다.");
    }

    @DisplayName("입력한 값이 'Q'라면 true를 반환한다.")
    @Test
    void isExitType() {
        //given
        String feature = "Q";

        //when
        boolean actual = FeatureType.isExitType(feature);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("입력한 값이 'Q'가 아니라면 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "1", "2", "3", "4"
    })
    void isNotExitType(String feature) {
        //given

        //when
        boolean actual = FeatureType.isExitType(feature);

        //then
        assertThat(actual).isFalse();
    }
}
