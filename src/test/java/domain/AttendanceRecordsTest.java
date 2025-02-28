package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("출석 기록들에 대한 테스트")
public class AttendanceRecordsTest {

    private AttendanceRecords records;

    private AttendanceDateTime dateTime1;
    private AttendanceDateTime dateTime2;

    @BeforeEach
    public void setUp() {
        records = new AttendanceRecords();
        dateTime1 = AttendanceDateTime.parse("2025-02-27T10:00");
        dateTime2 = AttendanceDateTime.parse("2025-02-28T10:00");
    }

    @Test
    void 출석일시를_추가하면_출석기록에_추가된다() {
        records.add(dateTime1);
        assertThat(records.getRecords()).contains(dateTime1);
    }

    @Test
    void 추가하려는_출석일시가_이미_존재하면_예외가_발생한다() {
        records.add(dateTime1);

        AttendanceDateTime sameDate_as_DateTime1 = AttendanceDateTime.parse("2025-02-27T10:30");
        assertThatThrownBy(() -> records.add(sameDate_as_DateTime1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
    }

    @Test
    void 추가한_출석일시들을_모두_확인할_수_있다() {
        records.add(dateTime1);
        records.add(dateTime2);

        var records = this.records.getRecords();
        assertThat(records).containsExactly(dateTime1, dateTime2);
    }

    @Test
    void 날짜에_해당하는_출석일시가_존재할때_제거할_수_있다() {
        records.add(dateTime1);

        LocalDate dateToRemove = dateTime1.getDate();
        records.removeIfAttendedOnDate(dateToRemove);

        assertThat(records.getRecords()).doesNotContain(dateTime1);
    }

    @Test
    void 날짜에_해당하는_출석일시가_존재하지_않을때_제거하려하면_아무일도_일어나지_않는다() {
        int beforeSize = records.getRecords().size();

        LocalDate dateToRemove = dateTime1.getDate();
        records.removeIfAttendedOnDate(dateToRemove);

        int afterSize = records.getRecords().size();
        assertThat(afterSize).isEqualTo(beforeSize);
    }

    @Test
    void 두_날짜_사이_등교날짜에_대해_결석인_출석일시를_알_수_있다() {
        var fromMonday = LocalDate.of(2025, 2, 17);
        var toSunday = LocalDate.of(2025, 2, 23);
        records.add(AttendanceDateTime.of(2025, 2, 17, 13, 0));
        records.add(AttendanceDateTime.of(2025, 2, 18, 10, 10));
        records.add(AttendanceDateTime.of(2025, 2, 19, 10, 31));
        // [월] 출석, [화] 지각, [수] 늦어서 결석, [목,금] 출석하지 않음, [토,일] 등교일 아님

        List<AttendanceDateTime> dates = records.getAbsenceDatesBetween(fromMonday, toSunday);

        assertAll(
            () -> assertThat(dates).hasSize(5),
            () -> assertThat(dates).containsExactly(
                AttendanceDateTime.of(2025, 2, 17, 13, 0),
                AttendanceDateTime.of(2025, 2, 18, 10, 10),
                AttendanceDateTime.of(2025, 2, 19, 10, 31),
                AttendanceDateTime.ofAbsence(LocalDate.of(2025, 2, 20)),
                AttendanceDateTime.ofAbsence(LocalDate.of(2025, 2, 21))
            )
        );
    }
}
