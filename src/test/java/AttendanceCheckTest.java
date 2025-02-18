import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCheckTest {

    @Test
    @DisplayName("크루 정보를 출석부에 저장한다.")
    void 크루_정보_저장() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        Crew crew = new Crew(new ArrayList<>());
        crew.attend(dateAndTime);
        List<LocalDateTime> records = crew.getRecords();

        assertThat(records.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("크루 정보를 출석부에 저장하고 출력한다.")
    void 크루_정보_출력() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        Crew crew = new Crew(new ArrayList<>());
        LocalDateTime attendedTime = crew.attend(dateAndTime);

        assertThat(dateAndTime).isEqualTo(attendedTime);
    }

    @Test
    @DisplayName("이미 출석한 경우 수정 기능을 안내한다.")
    void 수정_기능_안내() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        Crew crew = new Crew(new ArrayList<>());
        crew.attend(dateAndTime);

        assertThatThrownBy(() -> {
            crew.attend(dateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
