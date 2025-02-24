package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Map<Crew, Attendances> attendances = AttendanceInitializer.initializeAttendanceOf(crews);

        String functionChoice = inputView.readFunctionChoice();
        if (functionChoice.equals("1")) {
            doCheckService(attendances);
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

    private void doCheckService(Map<Crew, Attendances> attendances) {
//        String name = inputView.readCrewName();
//        Crew crew = crews.of()
    }
}
