package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepeatUntilUserQuitSelectUtilTest {

    @Test
    @DisplayName("사용자가 true를 반환하면 반복이 유지되고, false를 반환하면 반복이 종료된다.")
    void whenTrueKeepLoopAndWhenFalseStopLoop() {
        // given
        List<Boolean> responses = List.of(true, true, false);
        Iterator<Boolean> iterator = responses.iterator();

        Supplier<Boolean> supplier = iterator::next;

        // when
        // then
        RepeatUntilUserQuitSelectUtil.repeat(supplier);
    }

    @Test
    @DisplayName("일반 예외 발생 시, 예외 메시지를 출력한 후 다시 던진다 (종료)")
    void shouldThrowExceptionWhenOtherExceptionOccurs() {
        // given
        Supplier<Boolean> supplier = () -> {
            throw new RuntimeException("예상치 못한 오류 발생");
        };

        // when
        // then
        assertThatThrownBy(() -> RepeatUntilUserQuitSelectUtil.repeat(supplier))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("예상치 못한 오류 발생");
    }
}