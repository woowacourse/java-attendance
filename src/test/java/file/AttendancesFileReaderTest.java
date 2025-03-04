package file;

import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.Crew;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("파일 리더기에 대한 테스트")
public class AttendancesFileReaderTest {

    @Test
    void 파일을_읽어_만든_출석부는_크루를_가지고있다() {
        AttendanceBook book = AttendanceBookFileReader.read("src/test/resources/test.csv");
        Optional<Crew> crew = book.findCrewByName("포포");
        assertThat(crew).isPresent();
    }

    @Test
    void 파일을_읽어_만든_출석부는_크루의_출석기록을_가지고있다() {
        AttendanceBook book = AttendanceBookFileReader.read("src/test/resources/test.csv");
        Crew crew = book.findCrewByName("포포").orElseThrow();

        List<AttendanceDateTime> records = book.findAllRecordsByCrew(crew);
        assertThat(records).hasSize(3);
    }
}
