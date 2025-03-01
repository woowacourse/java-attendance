package domain;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import domain.holiday.Holidays;
import domain.holiday.KoreanHoliday;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import service.date_convertor.KoreanLunarDateConvertor;

class HolidaysTest {
    
    @Nested
    class 공휴일_판단_테스트 {
        
        private final Holidays sut = new Holidays(
                new KoreanLunarDateConvertor(),
                List.of(KoreanHoliday.values())
        );
        
        @ParameterizedTest
        @CsvSource({
                "2024-01-01", "2025-01-01",
                "2024-05-05", "2025-05-05",
                "2024-06-06", "2025-06-06",
                "2024-08-15", "2025-08-15",
                "2024-12-25", "2025-12-25",
        })
        void 양력_공휴일을_판단한다(String dateValue) {
            //given
            var date = LocalDate.parse(dateValue);
            
            //when
            var result = sut.isHoliday(date);
            
            //then
            assertThat(result).isTrue();
        }
        
        @ParameterizedTest
        @CsvSource({
                "2024-02-10", "2025-01-29",
                "2024-05-15", "2025-05-05",
                "2024-09-17", "2025-10-06",
        })
        void 음력_공휴일을_판단한다(String dateValue) {
            //given
            var date = LocalDate.parse(dateValue);
            
            //when
            var result = sut.isHoliday(date);
            
            //then
            assertThat(result).isTrue();
        }
    }
    
}
