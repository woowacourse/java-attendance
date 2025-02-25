package dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.SubjectType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DismissalCrewHistoryDtoTest {

    // 제적 위험자 조회 결과
    //- 빙티: 결석 3회, 지각 4회 (면담) // 결석 4회, 지각 1회
    //- 이든: 결석 2회, 지각 5회 (면담) // 결석 3회, 지각 2회
    //- 빙봉: 결석 1회, 지각 6회 (면담) // 결석 3회
    //- 쿠키: 결석 2회, 지각 3회 (면담) // 결석 3회
    //- 짱수: 결석 0회, 지각 6회 (경고) // 결석 2회
    @DisplayName("제적 위험자를 정렬한다")
    @Test
    void sortTest() {
        // Given
        DismissalCrewDto bingbong = new DismissalCrewDto("빙봉", 1, 6, SubjectType.INTERVIEW);
        DismissalCrewDto cookie = new DismissalCrewDto("쿠키", 2, 3, SubjectType.INTERVIEW);
        DismissalCrewDto bingtee = new DismissalCrewDto("빙티", 3, 4, SubjectType.INTERVIEW);
        DismissalCrewDto zzangsu = new DismissalCrewDto("짱수", 0, 6, SubjectType.WARNING);
        DismissalCrewDto eden = new DismissalCrewDto("이든", 2, 5, SubjectType.INTERVIEW);
        List<DismissalCrewDto> dtos = new ArrayList<>(List.of(bingbong, cookie, bingtee, zzangsu, eden));

        // When
        Collections.sort(dtos);

        // Then
        assertThat(dtos).containsExactly(bingtee, eden, bingbong, cookie, zzangsu);
    }

}
