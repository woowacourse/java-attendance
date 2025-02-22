package model;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecordTest {

    Record record = new Record(LocalDateTime.of(2024,12,12,0,0));

    @Test
    @DisplayName("로컬 데이트 타임을 저장하는 메서드 테스트")
    void test1() {
        record.addTime(LocalDateTime.of(2024,12,1,0,0));
        Assertions.assertTrue(record.getTimeRecords().contains(LocalDateTime.of(2024,12,1,0,0)));
    }

    @Test
    @DisplayName("찾고자 하는 로컬 데이트 타임을 찾는 메서드 테스트")
    void test2() {
        org.assertj.core.api.Assertions.assertThat(record
                .compareDayIsSame(LocalDateTime.of(2024,12,12,0,0)))
                .isEqualTo(LocalDateTime.of(2024,12,12,0,0));
    }

    @Test
    @DisplayName("수정하고자 하는 로컬 데이트 타임을 삭제하는 메서드 테스트")
    void test3() {
        record.modifyRecord(LocalDateTime.of(2024,12,12,0,1));
        Assertions.assertFalse(record.getTimeRecords()
                .contains(LocalDateTime.of(2024,12,12,0,0)));

    }

    @Test
    @DisplayName("두 로컬 데이트 타임을 일자까지만 비교하여 같은 날인지 확인하는 메서드 테스트")
    void test4() {
        Assertions.assertTrue(
                record.compareDayIsSame(LocalDateTime.of(2024,12,12,0,0),
                        LocalDateTime.of(2024,12,12,13,59))
        );
    }

    @Test
    @DisplayName("파일에 있지 않은 일자들을 업데이트 해주는 메서드 구현")
    void test5() {
        record.updateNoInformationInFile(LocalDateTime.of(2024,12,26,0,0));
        Assertions.assertTrue(record.getTimeRecords().contains(LocalDateTime.of(2024,12,13,0,0)));
    }

}