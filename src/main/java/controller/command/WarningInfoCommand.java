package controller.command;

import domain.AttendanceBook;
import domain.attendance.Attendances;
import dto.WarningCrewInfoDto;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import view.OutputView;

public class WarningInfoCommand implements Consumer<AttendanceBook> {
    private final OutputView outputView;

    public WarningInfoCommand(OutputView outputView) {
        this.outputView = outputView;
    }

    @Override
    public void accept(AttendanceBook attendanceBook) {
        Map<String, Attendances> warningCrews = attendanceBook.findWarningCrews();
        List<WarningCrewInfoDto> warningCrewInfoDtos = createWarningCrewInfoDtos(warningCrews);
        outputView.printWarningCrews(warningCrewInfoDtos);
    }

    private List<WarningCrewInfoDto> createWarningCrewInfoDtos(Map<String, Attendances> sortedWarningCrews) {
        return sortedWarningCrews.entrySet()
                .stream()
                .map(warningCrew -> new WarningCrewInfoDto(
                        warningCrew.getKey(),
                        warningCrew.getValue().countAbsence(),
                        warningCrew.getValue().countTardy(),
                        warningCrew.getValue().countAllAbsence())).toList();
    }
}
