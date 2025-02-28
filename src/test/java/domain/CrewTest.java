package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    @DisplayName("닉네임을 통해 Crew를 성공적으로 생성")
    void createCrewTest() {
        // given
        String nickName = "차니";

        // when, then
        assertThatCode(() -> Crew.of(nickName))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Crew의 이름이 같으면 같은 객체로 판단")
    void equalsCrewTest() {
        // given
        String nickName = "차니";
        Crew crew = Crew.of(nickName);
        Crew compared = Crew.of("차니");

        // when
        boolean isEqual = crew.equals(compared);

        // then
        assertThat(isEqual).isTrue();
    }

    @Test
    @DisplayName("닉네임이 2글자 미만이면 Crew 생성 시 예외 발생")
    void nameLengthUnderTwoThrowException() {
        // given
        String nickName = "찬";

        // when, then
        assertThatThrownBy(() -> Crew.of(nickName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 2글자 이상 4글자 이하여야 합니다.");
    }

    @Test
    @DisplayName("닉네임이 4글자 초과면 Crew 생성 시 예외 발생")
    void nameLengthOverFourThrowException() {
        // given
        String nickName = "차니차니찬";

        // when, then
        assertThatThrownBy(() -> Crew.of(nickName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 2글자 이상 4글자 이하여야 합니다.");
    }

    @Test
    @DisplayName("크루 정렬 기준 테스트")
    void compareToCrewTest() {
        // given
        Crew bigger = Crew.of("bca");
        Crew smaller = Crew.of("abc");
        Crew sameSmaller = Crew.of("abc");

        // when
        int positive = bigger.compareTo(smaller);
        int negative = smaller.compareTo(bigger);
        int zero = smaller.compareTo(sameSmaller);

        // then
        assertThat(positive).isGreaterThan(0);
        assertThat(negative).isLessThan(0);
        assertThat(zero).isEqualTo(0);
    }
}
