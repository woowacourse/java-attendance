package controller;

import domain.*;
import util.Converter;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceController {
    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String option;
        crews.recordAllAbsence();
        do {
            outputView.printOptionMessage();
            option = inputView.getOption();

            if (option.equals("1")) {
                checkHoliday(LocalDate.now());

                String nickname = inputView.getNickname();
                Crew crew = crews.findByNickname(nickname);

                if (crew.isAlreadyAttend(LocalDate.now())) {
                    System.out.println("이미 출석 완료되었습니다. 수정 기능을 이용해주세요.");
                    continue;
                }

                String attendanceTimeInput = inputView.getAttendanceTime();
                LocalTime attendanceTime = Converter.convertStringToLocalTime(attendanceTimeInput);
                Attendance attendance = new Attendance(new Day(LocalDate.now()), attendanceTime);
                crew.addAttendance(attendance);
                outputView.printAttendanceInformation(attendance.toDto());
            }


            if (option.equals("2")) {
                String editNickname = inputView.getEditNickname();
                Integer dayOfMonth = Converter.convertStringToInteger(inputView.getEditDayOfMonth());
                LocalTime attendanceTime = Converter.convertStringToLocalTime(inputView.getNewTime());

                Crew crew = crews.findByNickname(editNickname);
                Attendance attendance = crew.findByDate(dayOfMonth);
                AttendanceDto originalAttendanceDto = attendance.toDto();

                attendance.updateAttendanceTime(attendanceTime);
                AttendanceDto editedAttendanceDto = attendance.toDto();

                outputView.printUpdatedAttendanceHistory(originalAttendanceDto, editedAttendanceDto);
            }


            if (option.equals("3")) {
                String nickname = inputView.getNickname();
                Crew crew = crews.findByNickname(nickname);

                outputView.printCrewAttendanceHistoryMessage(nickname);
                outputView.printAttendanceHistoryWithCrew(crew);
            }


            if (option.equals("4")) {
                List<CrewDto> crewDtos = crews.createCrewDtos();
                List<CrewDto> penaltyCrewDtos = crewDtos.stream()
                        .filter(crewDto -> crewDto.getPenaltyStatus() != PenaltyStatus.NONE)
                        .collect(Collectors.toCollection(ArrayList::new));

                outputView.printPenaltyCrews(new CrewDtos(penaltyCrewDtos));
            }
        }
        while (!option.equals("Q"));
    }

    private void checkHoliday(LocalDate todayDate) {
        Day today = new Day(todayDate);
        int month = todayDate.getMonth().getValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeekName = DayOfWeek.getNameById(todayDate.getDayOfWeek().getValue());
        if (today.checkHoliday()) {
            throw new IllegalArgumentException("[ERROR] " + month + "월 " + dayOfMonth + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }
}
