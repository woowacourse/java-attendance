public class AppConfig {
    public Controller controller() {
        return new Controller(fileReader());
    }

    private CsvReader fileReader() {
        return new CsvReader();
    }
}