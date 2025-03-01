package controller.command;

import domain.AttendanceDateTime;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import service.AttendanceService;
import util.DateUtils;
import view.InputView;
import view.OutputView;

public class AttendCommand implements ControllerCommand {

    private final AttendanceService service;

    public AttendCommand(AttendanceService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        LocalDate today = LocalDate.now();
        validateTodayIsWorkingDay(today);

        String nickName = InputView.readNickName();
        Crew crew = service.getCrewByNickName(nickName);

        String attendTime = InputView.readAttendTime();
        LocalTime time = LocalTime.parse(attendTime);

        AttendanceDateTime attended = service.attend(crew, today, time);
        OutputView.printAttendResult(attended);
    }

    private static void validateTodayIsWorkingDay(LocalDate today) {
        if (DateUtils.isDayOff(today)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일");
            String formattedDate = formatter.format(today);
            throw new IllegalArgumentException(formattedDate + "은 등교일이 아닙니다.");
        }
    }
}
