package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CampusTime {

    @DisplayName("출석 24시간 형식 확인")
    @Test
    void checkTimeFormat() {
        //given
        String inputTime = "09:59";

        //when
        CampusTime campusTime = new CampusTime(inputTime);

        //then
        campusTime.getTime().equals(inputTime);
    }
}
