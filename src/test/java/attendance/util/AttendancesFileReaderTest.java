package attendance.util;

import org.junit.jupiter.api.Test;

class AttendancesFileReaderTest {

    @Test
    void read() {
        String input = AttendancesFileReader.read();
        System.out.println(input);
    }
}