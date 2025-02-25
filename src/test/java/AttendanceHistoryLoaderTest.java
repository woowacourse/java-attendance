import domain.Attendance;
import domain.AttendanceHistoryLoader;
import domain.Crew;
import domain.Crews;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceHistoryLoaderTest {

    public Path testFilePath = Paths.get("testAttendance.csv");
    public File file;

    public AttendanceHistoryLoader loader = new AttendanceHistoryLoader();

    @BeforeEach
    public void createTestCsvFile() throws IOException {
        if (!Files.exists(testFilePath)) {
            Files.createFile(testFilePath);
        }

        file = new File("testAttendance.csv");
    }

    @AfterEach
    public void removeTestCsvFile() throws IOException {
        if (Files.exists(testFilePath)) {
            Files.delete(testFilePath);
        }
    }

    @Test
    void 파일에서_불러온_닉네임을_확인한다() throws IOException {
        writeTestData();

        Crews crews = loader.loadCrews(new FileReader(file));
        Crew crew1 = crews.findByNickname("저스틴");
        Crew crew2 = crews.findByNickname("브라운");

        assertThat(crew1.getNickName()).isEqualTo("저스틴");
        assertThat(crew2.getNickName()).isEqualTo("브라운");
    }

    @Test
    void 파일에서_불러온_출석날짜를_확인한다() throws IOException {
        writeTestData();

        Crews crews = loader.loadCrews(new FileReader(file));
        Crew crew = crews.findByNickname("브라운");
        Attendance attendance = crew.findByDate(12);
        attendance.isEqualTo(LocalDate.of(2024, 12, 12));
        LocalTime attendanceTime = attendance.getAttendanceTime();

        assertThat(attendance.isEqualTo(LocalDate.of(2024, 12, 12))).isEqualTo(true);
        assertThat(attendanceTime).isEqualTo(LocalTime.of(10, 5));
    }

    private void writeTestData() throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write("nickname,datetime".getBytes());
        fos.write("\n저스틴,2024-12-13 10:08".getBytes());
        fos.write("\n브라운,2024-12-12 10:05".getBytes());
        fos.close();
    }

}
