package controller.store;

import domain.attendance.AttendanceBook;
import java.io.IOException;

public interface AttendanceStoreController {

    AttendanceBook store() throws IOException;
}
