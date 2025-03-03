package controller;

import converter.StringConverter;
import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import file.DataReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StringConverter converter;

    public AttendanceController(InputView inputView, OutputView outputView, StringConverter converter) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.converter = converter;
    }

    public void run() {
        try {
            List<String> rawAttendances = new DataReader().readRawAttendances();
            Crews crews = converter.convertToCrews(rawAttendances);
            Attendances attendances = setUpAttendances(crews, rawAttendances);

            Command command;
            do {
                command = readCommand();
                process(command, crews, attendances);
            } while (!command.isQuit());
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e);
        }
    }

    private Attendances setUpAttendances(Crews crews, List<String> rawAttendances) {
        List<Attendance> attendances = new ArrayList<>();
        for (String rawAttendance : rawAttendances) {
            String[] attendanceInfos = converter.splitToNicknameAndTime(rawAttendance);
            Crew crew = crews.findByNickname(attendanceInfos[0]);
            attendances.add(converter.convertToAttendance(attendanceInfos[1], crew));
        }
        return new Attendances(attendances);
    }

    private Command readCommand() {
        LocalDate today = LocalDate.now();
        return Command.find(inputView.readCommand(today));
    }

    private void process(Command command, Crews crews, Attendances attendances) {
        if (command.isOne()) {
            checkIn(crews, attendances);
        }
        if (command.isTwo()) {
            modify(crews, attendances);
        }
        if (command.isThree()) {
            showAttendancesByCrew(crews, attendances);
        }
        if (command.isFour()) {
            showDangerCrews(crews, attendances);
        }
    }

    private void checkIn(Crews crews, Attendances attendances) {
        LocalDate today = LocalDate.now();
        Crew crew = crews.findByNickname(inputView.readNickname());
        Attendance attendance = converter.convertToAttendance(crew, inputView.readCheckInTime(), today);
        attendances.add(attendance);

        outputView.printCheckInResult(attendance);
    }

    private void modify(Crews crews, Attendances attendances) {
        LocalDate today = LocalDate.now();
        Crew crew = crews.findByNickname(inputView.readNickname());
        LocalDateTime newTime = converter.convertToLocalDateTime(inputView.readDate(), inputView.readTime(), today);

        Attendance oldAttendance = attendances.findByCrewAndDate(crew, newTime.toLocalDate());
        attendances.modifyAttendanceTime(crew, newTime);
        Attendance newAttendance = attendances.findByCrewAndDate(crew, newTime.toLocalDate());

        outputView.printModifiedResult(oldAttendance, newAttendance);
    }

    private void showAttendancesByCrew(Crews crews, Attendances attendances) {
        LocalDate today = LocalDate.now();
        String rawNickname = inputView.readNickname();
        Crew crew = crews.findByNickname(rawNickname);
        Attendances filteredAttendances = attendances.createMonthlyAttendances(crew, today);

        outputView.printAttendanceRecord(crew, filteredAttendances, today);
    }

    private void showDangerCrews(Crews crews, Attendances attendances) {
        LocalDate today = LocalDate.now();
        Crews dangerCrews = crews.findDangerCrews(attendances, today);
        Map<Crew, Attendances> dangerAttendances = dangerCrews.createAttendancesOfDangerCrews(attendances, today);
        List<Crew> crewOrder = dangerCrews.sortDangerCrews(attendances, today);

        outputView.printDangerCrews(dangerAttendances, crewOrder);
    }
}
