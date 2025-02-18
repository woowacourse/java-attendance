import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


import org.junit.jupiter.api.Test;

public class AttendTest {


    @Test
    void time_정적생성() {
        //given
        Attend attend = Attend.of("09:59");

        //when
        var hour = attend.getHour();
        var minute = attend.getMinute();

        //then
        assertAll(
                () -> assertThat(hour).isEqualTo(9),
                () -> assertThat(minute).isEqualTo(59)
        );
    }

    @Test
    void day_time_정적생성() {
        //given
        String day = "3";
        String time = "09:59";

        //when
        var result = Attend.of(day, time);

        //then
        assertAll(
                () -> assertThat(result.getDay()).isEqualTo(3),
                () -> assertThat(result.getHour()).isEqualTo(9),
                () -> assertThat(result.getMinute()).isEqualTo(59)
        );
    }

}
