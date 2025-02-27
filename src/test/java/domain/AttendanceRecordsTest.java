package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
