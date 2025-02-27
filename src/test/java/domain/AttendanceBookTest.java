package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("출석부에 대한 테스트")
public class AttendanceBookTest {

    private AttendanceBook book;
    private final Crew crew = new Crew("크루");

    private AttendanceDateTime dateTime1 = AttendanceDateTime.parse("2025-02-24T10:00");

    @BeforeEach
    public void setUp() {
        book = new AttendanceBook();
    }

    @Test
    void 크루와_일시를_통해_해당_크루의_출석_기록에_출석일시를_추가할_수_있다() {
        book.attend(crew, dateTime1);

        AttendanceRecords records = book.getRecordsOfCrew(crew);
        assertThat(records.getRecords()).contains(dateTime1);
    }

    @Test
    void 출석하려는_날짜에_이미_출석했으면_예외가_발생한다() {
        book.attend(crew, dateTime1);

        assertThatThrownBy(() -> book.attend(crew, dateTime1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루가_출석한_적이_없으면_출석기록은_비어있다() {
        AttendanceRecords records = book.getRecordsOfCrew(crew);
        assertThat(records.getRecords()).isEmpty();
    }
}
