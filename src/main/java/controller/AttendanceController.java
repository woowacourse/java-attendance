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
import model.AttendanceInitializer;
import model.AttendanceBook;
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
        List<String> data = readAttendanceFile();
        List<String> crewNames = AttendanceInitializer.extractUniqueCrewData(data);
        Crews crews = Crews.of(crewNames);
        Map<Crew, AttendanceBook> attendances = registerAttendances(data, crews);

        String functionChoice = inputView.readFunctionChoice();
        if (functionChoice.equals("1")) {
            doRegisterService(attendances, crews);
        }
        if (functionChoice.equals("2")) {
            doModifyService(attendances, crews);
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

    private Map<Crew, AttendanceBook> registerAttendances(List<String> data, Crews crews) {
        Map<Crew, AttendanceBook> defaultAttendances = AttendanceInitializer.initializeAttendanceOf(crews);
        AttendanceInitializer.updateAttendances(crews, data, defaultAttendances);
        return defaultAttendances;
    }

    private void doRegisterService(Map<Crew, AttendanceBook> attendances, Crews crews) {
        try {
            String name = inputView.readCrewName();
            Crew crew = crews.findCrewByName(name)
                    .orElseThrow(CrewNotExistException::new);

            LocalDate date = DateGenerator.now();
            December.validateHoliday(date);

            LocalTime time = inputView.readAttendanceTime();
            AttendanceBook crewAttendanceBook = attendances.get(crew);
            Attendance newAttendance = crewAttendanceBook.register(date, time);
            outputView.printAttendanceRegisterResult(newAttendance);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }

    private void doModifyService(Map<Crew, AttendanceBook> attendances, Crews crews) {
        try {
            String name = inputView.readCrewName();
            Crew crew = crews.findCrewByName(name)
                    .orElseThrow(CrewNotExistException::new);

            LocalDate date = DateGenerator.create(inputView.readModifyDate());
            LocalTime time = inputView.readModifyTime();

            AttendanceBook crewAttendance = attendances.get(crew);
            Attendance oldAttendance = crewAttendance.findByDate(date);
            Attendance newAttendance = crewAttendance.modifyFrom(oldAttendance, time);

            outputView.printAttendanceModifyResult(oldAttendance, newAttendance);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }
}
