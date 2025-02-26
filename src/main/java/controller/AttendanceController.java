package controller;

import exception.CrewNotExistException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Attendance;
import model.AttendanceBook;
import model.ExistingAttendances;
import model.AttendanceHistory;
import model.Crew;
import model.Crews;
import model.DateGenerator;
import model.December;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView;
    private final InputView inputView;

    public AttendanceController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        List<String> crewAttendanceData = readAttendanceFile();
        ExistingAttendances existingAttendances = ExistingAttendances.from(crewAttendanceData);
        Crews crews = Crews.from(crewAttendanceData);
        AttendanceBook attendanceBook = AttendanceBook.from(crews);
        attendanceBook.update(existingAttendances.getAttendances(), crews);

        String functionChoice = inputView.readFunctionChoice();
        if (functionChoice.equals("1")) {
            doRegisterService(attendanceBook, crews);
        }
        if (functionChoice.equals("2")) {
            doModifyService(attendanceBook, crews);
        }
    }

    //TODO : 도메인 없는 순수 리더
    private List<String> readAttendanceFile() {
        try {
            String path = "./src/main/resources/attendances.csv";
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("파일 입력 중 오류가 발생했습니다.");
        }
    }

    private void doRegisterService(AttendanceBook attendances, Crews crews) {
        try {
            String name = inputView.readCrewName();
            Crew crew = crews.findCrewByName(name)
                    .orElseThrow(CrewNotExistException::new);

            LocalDate date = DateGenerator.now();
            December.validateHoliday(date);

            LocalTime time = inputView.readAttendanceTime();
            AttendanceHistory crewAttendanceHistory = attendances.findByCrew(crew);
            Attendance newAttendance = crewAttendanceHistory.register(date, time);
            outputView.printAttendanceRegisterResult(newAttendance);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }

    private void doModifyService(AttendanceBook attendances, Crews crews) {
        try {
            String name = inputView.readCrewName();
            Crew crew = crews.findCrewByName(name)
                    .orElseThrow(CrewNotExistException::new);

            LocalDate date = DateGenerator.create(inputView.readModifyDate());
            LocalTime time = inputView.readModifyTime();

            AttendanceHistory crewAttendance = attendances.findByCrew(crew);
            Attendance oldAttendance = crewAttendance.findByDate(date);
            Attendance newAttendance = crewAttendance.modifyFrom(oldAttendance, time);

            outputView.printAttendanceModifyResult(oldAttendance, newAttendance);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }
}
