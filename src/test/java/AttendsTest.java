import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendsTest {

    @Test
    void 출석_추가_테스트() throws Exception {
        //given
        final String time = "09:59";
        Attend attend = Attend.of(time);
        Attends attends = new Attends(new ArrayList<>());

        //when
        attends.addAttend(attend);

        //then
        assertThat(attends.attends).hasSize(1);
    }

    @Test
    void 날짜주면가져오는거테스트() throws Exception {
        //given
        final String time = "09:59";
        Attend attend = Attend.of(time);
        Attends attends = new Attends(new ArrayList<>());
        final int targetDay = 13;

        //when
        attends.addAttend(attend);
        Attend result = attends.findByDay(targetDay);

        //then
        assertThat(result).isEqualTo(attend);
    }

    @Test
    void 날짜_중복_시_예외_발생() throws Exception {
        //given
        Attend attend = Attend.of("1", "10:00");
        Attends attends = new Attends(new ArrayList<>());

        //when & then
        attends.addAttend(attend);
        assertThatThrownBy(() -> attends.addAttend(attend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_return_is_same_attend_by_day_true_case() {
        // given
        Attend containedAttend = Attend.of("13", "10:00");
        Attend attendToAdd = Attend.of("13", "10:01");
        Attends attends = new Attends(new ArrayList<>());
        attends.addAttend(containedAttend);

        // when
        boolean hasDayEqualsAttend = attends.hasDayEqualsAttend(attendToAdd);

        // than
        assertThat(hasDayEqualsAttend).isEqualTo(true);
    }

    @Test
    void should_return_is_same_attend_by_day_false_case() {
        // given
        Attend containedAttend = Attend.of("12", "10:00");
        Attend attendToAdd = Attend.of("13", "10:01");
        Attends attends = new Attends(new ArrayList<>());
        attends.addAttend(containedAttend);

        // when
        boolean hasDayEqualsAttend = attends.hasDayEqualsAttend(attendToAdd);

        // than
        assertThat(hasDayEqualsAttend).isEqualTo(false);
    }

    @Test
    @DisplayName("같은 날짜에 출석이 존재하지 않으면 수정할 값으로 출석을 추가하는 기능")
    void should_edit_attend_not_exist_case() {
        // given
        Attends attends = new Attends(new ArrayList<>());
        Attend attend = Attend.of("13", "10:00");

        // when
        attends.edit(attend);

        // then
        assertThat(attends.attends).hasSize(1);
    }

    @Test
    @DisplayName("같은 날짜에 출석이 존재하면 수정할 값으로 출석을 변경하는 기능")
    void should_edit_attend_exist_case() {
        // given
        Attends attends = new Attends(new ArrayList<>());
        Attend beforeAttend = Attend.of("13", "10:00");
        Attend afterAttend = Attend.of("13", "10:10");
        attends.addAttend(beforeAttend);

        // when
        attends.edit(afterAttend);

        // then
        assertThat(attends.attends).containsExactly(afterAttend);
    }
}
