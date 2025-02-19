import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Controller {
    private final CsvReader csvFileReader;

    public Controller(CsvReader csvReader) {

        this.csvFileReader = csvReader;
    }

    public void start() {
        String dataPath = "src/main/resources/attendances.csv";
        List<String> fileData = csvFileReader.readCsv(dataPath);

        List<String> removedData = Parser.parse(fileData);
        List<List<String>> seperatedData = Parser.parseName(removedData);

        AttendanceBook attendanceBook = new AttendanceBook();

        for (List<String> data : seperatedData){
            String name = data.getFirst();
            Map<LocalDate, LocalTime> dateAndTime = Parser.parseDate(data.getLast());
            attendanceBook.initialize(name, dateAndTime);
        }
    }


}