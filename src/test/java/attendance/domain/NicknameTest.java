package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("닉네임 테스트")
public class NicknameTest {

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getEmptyOrNullNickname")
    void 닉네임이_빈칸이거나_널이면_예외가_발생한다(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루의 닉네임은 반드시 존재해야 합니다.");
    }

    static Stream<Arguments> getEmptyOrNullNickname() {
        return Stream.of(
            Arguments.of("", "empty"),
            Arguments.of(" ", "blank"),
            Arguments.of(null, "null")
        );
    }
}
