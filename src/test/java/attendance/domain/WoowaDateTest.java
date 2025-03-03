package attendance.domain;

import static attendance.util.DateFormatUtil.DATE_FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WoowaDateTest {

    @Test
    @DisplayName("교육일에 해당하는 날짜로 WoowaDate 생성 시 정상적으로 객체가 생성된다.")
    void testValidEducationDateCreation() {
        // given
        LocalDate validDate = LocalDate.of(2024, 12, 16);
        EducationDayPolicy policy = new EducationDayPolicy(Set.of());

        // when
        WoowaDate woowaDate = new WoowaDate(validDate, policy);

        // then
        assertThat(woowaDate.toLocalDate()).isEqualTo(validDate);
        assertThat(woowaDate.getDayOfWeek()).isEqualTo(validDate.getDayOfWeek());
    }

    @Test
    @DisplayName("교육일이 아닌 날짜(주말)로 WoowaDate 생성 시 예외가 발생한다.")
    void testInvalidEducationDateWeekend() {
        // given
        LocalDate invalidDate = LocalDate.of(2024, 12, 14);
        EducationDayPolicy policy = new EducationDayPolicy(Set.of());

        // when & then
        assertThatThrownBy(() -> new WoowaDate(invalidDate, policy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(invalidDate.format(DATE_FORMATTER)); // 예시 메시지, 실제 메시지에 맞게 수정
    }

    @Test
    @DisplayName("교육일이 아닌 날짜(공휴일)로 WoowaDate 생성 시 예외가 발생한다.")
    void testInvalidEducationDateHoliday() {
        // given
        LocalDate invalidDate = LocalDate.of(2024, 12, 25);
        EducationDayPolicy policy = new EducationDayPolicy(Set.of(invalidDate));

        // when & then
        assertThatThrownBy(() -> new WoowaDate(invalidDate, policy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(invalidDate.format(DATE_FORMATTER));
    }

    @Test
    @DisplayName("동일한 날짜로 생성한 WoowaDate 객체는 equals와 hashCode가 동일해야 한다.")
    void testEqualsAndHashCode() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 16);
        EducationDayPolicy policy = new EducationDayPolicy(Set.of());
        WoowaDate date1 = new WoowaDate(date, policy);
        WoowaDate date2 = new WoowaDate(date, policy);

        // when & then
        assertThat(date1).isEqualTo(date2);
        assertThat(date1.hashCode()).isEqualTo(date2.hashCode());
    }
}