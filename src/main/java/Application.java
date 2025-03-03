import controller.AttendanceController;
import domain.AttendanceBook;
import file.AttendanceBookFileReader;
import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        String fileName = "src/main/resources/attendances.csv";
        AttendanceBook book = AttendanceBookFileReader.read(fileName);

        AttendanceController controller = new AttendanceController(book);
        LocalDate today = LocalDate.now();
        controller.run(today);
    }
}
