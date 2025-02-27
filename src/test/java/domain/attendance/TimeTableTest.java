package domain.attendance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TimeTableTest {
    @Nested
    class TimeTableOperationTest{
        @DisplayName("캠퍼스 운영 시간이 아닌 경우 False 반환.")
        @ParameterizedTest
        @ValueSource(ints = {0,1,2,3,4,5,6,7,23})
        void isNotCampusOperatingTime(int hour){
            LocalTime localTime = LocalTime.of(hour,1);
            assertThat(TimeTable.isOnCampusOperatingTime(localTime)).isFalse();
        }

        @DisplayName("캠퍼스 운영 시간인 경우 True를 반환.")
        @ParameterizedTest
        @ValueSource(ints = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23})
        void isCampusOperatingTime(int hour){
            LocalTime localTime = LocalTime.of(hour,0);
            assertThat(TimeTable.isOnCampusOperatingTime(localTime)).isTrue();
        }
    }
}