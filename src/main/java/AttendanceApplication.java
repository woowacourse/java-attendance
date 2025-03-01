import controller.AttendanceController;
import domain.AttendanceBook;
import domain.dto.AttendanceRecordDto;
import domain.factory.AttendanceBookFactory;
import java.io.IOException;
import java.util.List;
import util.AttendanceParser;
import util.CsvFileReader;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {

    public static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        try {
            AttendanceBook attendanceBook = loadAttendanceBook();
            AttendanceController controller = new AttendanceController(
                    attendanceBook,
                    inputView,
                    outputView
            );
            controller.run();
        } catch (IOException e) {
            System.out.println("출석 기록을 불러오는 데 실패하였습니다.");
        } catch (RuntimeException e) {
            System.out.println("예기치 못한 에러가 발생하였습니다.");
        }
    }

    private static AttendanceBook loadAttendanceBook() throws IOException {
        List<String[]> rawRecords = CsvFileReader.readCsvFile(FILE_PATH);
        List<AttendanceRecordDto> records = AttendanceParser.parse(rawRecords);
        return AttendanceBookFactory.createAttendanceBook(records);
    }
}
