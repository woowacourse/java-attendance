package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Test
    void 출석_일자를_알려주면_출석이_생성된다() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 26, 10, 0);

        Attendance attendance = new Attendance(localDateTime);

        assertThat(attendance).isEqualTo(new Attendance(localDateTime));
    }

    @Test
    void 출석_일자를_알려주면_결석_처리된_출석을_생성한다() {
        LocalDate absentDate = LocalDate.of(2025, 2, 28);

        Attendance absentAttendance = Attendance.absent(absentDate);

        assertThat(absentAttendance.calculateStatus()).isEqualByComparingTo(AttendanceStatus.ABSENCE);
    }

    @CsvSource(value = {
            "2,23,10,0,",
            "3,1,10,0,",
            "2,24,23,1"
    })
    @ParameterizedTest
    void 출석_가능_일자가_아니면_출석을_생성할_수_없다(int month, int day, int hour, int minute) {
        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2025, month, day, hour, minute)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_날짜와_같은지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));

        assertThat(attendance.isSameDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 다른_출석_기록을_알려주면_같은_출석_날짜인지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendance otherAttendance = new Attendance(LocalDateTime.of(2025, 2, day, 9, 0));

        assertThat(attendance.isSameDate(otherAttendance)).isEqualTo(expected);
    }

    @Test
    void 수정_일자를_알려주면_출석_시간을_수정해_반환한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        LocalDateTime modificationDateTime = LocalDateTime.of(2025, 2, 26, 9, 50);

        assertThat(attendance.changeTime(modificationDateTime)).isEqualTo(new Attendance(modificationDateTime));
    }

    @CsvSource(value = {
            "26,true", "25,false"
    })
    @ParameterizedTest
    void 날짜가_출석_날짜보다_과거이거나_동일한지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));

        assertThat(attendance.isSameDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @CsvSource(value = {"24,true", "25,false"})
    @ParameterizedTest
    void 출석_날짜가_월요일인지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, day, 10, 0));

        assertThat(attendance.isMonday()).isEqualTo(expected);
    }

    @CsvSource(value = {"10,5,true", "10,6,false"})
    @ParameterizedTest
    void 현재_출석이_출석_완료인지_알려준다(int hour, int minute, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 27, hour, minute));

        assertThat(attendance.isAttendanceComplete()).isEqualTo(expected);
    }

    @CsvSource(value = {
            "27,10,6,true", "27,10,30,true", "24,13,6,true", "24,13,30,true",
            "27,10,5,false", "27,10,31,false", "24,13,5,false", "24,13,31,false"
    })
    @ParameterizedTest
    void 현재_출석이_지각인지_알려준다(int day, int hour, int minute, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, day, hour, minute));

        assertThat(attendance.isLate()).isEqualTo(expected);
    }

    @CsvSource(value = {
            "27,10,31,true", "24,13,31,true",
            "27,10,30,false", "24,13,30,false"
    })
    @ParameterizedTest
    void 현재_출석이_결석인지_알려준다(int day, int hour, int minute, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, day, hour, minute));

        assertThat(attendance.isAbsence()).isEqualTo(expected);
    }

    @CsvSource(value = {"31,ABSENT", "30,LATE", "6,LATE", "5,ATTENDANCE_COMPLETE"})
    @ParameterizedTest
    void 현재_출석의_상태를_알려준다(int minute, AttendanceStatus expectedStatus) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 27, 10, minute));

        assertThat(attendance.calculateStatus()).isEqualByComparingTo(expectedStatus);
    }

}
