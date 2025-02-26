package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceRecord;
import attendance.domain.Crew;
import attendance.loader.AttendanceAssembler;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceAssembler assembler;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceAssembler assembler) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.assembler = assembler;
    }

    public void run() {
        AttendanceBook attendanceBook = assembler.assembleDatas();

        MainOption option = MainOption.from(inputView.readOption());

        if (option == MainOption.CHECK_ATTENDANCE) {
            Crew crew = new Crew(inputView.readName());
            LocalTime entryTime = inputView.readEntryTime();

            AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(LocalDate.of(2024, 12, 16), entryTime));
            attendanceBook.add(crew.getName(),
                    record);

            outputView.displayAttendanceResult(record);
        }

    }
}
