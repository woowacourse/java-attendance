package attendance;

import attendance.view.AttendanceMenu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendanceMenuTest {

    @ParameterizedTest
    @MethodSource
    void 커맨드_입력에_알맞는_메뉴를_반환한다(String input, AttendanceMenu menu) {
        assertThat(AttendanceMenu.find(input))
                .isEqualTo(menu);
    }

    @Test
    void 올바른_커맨드_입력이_아닌_경우_예외가_발생한다() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> AttendanceMenu.find("NONE"))
                .withMessage("[ERROR] 메뉴의 알맞은 커맨드를 입력해주세요.");
    }

    static Stream<Arguments> 커맨드_입력에_알맞는_메뉴를_반환한다() {
        return Stream.of(
                Arguments.of("1", AttendanceMenu.CHECK),
                Arguments.of("2", AttendanceMenu.UPDATE),
                Arguments.of("3", AttendanceMenu.RECORD_SEARCH),
                Arguments.of("4", AttendanceMenu.RISK_SEARCH),
                Arguments.of("Q", AttendanceMenu.QUIT)
        );
    }
}
