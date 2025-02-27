package controller;

import exception.CrewNotExistException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Attendance;
import model.AttendanceBook;
import model.AttendanceStatistic;
import model.AttendanceStatistics;
import model.AttendanceStatus;
import model.ExistingAttendances;
import model.AttendanceHistory;
import model.Crew;
import model.Crews;
import model.DateGenerator;
import model.December;
import model.PenaltyStatus;
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

        boolean continueService = true;
        while (continueService) {
            continueService = chooseAndDoService(attendanceBook, crews);
        }
    }

    private boolean chooseAndDoService(AttendanceBook attendanceBook, Crews crews) {
        String functionChoice = inputView.readFunctionChoice();
        if (functionChoice.equals("1")) {
            doRegisterService(attendanceBook, crews);
            return true;
        }
        if (functionChoice.equals("2")) {
            doModifyService(attendanceBook, crews);
            return true;
        }
        if (functionChoice.equals("3")) {
            doHistoryService(attendanceBook, crews);
            return true;
        }
        if (functionChoice.equals("4")) {
            doPenaltyService(attendanceBook, crews);
            return true;
        }
        //TODO: 메뉴 선택 enum화
        return false;
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

    private void doHistoryService(AttendanceBook attendanceBook, Crews crews) {
        try {
            String name = inputView.readCrewName();
            Crew crew = crews.findCrewByName(name)
                    .orElseThrow(CrewNotExistException::new);

            LocalDate now = DateGenerator.now();
            AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
            AttendanceStatistic attendanceStatistic = new AttendanceStatistic(attendanceHistory);

            List<Attendance> attendanceHistories = attendanceHistory.sliceByDateUntilBefore(now);
            Map<AttendanceStatus, Integer> attendanceStatusHistory = attendanceStatistic.calculateStatusCountUntilBefore(now);
            PenaltyStatus penaltyStatus = attendanceStatistic.calculatePenaltyUntilBefore(now);

            outputView.printAttendanceHistories(attendanceHistories, name);
            outputView.printAttendanceStatusHistory(attendanceStatusHistory);
            outputView.printPenaltyStatus(penaltyStatus);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }

    private void doPenaltyService(AttendanceBook attendanceBook, Crews crews) {
        LocalDate now = DateGenerator.now(); //TODO : 컨트롤러 생성자
        AttendanceStatistics attendanceStatistics = attendanceBook.findAllStatistics();
        List<AttendanceStatistic> penaltyTargets = attendanceStatistics.findPenaltyTargets(now);
        Map<Crew, AttendanceStatistic> penaltyTargetCrews = new HashMap<>();
        penaltyTargets.stream()
                .forEach(statistic -> {
                    Crew crew = attendanceBook.findCrewByAttendance(statistic.getAttendanceHistory());
                    penaltyTargetCrews.put(crew, statistic);
                });

        outputView.printPenaltyResult(penaltyTargetCrews);
    }
}
