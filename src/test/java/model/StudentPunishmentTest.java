package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentPunishmentTest {
    @Test
    @DisplayName("제적 결정 테스트")
    void test1() {
        Assertions.assertEquals(
                StudentPunishment.DISMISSAl, StudentPunishment.calculatePunishment(5)
        );
    }

    @Test
    @DisplayName("면담 결정 테스트")
    void test2() {
        Assertions.assertEquals(
                StudentPunishment.INTERVIEW, StudentPunishment.calculatePunishment(3)
        );
    }

    @Test
    @DisplayName("경고 결정 테스트")
    void test3() {
        Assertions.assertEquals(
                StudentPunishment.WARNING, StudentPunishment.calculatePunishment(2)
        );
    }

    @Test
    @DisplayName("안전 결정 테스트")
    void test4() {
        Assertions.assertEquals(
                StudentPunishment.SAFE, StudentPunishment.calculatePunishment(1)
        );
    }
}
