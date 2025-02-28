import domain.OperationTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CurrentTest {

    private static List<LocalDate> provideOperationDate(int day) {
        return IntStream.range(1, day)
                .mapToObj(date -> LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), date))
                .filter(OperationTime::isOperationDate)
                .toList();
    }

    @Test
    @DisplayName("오늘에 해당되는 월초부터 당일 직전까지의 운영 대상 날짜 데이터를 받아오는 기능")
    void getEducationDateUntilCurrent() {
        //when
        List<LocalDate> actual = Current.getEducationDateUntilCurrent();
        List<LocalDate> expected = provideOperationDate(Current.TODAY.getDay());

        //then
        Assertions.assertThat(actual).containsExactlyElementsOf(expected);
    }
}
