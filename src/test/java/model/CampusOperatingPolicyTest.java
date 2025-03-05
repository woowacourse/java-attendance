package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import model.policy.CampusOperatingPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CampusOperatingPolicyTest {
    @DisplayName("캠퍼스 운영 날짜인지 확인한다")
    @Test
    void campusOperatingDateTest() {
        assertAll(
                () -> assertThat(CampusOperatingPolicy.isOperatingDate(LocalDate.of(2024, 12, 2))).isTrue(),
                () -> assertThat(CampusOperatingPolicy.isOperatingDate(LocalDate.of(2024, 12, 3))).isTrue(),
                () -> assertThat(CampusOperatingPolicy.isOperatingDate(LocalDate.of(2024, 12, 1))).isFalse(),
                () -> assertThat(CampusOperatingPolicy.isOperatingDate(LocalDate.of(2024, 12, 7))).isFalse(),
                () -> assertThat(CampusOperatingPolicy.isOperatingDate(LocalDate.of(2024, 12, 25))).isFalse()
        );
    }

    @DisplayName("캠퍼스 운영 시간인지 확인한다")
    @Test
    void campusOperatingTimeTest() {
        assertAll(
                () -> assertThat(CampusOperatingPolicy.isOperatingTime(LocalTime.of(8, 0))).isTrue(),
                () -> assertThat(CampusOperatingPolicy.isOperatingTime(LocalTime.of(7, 59))).isFalse(),
                () -> assertThat(CampusOperatingPolicy.isOperatingTime(LocalTime.of(23, 0))).isTrue(),
                () -> assertThat(CampusOperatingPolicy.isOperatingTime(LocalTime.of(23, 1))).isFalse()
        );
    }
}
