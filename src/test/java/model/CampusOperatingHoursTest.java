package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CampusOperatingHoursTest {

    @Test
    @DisplayName("캠퍼스 운영 시간 전 예외 테스트")
    void 캠퍼스_운영_시간_전_예외_테스트() {
        LocalTime testTime = LocalTime.of(7,59);
        assertThrows(
                IllegalArgumentException.class,
                ()->CampusOperatingHours.validateOperatingHours(testTime)
        );
    }

    @Test
    @DisplayName("캠퍼스 운영 시간 종료 후 예외 테스트")
    void 캠퍼스_운영_시간_종료_후_예외_테스트() {
        LocalTime testTime = LocalTime.of(23,1);
        assertThrows(
                IllegalArgumentException.class,
                ()->CampusOperatingHours.validateOperatingHours(testTime)
        );
    }
}