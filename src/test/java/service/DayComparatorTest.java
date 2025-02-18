package service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DayComparatorTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 25})
    void test1(int value){
        LocalDateTime today = LocalDateTime.of(2024,12,25,10,0);
        Assertions.assertTrue(DayComparator.isHoliday(value, today));
    }
}