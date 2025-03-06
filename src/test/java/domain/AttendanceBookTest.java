package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    void 닉네임과_출석일시를_입력하여_출석기록을_생성한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, dateTime);

        assertThat(attendanceBook.containsCrew(crew)).isTrue();
    }

    @Test
    void 닉네임_날짜_시간으로_출석하면_출석기록에_반영된다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDateTime newDateTime = LocalDateTime.of(2024, 12, 15, 10, 5);

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, dateTime);
        attendanceBook.attend(crew, newDateTime.toLocalDate(), newDateTime.toLocalTime());

        assertThat(attendanceBook.hasAttendanceDate(crew, newDateTime.toLocalDate())).isTrue();
    }

    @Test
    void 닉네임_날짜_시간으로_출석을_수정한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 15);
        final LocalTime newTime = LocalTime.of(10, 0);

        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew = new Crew(name);

        attendanceBook.initAttendance(crew, dateTime);
        assertThat(attendanceBook.findAttendanceDateByDate(crew, dateTime.toLocalDate()).getTime())
                .isEqualTo(dateTime.toLocalTime());

        attendanceBook.edit(crew, dateTime.toLocalDate(), newTime);
        assertThat(attendanceBook.findAttendanceDateByDate(crew, dateTime.toLocalDate()).getTime())
                .isEqualTo(newTime);
    }

    @Test
    void 크루의_출석_횟수를_계산한다() {
        final String name = "시소";
        final LocalDateTime initDate = LocalDateTime.of(2024, 12, 10, 10, 0);

        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 0), LocalDateTime.of(2024, 12, 12, 10, 0),
                LocalDateTime.of(2024, 12, 13, 10, 0), LocalDateTime.of(2024, 12, 14, 10, 0));

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, initDate);
        dateTimes.forEach(dateTime -> attendanceBook.attend(crew, dateTime.toLocalDate(), dateTime.toLocalTime()));

        assertThat(attendanceBook.getAttendanceCount(crew)).isEqualTo(1 + dateTimes.size());
    }


    @Test
    void 크루의_지각_횟수를_계산한다() {
        final String name = "시소";
        final LocalDateTime initDate = LocalDateTime.of(2024, 12, 10, 10, 0);

        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 10), LocalDateTime.of(2024, 12, 12, 10, 10),
                LocalDateTime.of(2024, 12, 13, 10, 10), LocalDateTime.of(2024, 12, 14, 10, 10));

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, initDate);
        dateTimes.forEach(dateTime -> attendanceBook.attend(crew, dateTime.toLocalDate(), dateTime.toLocalTime()));

        assertThat(attendanceBook.getTardyCount(crew)).isEqualTo(dateTimes.size());
    }

    @Test
    void 크루의_결석_횟수를_계산한다() {
        final String name = "시소";
        final LocalDateTime initDate = LocalDateTime.of(2024, 12, 10, 10, 0);

        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 15, 10), LocalDateTime.of(2024, 12, 12, 15, 10),
                LocalDateTime.of(2024, 12, 13, 15, 10), LocalDateTime.of(2024, 12, 14, 15, 10));

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, initDate);
        dateTimes.forEach(dateTime -> attendanceBook.attend(crew, dateTime.toLocalDate(), dateTime.toLocalTime()));

        assertThat(attendanceBook.getAbsenceCount(crew)).isEqualTo(10);
    }

    @Test
    void 이미_출석된_일자에_출석하려는_경우_예외가_발생한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, dateTime);

        assertThatThrownBy(() -> attendanceBook.validateBeforeAdd(crew, dateTime.toLocalDate()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석기록이_없을_때_출석을_시도하는_경우_예외는_발생하지_않는다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDate newDate = LocalDate.of(2024, 12, 13);

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, dateTime);

        assertThatNoException().isThrownBy(() -> attendanceBook.validateBeforeAdd(crew, newDate));
    }

    @Test
    void 출석되지_않은_일자를_수정하려는_경우_예외가_발생한다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDate newDate = LocalDate.of(2024, 12, 13);

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, dateTime);

        assertThatThrownBy(() -> attendanceBook.validateBeforeEdit(crew, newDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석된_일자를_수정하려는_경우_예외가_발생하지_않는다() {
        final String name = "시소";
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

        Crew crew = new Crew(name);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.initAttendance(crew, dateTime);

        assertThatNoException().isThrownBy(() -> attendanceBook.validateBeforeEdit(crew, dateTime.toLocalDate()));
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
