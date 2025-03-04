package study;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Nickname;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EqualsAndHashCodeTest {

    private static Nickname nickname1;
    private static Nickname nickname2;

    @BeforeAll
    static void beforeAll() {
        nickname1 = new Nickname("칼리");
        nickname2 = new Nickname("칼리");
    }

    @Test
    @DisplayName("equals와 hashCode 이해를 위한 테스트")
    void equalsTest() {

        // given
        // when
        final boolean equals = nickname1.equals(nickname2);

        // then
        System.out.println(nickname1.hashCode());
        System.out.println(nickname2.hashCode());
        Assertions.assertThat(equals).isTrue();
    }

    @Test
    @DisplayName("List에 들어갔을 때 size 확인 테스트")
    void addList() {

        // given
        final List<Nickname> nicknames = new ArrayList<>();

        // when
        nicknames.add(nickname1);
        nicknames.add(nickname2);

        // then
        Assertions.assertThat(nicknames.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Set에 들어갔을 때 같은 객체로 인식하는 지 확인")
    void addSet() {

        // given
        final Set<Nickname> nicknames = new HashSet<>();

        // when
        nicknames.add(nickname1);
        nicknames.add(nickname2);

        // then
        Assertions.assertThat(nicknames.size()).isEqualTo(1);
    }
}
