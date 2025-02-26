package attendance.util;

import attendance.view.OutputView;
import org.junit.jupiter.api.Test;

class AttendancesFileReaderTest {

    @Test
    void read() {
        String input = AttendancesFileReader.read();
        System.out.println(input);
    }

    @Test
    void outputViewTest() {
        OutputView outputView = new OutputView();
    }
}