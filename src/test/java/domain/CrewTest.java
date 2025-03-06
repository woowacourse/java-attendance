package domain;

import exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrewTest {
    @Test
    @DisplayName("올바른 이름이면 Crew를 정상적으로 생성")
    void createCrewTest() {
        //given
        String name = "조로";
        //when
        //then
        assertThatNoException().isThrownBy(() -> Crew.of(name));
    }

    @Test
    @DisplayName("이름이 NULL일 경우 예외 발생")
    void nonNullNameTest() {
        //given
        String name = null;
        //when
        //then
        assertThatThrownBy(() -> Crew.of(name)).isInstanceOf(AppException.class).hasMessageContaining(AppException.PREFIX);

    }

    @DisplayName("이름이 2글자에서 4글자 사이가 아니면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"가", "가나다라마", "아아아아아아"})
    void outOfRangeNameException(final String name) {
        assertThatThrownBy(() -> Crew.of(name)).isInstanceOf(AppException.class).hasMessageContaining(AppException.PREFIX);
    }

    @DisplayName("이름이 한글이 아니면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"dab", "a1", "가1나2"})
    void nonKoreanNameException(final String name) {
        assertThatThrownBy(() -> Crew.of(name)).isInstanceOf(AppException.class).hasMessageContaining(AppException.PREFIX);
    }
}