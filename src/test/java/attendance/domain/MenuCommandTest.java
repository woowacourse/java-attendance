package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MenuCommandTest {
    @DisplayName("기능: 메뉴 명령어에 대한 대응하는 출석 옵션 명령어 반환")
    @Test
    void checkMenuCommandAttend() {
        MenuCommand menuCommand = MenuCommand.toCommand("1");
        assertThat(menuCommand.toString()).isEqualTo("ATTEND");
    }

    @DisplayName("기능: 메뉴 명령어에 대한 대응하는 수정 옵션 명령어 반환")
    @Test
    void checkMenuCommandModify() {
        MenuCommand menuCommand = MenuCommand.toCommand("2");
        assertThat(menuCommand.toString()).isEqualTo("MODIFY");
    }

    @DisplayName("기능: 메뉴 명령어에 대한 대응하는 조회 옵션 명령어 반환")
    @Test
    void checkMenuCommandLookup() {
        MenuCommand menuCommand = MenuCommand.toCommand("3");
        assertThat(menuCommand.toString()).isEqualTo("LOOKUP");
    }

    @DisplayName("기능: 메뉴 명령어에 대한 대응하는 제적 옵션 명령어 반환")
    @Test
    void checkMenuCommandExpel() {
        MenuCommand menuCommand = MenuCommand.toCommand("4");
        assertThat(menuCommand.toString()).isEqualTo("EXPEL");
    }

    @DisplayName("기능: 메뉴 명령어에 대한 대응하는 종료 옵션 명령어 반환")
    @Test
    void checkMenuCommandQuit() {
        MenuCommand menuCommand = MenuCommand.toCommand("Q");
        assertThat(menuCommand.toString()).isEqualTo("QUIT");
    }

    @DisplayName("예외: 올바르지 않은 메뉴 명령어 입력에 대한 처리")
    @ValueSource(strings = {"5", "일", "q"})
    @ParameterizedTest
    void causeExceptionForWrongMenuCommand(String input) {
        assertThatThrownBy(() -> MenuCommand.toCommand(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
