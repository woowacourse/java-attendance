package domain;

import static domain.MenuOption.CHECK_ATTENDANCE;
import static domain.MenuOption.CHECK_CREW_ATTENDANCE_HISTORY;
import static domain.MenuOption.CHECK_DANGEROUS_CREW;
import static domain.MenuOption.MODIFY_ATTENDANCE;
import static domain.MenuOption.QUIT_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuOptionTest {
    @DisplayName("옵션 값 반환 테스트")
    @Test
    void test1() {
        Assertions.assertAll(
                () -> assertThat(CHECK_ATTENDANCE.getValue()).isEqualTo("1"),
                () -> assertThat(MODIFY_ATTENDANCE.getValue()).isEqualTo("2"),
                () -> assertThat(CHECK_CREW_ATTENDANCE_HISTORY.getValue()).isEqualTo("3"),
                () -> assertThat(CHECK_DANGEROUS_CREW.getValue()).isEqualTo("4"),
                () -> assertThat(QUIT_REGEX.getValue()).isEqualTo("[Qq]")
        );
    }
    @DisplayName("옵션 객체 반환 테스트")
    @Test
    void test2() {
        Assertions.assertAll(
                () -> assertThat(MenuOption.getMenuOption("1")).isEqualTo(CHECK_ATTENDANCE),
                () -> assertThat(MenuOption.getMenuOption("2")).isEqualTo(MODIFY_ATTENDANCE),
                () -> assertThat(MenuOption.getMenuOption("3")).isEqualTo(CHECK_CREW_ATTENDANCE_HISTORY),
                () -> assertThat(MenuOption.getMenuOption("4")).isEqualTo(CHECK_DANGEROUS_CREW),
                () -> assertThat(MenuOption.getMenuOption("[Qq]")).isEqualTo(QUIT_REGEX)
        );
    }
}