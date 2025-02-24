package controller.command;

import domain.AttendanceDto;
import domain.Crew;
import domain.CrewDto;
import domain.Crews;
import domain.Nickname;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceRecordByCrewCommand implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        Nickname nickname = readNickname();
        Crew crew = crews.findByNickname(nickname);
        final List<AttendanceDto> attendanceDtos = crew.getAttendances()
                .stream()
                .map(AttendanceDto::from)
                .toList();

        OutputView.printCrewAttendances(CrewDto.from(crew), attendanceDtos);
    }

    private Nickname readNickname() {
        final String inputNickName = InputView.readNickName();
        return new Nickname(inputNickName);
    }
}

