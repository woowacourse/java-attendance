import java.util.List;

public class Controller {
    private final CsvReader csvFileReader;

    public Controller(CsvReader csvReader) {

        this.csvFileReader = csvReader;
    }

    public void start() {
        String dataPath = "src/main/resources/attendances.csv";
        List<String> fileData = csvFileReader.readCsv(dataPath);
    }
}