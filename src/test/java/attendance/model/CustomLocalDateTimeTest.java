//package attendance.model;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//class CustomLocalDateTimeTest {
//
//    @Test
//    void 커스텀_LocalDateTIme을_받아온다() {
//        assertThat(CustomLocalDateTime.now())
//                .isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 0));
//    }
//
//    @Test
//    void 평일은_쉬는날이_아니다() {
//        //given
//        LocalDate localDate = LocalDate.of(2024, 12, 10);
//
//        //when
//        boolean holiday = CustomLocalDateTime.isWeekendOrHoliday(localDate);
//
//        //then
//        Assertions.assertThat(holiday).isFalse();
//    }
//
//    @Test
//    void 공휴일로_지정된_날짜는_쉬는날이다() {
//        //given
//        LocalDate localDate = LocalDate.of(2024, 12, 25);
//
//        //when
//        boolean holiday = CustomLocalDateTime.isWeekendOrHoliday(localDate);
//
//        //then
//        Assertions.assertThat(holiday).isTrue();
//    }
//
//    @Test
//    void 주말은_쉬는날이다() {
//        //given
//        LocalDate localDate = LocalDate.of(2024, 12, 8);
//
//        //when
//        boolean holiday = CustomLocalDateTime.isWeekendOrHoliday(localDate);
//
//        //then
//        Assertions.assertThat(holiday).isTrue();
//    }
//}