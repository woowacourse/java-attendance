package attendance.domain.crew;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CrewTest {

    @DisplayName("닉네임이 공백일 경우 크루를 생성할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void 닉네임이_공백일_경우_크루를_생성할_수_없다(String input) {
        assertThatCode(() -> new Crew(input))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.BLANK_NICKNAME.getMessage());
    }

    @DisplayName("닉네임이 동일한지 체크할 수 있다")
    @Test
    void 닉네임이_동일한지_체크할_수_있다() {
        String expectedName = "쿠키";
        Crew crew = new Crew(expectedName);

        assertThat(crew.isSameNickname(expectedName)).isTrue();
    }
}