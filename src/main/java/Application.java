import controller.AttendanceController;
import domain.AttendanceBook;
import file.AttendanceBookFileReader;
import java.time.LocalDate;

public class Application {

    public static final LocalDate BEGIN_DATE_OF_EDUCATION = LocalDate.of(2025, 2, 11);

    public static void main(String[] args) {
        String fileName = "src/main/resources/attendances.csv";
        AttendanceBook book = AttendanceBookFileReader.read(fileName);

        LocalDate today = LocalDate.now();
        AttendanceController controller = new AttendanceController(book, BEGIN_DATE_OF_EDUCATION);
        controller.run(today);
    }
}
