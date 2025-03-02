package controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeatureTypeTest {

    @DisplayName("입력한 옵션에 따른 기능 옵션을 반환한다.")
    @Test
    void findFeatureByCommand() {
        //given
        String command = "1";

        //when
        FeatureType by = FeatureType.findBy(command);

        //then
        assertThat(by).isEqualTo(FeatureType.ATTENDANCE_CHECK);
    }

}
