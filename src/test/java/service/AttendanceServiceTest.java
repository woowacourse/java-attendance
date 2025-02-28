package service;

import domain.AttendanceStorage;
import java.io.FileNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import view.AttendanceFileReader;

class AttendanceServiceTest {
    @Test
    void checkNicknameRegistered1() throws FileNotFoundException {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);
        AttendanceService attendanceService = new AttendanceService(attendanceStorage);

        // when
        boolean actual = attendanceService.checkNicknameRegistered("히스타");

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void checkNicknameRegistered2() throws FileNotFoundException {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);
        AttendanceService attendanceService = new AttendanceService(attendanceStorage);

        // when
        boolean actual = attendanceService.checkNicknameRegistered("빙티");

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
