package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceStorageTest {
    @Test
    void containsSameNicknameTest1() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        attendanceStorage.add(Crew.from("히스타"));

        // when
        boolean actual = attendanceStorage.containsSameNickname("히스타");

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void containsSameNicknameTest2() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        attendanceStorage.add(Crew.from("히스타"));

        // when
        boolean actual = attendanceStorage.containsSameNickname("히로");

        // then
        Assertions.assertThat(actual).isFalse();
    }
}
