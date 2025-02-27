import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class PenaltyCheckTest {

    //제적 위험자 조회 결과
    //- 빙티: 결석 3회, 지각 4회 (면담)
    //- 이든: 결석 2회, 지각 5회 (면담)
    //- 빙봉: 결석 1회, 지각 6회 (면담)
    //- 쿠키: 결석 2회, 지각 3회 (면담)
    //- 짱수: 결석 0회, 지각 6회 (경고)
    //- [] 전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
    //- [] 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력한다
    //    - [] 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다.
    //    - [] 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.

    @Test
    void 제적_위험자인_크루의_정보를_가져온다() throws IOException {
        PenaltyCheck penaltyCheck = new PenaltyCheck();

        AttendanceBook attendanceBook = new AttendanceBook();
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));

        final var penaltyCrews = penaltyCheck.getPenaltyHistory(attendanceBook);

    }
}