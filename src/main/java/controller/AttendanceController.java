package controller;

import exception.DuplicatedAttendanceRegistrationException;
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
import model.Attendances;
import model.Crew;
import model.Crews;
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
        Map<Crew, Attendances> attendances = registerAttendances(data, crews);

        String functionChoice = inputView.readFunctionChoice();
        if (functionChoice.equals("1")) {
            doRegisterService(attendances, crews);
        }
        if (functionChoice.equals("2")) {
            doModifyService(attendances, crews);
        }
    }

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

    private Map<Crew, Attendances> registerAttendances(List<String> data, Crews crews) {
        Map<Crew, Attendances> defaultAttendances = AttendanceInitializer.initializeAttendanceOf(crews);
        AttendanceInitializer.updateAttendances(crews, data, defaultAttendances);
        return defaultAttendances;
    }

    private void doRegisterService(Map<Crew, Attendances> attendances, Crews crews) {
        try {
            String name = inputView.readCrewName();
            Crew crew = crews.findCrewByName(name).orElseThrow(IllegalArgumentException::new);

            LocalDate date = LocalDate.of(2024, 12, 13); //TODO : 오늘
            LocalTime time = inputView.readAttendanceTime();
            Attendances crewAttendances = attendances.get(crew);
            Attendance newAttendance = crewAttendances.register(date, time);
            outputView.printAttendanceRegisterResult(newAttendance);
        } catch (DuplicatedAttendanceRegistrationException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }

    private void doModifyService(Map<Crew, Attendances> attendances, Crews crews) {
    }
}
