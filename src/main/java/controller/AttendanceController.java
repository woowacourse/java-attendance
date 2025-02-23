package controller;

import domain.AttendanceStatus;
import domain.Crew;
import domain.Crews;
import global.util.Date;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static global.util.Date.TODAY;
import static global.util.Date.assembleDateAndTime;
import static global.util.Validator.validateIsFutureDate;
import static global.util.Validator.validateIsNotWorkingDay;

public class AttendanceController {
    InputView inputView;
    OutputView outputView;
    Crews crews;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        crews = initCrews();
        while (true) {
            String menu;
            try {
                menu = inputView.inputMenu();
                if (menu.equals("Q")) {
                    break;
                }
                selectMenu(menu);
            } catch (DateTimeParseException | IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }

        }
    }

    public Crews initCrews() {
        return inputView.getFile();
    }

    public void selectMenu(String menu) {
        if (menu.equals("1")) {
            validateIsNotWorkingDay(TODAY.toLocalDate());
            attendCrew();
            return;
        }
        if (menu.equals("2")) {
            editAttend();
            return;
        }
        if (menu.equals("3")) {
            checkCrewsRecord();
            return;
        }
        if (menu.equals("4")) {
            checkRiskCrews();
            return;
        }
        throw new IllegalArgumentException("메뉴는 1, 2, 3, 4, Q만 입력할 수 있습니다.");
    }

    private void checkRiskCrews() {
        outputView.printRiskCrews(crews.getCrewResponseWithRisk());
    }

    private void checkCrewsRecord() {
        String name = inputView.inputName();
        Crew crew = crews.findCrewByName(name);
        outputView.printCrewAttendanceRecord(crews.createCrewResponse(crew));
    }

    public void attendCrew() {
        String name = inputView.inputName();
        Crew crew = crews.findCrewByName(name);
        crew.validateAvailableAttendanceDate(TODAY.toLocalDate());
        LocalTime time = LocalTime.parse(inputView.inputAttendTime());
        crew.addAttendStatus(assembleDateAndTime(TODAY.toLocalDate(), time));
        outputView.printAttendDateAttendanceMessage(TODAY.toLocalDate(), crews.createCrewResponse(crew).attendanceBook());
    }

    private void editAttend() {
        String name = inputView.inputEditCrewName();
        Crew crew = crews.findCrewByName(name);
        LocalDate date = Date.getDateByInputDay(Integer.parseInt(inputView.inputEditDay()));
        validateIsFutureDate(date);
        LocalTime beforeTime = crew.getAttendanceTime(date);
        AttendanceStatus beforAttendanceStatus = crew.getAttendanceStatusByDate(Date.assembleDateAndTime(date, beforeTime));
        LocalTime time = LocalTime.parse(inputView.inputEditTime());
        AttendanceStatus afterAttendanceStatus = crew.editAttendStatus(assembleDateAndTime(date, time));
        LocalTime afterTime = crew.getAttendanceTime(date);
        outputView.printAttendEditMessage(date, beforAttendanceStatus, beforeTime, afterAttendanceStatus, afterTime);
    }
}
