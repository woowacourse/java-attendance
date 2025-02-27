package domain;

import attendance.domain.CampusOperationTime;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CampusOperationTimeTest {

    @Test
    void 캠퍼스_운영_중() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9,59);

        //when & then
        Assertions.assertThat(CampusOperationTime.isOperation(localDateTime.getHour())).isTrue();
    }
}
