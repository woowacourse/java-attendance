package input;

import attendance.model.CrewDataLoader;
import attendance.model.Crews;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoadCrewData {

    @DisplayName("크루 데이터 csv 파일을 읽어온다.")
    @Test
    void test_read() {
        Crews crews = new Crews();
        CrewDataLoader crewDataLoader = new CrewDataLoader(crews);
        crewDataLoader.load("attendances.csv");
        Assertions.assertThat(crews.getCrews()).hasSize(7);
    }

}
