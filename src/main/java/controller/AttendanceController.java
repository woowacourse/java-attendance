package controller;

import domain.CrewRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import repository.AttendanceRepository;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceRepository attendanceRepository;

    public AttendanceController(InputView inputView, OutputView outputView,
        AttendanceRepository attendanceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceRepository = attendanceRepository;
    }

    public void run() {

        attend();
        edit();

    }

    private void attend() {
        String name = inputView.readName();
        String time = inputView.readTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date + " " + time, formatter);

        attendanceRepository.attend(name,dateTime);
        outputView.printAttendanceRecord(dateTime);
    }

    private void edit(){
        String name = inputView.readEditName();
        String dayOfMonth = inputView.readEditDayOfMonth();
        String time = inputView.readTime();

        CrewRecord newRecord = attendanceRepository.edit(name, Integer.parseInt(dayOfMonth) , time);
        outputView.printEditResult();
    }
}
