package controller;

import converter.StringConverter;
import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import domain.PenaltyPolicy;
import file.DataReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
        String rawNickname = inputView.readNickname();
        String rawCheckInTime = inputView.readCheckInTime();

        Crew crew = crews.findByNickname(rawNickname);

        Attendance attendance = converter.convertToAttendance(crew, rawCheckInTime, today);
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
        List<Crew> dangerCrews = crews.findDangerCrews(attendances, today);
        Map<Crew, Attendances> dangerAttendances = createAttendancesOfDangerCrews(dangerCrews, attendances, today);
        List<Crew> crewOrder = sortDangerCrews(dangerAttendances);
        outputView.printDangerCrews(dangerAttendances, crewOrder);
    }

    private Map<Crew, Attendances> createAttendancesOfDangerCrews(List<Crew> crews, Attendances attendances,
                                                                  LocalDate today) {
        Map<Crew, Attendances> dangerAttendances = new HashMap<>();
        for (Crew crew : crews) {
            dangerAttendances.put(crew, attendances.createMonthlyAttendances(crew, today));
        }
        return dangerAttendances;
    }

    private List<Crew> sortDangerCrews(Map<Crew, Attendances> dangerCrews) {
        List<Crew> crews = new ArrayList<>(dangerCrews.keySet());
        crews.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew o1, Crew o2) {
                Attendances a1 = dangerCrews.get(o1);
                Attendances a2 = dangerCrews.get(o2);
                if (compareWithPenalty(a1, a2) == 0 && compareWithAbsenceCount(a1, a2) == 0) {
                    return o1.compareTo(o2);
                }
                if (compareWithPenalty(a1, a2) == 0) {
                    return compareWithAbsenceCount(a1, a2);
                }
                return compareWithPenalty(a1, a2);
            }
        });

        return crews;
    }

    private int compareWithAbsenceCount(Attendances a1, Attendances a2) {
        int thisAbsenceCount = PenaltyPolicy.getConvertedCount(a1.countAttendanceType());
        int otherAbsenceCount = PenaltyPolicy.getConvertedCount(a2.countAttendanceType());
        return otherAbsenceCount - thisAbsenceCount;
    }

    private int compareWithPenalty(Attendances a1, Attendances a2) {
        PenaltyPolicy penalty1 = PenaltyPolicy.judgePenalty(a1.countAttendanceType());
        PenaltyPolicy penalty2 = PenaltyPolicy.judgePenalty(a2.countAttendanceType());
        return penalty1.compareWithPriority(penalty2);
    }
}
