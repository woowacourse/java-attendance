package attendance.exception;

import java.io.IOException;

public class AttendanceFileException extends IOException {

    public AttendanceFileException(String message) {
        super("[ERROR] " + message);
    }

    public AttendanceFileException(String message, IOException e) {
        super("[ERROR] " + message + " : " + e);
    }
}
