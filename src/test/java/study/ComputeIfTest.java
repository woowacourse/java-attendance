package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ComputeIfTest {

    @Test
    @DisplayName("computeIfAbsent의 작동 방식 공부 테스트: key가 있을 때")
    void computeIfAbsent1() {

        // given
        final Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);

        // when
        final int result = map.computeIfAbsent("a", k -> 0);

        // then
        for (final String s : map.keySet()) {
            System.out.println("s = " + s + ", value = " + map.get(s));
        }
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("computeIfAbsent의 작동 방식 공부 테스트: key가 없을 때")
    void computeIfAbsent2() {

        // given
        final Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);

        // when
        final int result = map.computeIfAbsent("b", key -> 0);

        // then
        for (final String s : map.keySet()) {
            System.out.println("s = " + s + ", value = " + map.get(s));
        }

        System.out.println("result = " + result);
    }
}
