package attendance.controller;

import attendance.dto.AttendanceInfoDto;
import attendance.dto.CrewAttendanceDto;
import attendance.dto.EditResponseDto;
import attendance.dto.PenaltyCrewDto;
import attendance.service.AttendanceService;
import attendance.service.DateGenerator;
import attendance.domain.HolidayChecker;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService service;
    private final DateGenerator dateGenerator;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceService service,
                                DateGenerator dateGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
        this.dateGenerator = dateGenerator;
    }

    public void run() {
        service.init();
        LocalDate now = dateGenerator.generate();
        AttendanceOption attendanceOption = null;

        do {
            attendanceOption = inputView.readOption(now);
            executeOption(attendanceOption, now);
        } while (attendanceOption != AttendanceOption.QUIT);
    }

    private void executeOption(AttendanceOption attendanceOption, LocalDate today) {
        if (attendanceOption == AttendanceOption.MARK) {
            remarkAttendance(today);
        }

        if (attendanceOption == AttendanceOption.EDIT) {
            editAttendance();
        }

        if (attendanceOption == AttendanceOption.CHECK) {
            checkAttendance(today);
        }

        if (attendanceOption == AttendanceOption.WARNING) {
            checkExpulsion(today);
        }
    }

    private void remarkAttendance(LocalDate today) {
        HolidayChecker.validWeekDay(today);

        String nickname = inputView.readNickname();
        service.checkNameExists(nickname);

        LocalTime localTime = inputView.readTime();
        service.insertAttendance(nickname, today, localTime);

        String attendanceStatus = service.getAttendanceStatus(today, localTime);
        outputView.addResult(new AttendanceInfoDto(today, localTime, attendanceStatus));
    }

    private void editAttendance() {
        String nickName = inputView.readEditNickName();
        service.checkNameExists(nickName);
        LocalDate date = inputView.readEditDate();
        LocalTime editTime = inputView.readEditTime();

        EditResponseDto responseDto = service.edit(nickName, date, editTime);

        outputView.editResult(responseDto);
    }

    private void checkAttendance(LocalDate today) {
        String nickname = inputView.readNickname();
        service.checkNameExists(nickname);

        CrewAttendanceDto crewAttendance = service.getCrewAttendance(nickname, today);
        outputView.attendanceResult(crewAttendance);
    }

    private void checkExpulsion(LocalDate today) {
        List<PenaltyCrewDto> crewsInfos = service.getCrewsName(today);
        outputView.penaltyCrews(crewsInfos);
    }
}
