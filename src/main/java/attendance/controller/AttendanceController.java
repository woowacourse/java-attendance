package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.reader.AttendancesFileReader;
import attendance.service.CrewsService;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceController {
    private final CrewsService crewsService;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController() {
        this.crewsService = new CrewsService();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    private static LocalDateTime timeFormatter(LocalDate now, String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(inputDateTime, formatter);
        return LocalDateTime.of(now, time);
    }

    public void run() {
        LocalDate now = LocalDate.of(2024, 12, 17);
        Crews crews = crewsService.init(AttendancesFileReader.read(), now);

        printAttendanceByCrew(crews, now);
    }

    private void confirmAttendance(Crews crews, LocalDate now) {
        Crew crew = crews.findByName(inputView.inputNickname());
        crew.existInAttendances(now); //TODO: 수정으로 유도, 공휴일인지

        String attendTime = inputView.inputAttendTime();
        Attendance attendance = new Attendance(timeFormatter(now, attendTime));

        crew.addAttendance(attendance);
        outputView.printAttendanceResult(attendance);
    }

    private void printAttendanceByCrew(Crews crews, LocalDate now) {
        Crew crew = crews.findByName(inputView.inputNickname());
        outputView.printAttendanceByCrew(crew);
    }
}
