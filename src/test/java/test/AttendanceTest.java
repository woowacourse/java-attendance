package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import model.CrewGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @DisplayName("중복 없이 크루 이름을 읽어온다.")
    @Test
    void test1() {
        String crewInput = """
                쿠키,2024-12-13 10:08
                빙봉,2024-12-13 10:07
                이든,2024-12-13 10:07
                빙봉,2024-12-12 11:11
                빙티,2024-12-12 10:07
                이든,2024-12-12 10:06
                이든,2024-12-11 10:10
                """;
        List<String> crews = CrewGenerator.findCrewNames(crewInput);
        assertThat(crews).containsAll(Arrays.asList("쿠키", "빙봉", "빙티", "이든"));
    }

//    @DisplayName("입력한 닉네임에 맞는 크루 정보를 가져온다.")
//    @Test
//    void test1() {
//        String name = "빙티";
//        Crews crews = new C
//    }
}
