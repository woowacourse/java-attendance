package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.dto.AttendanceContentDTO;
import attendance.repository.AttendanceRepository;
import attendance.utils.AttendanceReader;
import attendance.utils.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {

        AttendanceContentDTO attendanceRecordContent = AttendanceReader.getAttendanceRecordContent(
                FileReader.parseToFile("src/main/resources/attendances.csv"));

        AttendanceRepository attendanceRepository = new AttendanceRepository(attendanceRecordContent.attendances());
        AttendanceBook attendanceBook = new AttendanceBook(attendanceRecordContent.names());

    }


}
