package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class RepeatUntilUserQuitSelectUtilTest {

    @Test
    @DisplayName("사용자가 true를 반환하면 반복이 유지되고, false를 반환하면 반복이 종료된다.")
    void whenTrueKeepLoopAndWhenFalseStopLoop() {
        // given
        List<Boolean> responses = List.of(true, true, false);
        Iterator<Boolean> iterator = responses.iterator();
        AtomicInteger count = new AtomicInteger(0);

        RepeatUntilUserQuitSelectUtil.ThrowingSupplier<Boolean, FileReadException> supplier = () -> {
            count.incrementAndGet();
            return iterator.next();
        };

        // when
        RepeatUntilUserQuitSelectUtil.repeat(supplier);

        // then
        assertThat(count.get()).isEqualTo(3); // 3번 실행 후 종료 확인
    }
}
