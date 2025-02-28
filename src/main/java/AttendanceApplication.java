import attendance.domain.FileReader;

public class AttendanceApplication {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        fileReader.readCSV();

    }
}
