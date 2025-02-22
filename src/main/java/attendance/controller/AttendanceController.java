package attendance.controller;

import attendance.dto.AttendanceInfoDto;
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
import java.util.Map;

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

        while (attendanceOption != AttendanceOption.QUIT) {
            attendanceOption = inputView.readOption(now);
            chooseOption(attendanceOption, now);
        }
    }

    private void chooseOption(AttendanceOption attendanceOption, LocalDate today) {
        if (attendanceOption == AttendanceOption.MARK) {
            optionOne(today);
        }

        if (attendanceOption == AttendanceOption.EDIT) {
            optionTwo();
        }

        if (attendanceOption == AttendanceOption.CHECK) {
            optionThree(today);
        }

        if (attendanceOption == AttendanceOption.WARNING) {
            optionFour(today);
        }
    }

    private void optionOne(LocalDate today) {
        HolidayChecker.validWeekDay(today);

        String nickname = inputView.readNickname();
        service.findName(nickname);

        LocalTime localTime = inputView.readTime();
        service.insertAttendance(nickname, today, localTime);

        String attendanceStatus = service.getAttendanceStatus(today, localTime);
        outputView.addResult(new AttendanceInfoDto(today, localTime, attendanceStatus));
    }

    private void optionTwo() {
        String nickName = inputView.readEditNickName();
        service.findName(nickName);
        LocalDate date = inputView.readEditDate();
        LocalTime editTime = inputView.readEditTime();

        EditResponseDto responseDto = service.edit(nickName, date, editTime);

        outputView.editResult(responseDto);
    }

    private void optionThree(LocalDate today) {
        String nickname = inputView.readNickname();
        service.findName(nickname);

        Map<LocalDate, AttendanceInfoDto> dtoMap = service.getAttendanceInfos(nickname, today);
        List<Integer> counts = service.getAttendanceCounts(nickname, today);
        String penalty = service.getAttendancePenalty(counts);

        outputView.attendanceResult(nickname, dtoMap, counts, penalty, today);
    }

    private void optionFour(LocalDate today) {
        List<PenaltyCrewDto> crewsInfos = service.getCrewsName(today);
        outputView.penaltyCrews(crewsInfos);
    }
}
