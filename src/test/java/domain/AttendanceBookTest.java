package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AttendanceBookTest {

    @Test
    void 닉네임과_출석일시를_입력하여_출석기록을_생성한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);

        assertThat(attendanceBook.hasCrew(name)).isTrue();
    }

    @Test
    void 닉네임_날짜_시간으로_출석하면_출석기록에_반영된다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDateTime newDateTime = LocalDateTime.of(2024, 12, 15, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);
        attendanceBook.attend(name, newDateTime.toLocalDate(), newDateTime.toLocalTime());

        assertThat(attendanceBook.hasAttendanceDate(name, newDateTime.toLocalDate())).isTrue();
    }

    @Test
    void 닉네임_날짜_시간으로_출석을_수정한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 15);
        final LocalTime newTime = LocalTime.of(10, 0);

        AttendanceBook attendanceBook = new AttendanceBook();

        attendanceBook.initAttendance(name, dateTime);
        assertThat(attendanceBook.findAttendanceDateByNameAndDate(name, dateTime.toLocalDate()).getTime())
                .isEqualTo(dateTime.toLocalTime());

        attendanceBook.edit(name, dateTime.toLocalDate(), newTime);
        assertThat(attendanceBook.findAttendanceDateByNameAndDate(name, dateTime.toLocalDate()).getTime())
                .isEqualTo(newTime);
    }


    @Test
    void 크루의_지각_횟수를_계산한다() {
        final String name = "시소";
        final LocalDateTime initDate = LocalDateTime.of(2024, 12, 10, 10, 0);

        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 10), LocalDateTime.of(2024, 12, 12, 10, 10),
                LocalDateTime.of(2024, 12, 13, 10, 10), LocalDateTime.of(2024, 12, 14, 10, 10));

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, initDate);
        dateTimes.forEach(dateTime -> attendanceBook.attend(name, dateTime.toLocalDate(), dateTime.toLocalTime()));

        assertThat(attendanceBook.getTardyCount(name)).isEqualTo(dateTimes.size());
    }

    @Test
    void 크루의_결석_횟수를_계산한다() {
        final String name = "시소";
        final LocalDateTime initDate = LocalDateTime.of(2024, 12, 10, 10, 0);

        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 15, 10), LocalDateTime.of(2024, 12, 12, 15, 10),
                LocalDateTime.of(2024, 12, 13, 15, 10), LocalDateTime.of(2024, 12, 14, 15, 10));

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, initDate);
        dateTimes.forEach(dateTime -> attendanceBook.attend(name, dateTime.toLocalDate(), dateTime.toLocalTime()));

        assertThat(attendanceBook.getAbsenceCount(name)).isEqualTo(dateTimes.size());
    }

    @Test
    void 등록되지_않은_크루를_조회할_경우_예외가_발생한다() {
        final String name = "시소";

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatThrownBy(() -> attendanceBook.validateHasCrew(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등록된_크루를_조회할_경우_예외는_발생하지_않는다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);

        assertThatNoException().isThrownBy(() -> attendanceBook.validateHasCrew(name));
    }

    @Test
    void 이미_출석된_일자에_출석하려는_경우_예외가_발생한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);

        assertThatThrownBy(() -> attendanceBook.validateBeforeAdd(name, dateTime.toLocalDate()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석기록이_없을_때_출석을_시도하는_경우_예외는_발생하지_않는다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDate newDate = LocalDate.of(2024, 12, 15);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);

        assertThatNoException().isThrownBy(() -> attendanceBook.validateBeforeAdd(name, newDate));
    }

    @Test
    void 출석되지_않은_일자를_수정하려는_경우_예외가_발생한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDate newDate = LocalDate.of(2024, 12, 15);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);

        assertThatThrownBy(() -> attendanceBook.validateBeforeEdit(name, newDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석된_일자를_수정하려는_경우_예외가_발생하지_않는다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(name, dateTime);

        assertThatNoException().isThrownBy(() -> attendanceBook.validateBeforeEdit(name, dateTime.toLocalDate()));
    }

    @Test
    void 등교일이_아닌_경우_예외가_발생한다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatThrownBy(() -> attendanceBook.validateIsWeekday(dateTime.toLocalDate()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등교일인_경우_예외가_발생하지_않는다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 13, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatNoException().isThrownBy(() -> attendanceBook.validateIsWeekday(dateTime.toLocalDate()));
    }

    @Test
    void 캠퍼스_운영시간이_아닌_경우_예외가_발생한다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 7, 5);

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatThrownBy(() -> attendanceBook.validateIsInRunningTime(dateTime.toLocalTime()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 캠퍼스_운영시간인_경우_예외가_발생하지_않는다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatNoException().isThrownBy(() -> attendanceBook.validateIsInRunningTime(dateTime.toLocalTime()));
    }

    @Test
    void 이번달_시작일부터_오늘까지에_포함되지_않는_날짜인_경우_예외가_발생한다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 20, 7, 5);

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatThrownBy(() -> attendanceBook.validateIsAvailableAttendance(dateTime.toLocalDate()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이번달_시작일부터_오늘까지에_포함되는_날짜인_경우_예외가_발생하지_않는다() {
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceBook attendanceBook = new AttendanceBook();

        assertThatNoException().isThrownBy(() -> attendanceBook.validateIsAvailableAttendance(dateTime.toLocalDate()));
    }
}
