package controller;

import domain.*;
import domain.constant.StandardDate;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceController {
    private static final String EXIT_OPTION = "Q";

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Runnable> options;

    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
        this.options = Map.of(
                Command.ATTEND, this::processAttendance,
                Command.UPDATE, this::processAttendanceUpdate,
                Command.HISTORY, this::processAttendanceHistory,
                Command.PENALTY, this::processPenaltyCheck
        );
    }

    public void run() {
        crews.recordAllAbsence(StandardDate.TODAY);

        String inputOption = "";

        while (!inputOption.equals(EXIT_OPTION)) {
            outputView.printOptionMessage(StandardDate.TODAY.getDate());
            inputOption = inputView.getOption();

            selectCommandAndRun(inputOption);
        }
    }

    public void selectCommandAndRun(String inputOption) {
        Command command = Command.check(inputOption);
        if (options.containsKey(command)) {
            options.get(command).run();
        }
    }

    public void processAttendance() {
        StandardDate.TODAY.validateNonHoliday();
        Crew crew = crews.findByNickname(inputView.getNickname());

        if (crew.isAlreadyAttend(StandardDate.TODAY.getDate())) {
            System.out.println("이미 출석 완료되었습니다. 수정 기능을 이용해주세요.");
            return;
        }

        registerAttendance(crew);
    }

    private void registerAttendance(Crew crew) {
        Attendance attendance = new Attendance(StandardDate.TODAY, inputView.getAttendanceTime());
        crew.addAttendance(attendance);
        outputView.printAttendanceInformation(attendance.toDto());
    }

    public void processAttendanceUpdate() {
        Crew crew = crews.findByNickname(inputView.getEditNickname());
        Attendance attendance = crew.findByDate(inputView.getEditDayOfMonth());
        AttendanceDto originalAttendanceDto = attendance.toDto();
        attendance.updateAttendanceTime(inputView.getNewTime());
        AttendanceDto editedAttendanceDto = attendance.toDto();

        outputView.printUpdatedAttendanceHistory(originalAttendanceDto, editedAttendanceDto);
    }

    public void processAttendanceHistory() {
        String nickname = inputView.getNickname();

        outputView.printCrewAttendanceHistoryMessage(nickname);
        outputView.printAttendanceHistoryWithCrew(crews.findByNickname(nickname));
    }

    public void processPenaltyCheck() {
        List<CrewDto> crewDtos = crews.createCrewDtos();
        List<CrewDto> penaltyCrewDtos = crewDtos.stream()
                .filter(crewDto -> crewDto.getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toCollection(ArrayList::new));

        outputView.printPenaltyCrews(penaltyCrewDtos);
    }
}

