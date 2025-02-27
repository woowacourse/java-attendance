package controller;

import model.exception.CrewNotExistException;
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
import model.AttendanceStatistic;
import model.AttendanceStatistics;
import model.AttendanceStatus;
import model.ExistingAttendances;
import model.AttendanceHistory;
import model.Crew;
import model.Crews;
import model.December;
import model.PenaltyStatus;
import model.exception.SystemException;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView;
    private final InputView inputView;
    private final LocalDate now;

    public AttendanceController(OutputView outputView, InputView inputView, LocalDate now) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.now = now;
    }

    public void start() {
        try {
            List<String> crewAttendanceData = readAttendanceFile();
            ExistingAttendances existingAttendances = ExistingAttendances.from(crewAttendanceData);
            Crews crews = Crews.from(crewAttendanceData);
            AttendanceBook attendanceBook = AttendanceBook.from(crews);
            attendanceBook.update(existingAttendances.getAttendances(), crews);

            boolean continueService = true;
            while (continueService) {
                continueService = chooseAndDoService(attendanceBook, crews);
            }
        } catch (SystemException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }

    private boolean chooseAndDoService(AttendanceBook attendanceBook, Crews crews) {
        try {
            Service serviceChoice = Service.findByValue(inputView.readFunctionChoice(now));
            if (serviceChoice == Service.REGISTER) {
                doRegisterService(attendanceBook, crews);
                return true;
            }
            if (serviceChoice == Service.MODIFY) {
                doModifyService(attendanceBook, crews);
                return true;
            }
            if (serviceChoice == Service.HISTORY) {
                doHistoryService(attendanceBook, crews);
                return true;
            }
            if (serviceChoice == Service.PENALTY) {
                doPenaltyService(attendanceBook);
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            return true;
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
            throw new SystemException();
        }
    }

    private void doRegisterService(AttendanceBook attendances, Crews crews) {
        String name = inputView.readCrewName();
        Crew crew = crews.findCrewByName(name)
                .orElseThrow(CrewNotExistException::new);

        December.validateHoliday(now);

        LocalTime time = inputView.readAttendanceTime();
        AttendanceHistory crewAttendanceHistory = attendances.findByCrew(crew);
        Attendance newAttendance = crewAttendanceHistory.register(now, time);
        outputView.printAttendanceRegisterResult(newAttendance);
    }

    private void doModifyService(AttendanceBook attendances, Crews crews) {
        String name = inputView.readCrewName();
        Crew crew = crews.findCrewByName(name)
                .orElseThrow(CrewNotExistException::new);

        LocalDate date = December.createDecemberDateWith(inputView.readModifyDate());
        LocalTime time = inputView.readModifyTime();

        AttendanceHistory crewAttendance = attendances.findByCrew(crew);
        Attendance oldAttendance = crewAttendance.findByDate(date);
        Attendance newAttendance = crewAttendance.modifyFrom(oldAttendance, time);

        outputView.printAttendanceModifyResult(oldAttendance, newAttendance);
    }

    private void doHistoryService(AttendanceBook attendanceBook, Crews crews) {
        String name = inputView.readCrewName();
        Crew crew = crews.findCrewByName(name)
                .orElseThrow(CrewNotExistException::new);

        AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);

        List<Attendance> attendanceHistories = attendanceHistory.sliceByDateUntilBefore(now);
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendanceHistories);
        Map<AttendanceStatus, Integer> attendanceStatusHistory = attendanceStatistic.getAttendanceCount();
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        outputView.printAttendanceHistories(attendanceHistories, name);
        outputView.printAttendanceStatusHistory(attendanceStatusHistory);
        outputView.printPenaltyStatus(penaltyStatus);
    }

    private void doPenaltyService(AttendanceBook attendanceBook) {
        Map<Crew, List<Attendance>> attendanceHistories = attendanceBook.findAllStatisticsUntilBefore(now);
        AttendanceStatistics attendanceStatistics = AttendanceStatistics.from(attendanceHistories);
        Map<Crew, AttendanceStatistic> penaltyTargets = attendanceStatistics.findPenaltyTargets();
        outputView.printPenaltyResult(penaltyTargets);
    }

}
