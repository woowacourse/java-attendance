package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    @DisplayName("1 입력 시 출석 반환")
    void attendFromCodeTest() {
        // given
        String code = "1";

        // when
        Command command = Command.from(code);

        // then
        assertThat(command).isEqualTo(Command.ATTEND);
    }

    @Test
    @DisplayName("2 입력 시 수정 반환")
    void modifyFromCodeTest() {
        // given
        String code = "2";

        // when
        Command command = Command.from(code);

        // then
        assertThat(command).isEqualTo(Command.MODIFY_ATTENDANCE);
    }

    @Test
    @DisplayName("3 입력 시 기록 조회 반환")
    void readLogFromCodeTest() {
        // given
        String code = "3";

        // when
        Command command = Command.from(code);

        // then
        assertThat(command).isEqualTo(Command.READ_ATTENDANCE_LOG);
    }

    @Test
    @DisplayName("4 입력 시 위험자 조회 반환")
    void readDisciplinaryFromCodeTest() {
        // given
        String code = "4";

        // when
        Command command = Command.from(code);

        // then
        assertThat(command).isEqualTo(Command.READ_DISCIPLINARY_CREWS);
    }

    @Test
    @DisplayName("Q 입력 시 출석 반환")
    void quitFromCodeTest() {
        // given
        String code = "Q";

        // when
        Command command = Command.from(code);

        // then
        assertThat(command).isEqualTo(Command.QUIT);
    }

    @Test
    @DisplayName("유효하지 않은 값 입력 시 예외 발생")
    void givenInvalidCodeThrowException() {
        // given
        String code = "0";

        // when, then
        assertThatThrownBy(() -> Command.from(code))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
