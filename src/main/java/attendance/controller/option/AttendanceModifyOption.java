package attendance.controller.option;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceTime;
import attendance.domain.Attendances;
import attendance.domain.CrewName;
import attendance.domain.CrewNames;
import attendance.dto.AttendanceModifyDto;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceModifyOption extends MenuOption {
    private static final int MODIFIABLE_YEAR_VALUE = 2025;
    private static final int MODIFIABLE_MONTH_VALUE = 2;

    public AttendanceModifyOption(InputView inputView, CrewNames crewNames, Attendances attendances,
                                  OutputView outputView) {
        super(inputView, outputView, crewNames, attendances);
    }

    @Override
    public void execute() {
        String crewNameInput = inputView.readModifyCrewName();
        CrewName crewName = crewNames.findCrewName(crewNameInput);
        Attendances crewAttendances = attendances.lookupCrewAttendance(crewName);

        String attendanceDateInput = inputView.readModifyAttendanceDate();
        AttendanceDate attendanceDate = new AttendanceDate(createTargetDate(attendanceDateInput));
        Attendance crewSpecificAttendance = crewAttendances.findCrewAttendanceByDate(crewName, attendanceDate);
        Attendance crewOriginalAttendance = crewSpecificAttendance.copyAttendanceInstance();
        String attendanceTimeInput = inputView.readModifyAttendanceTime();
        AttendanceTime attendanceTime = new AttendanceTime(attendanceTimeInput);
        crewSpecificAttendance.modifyAttendance(attendanceTime);

        outputView.printAttendanceModifyResult(
                AttendanceModifyDto.fromOriginalToNewAttendance(crewOriginalAttendance, crewSpecificAttendance));
    }

    private LocalDate createTargetDate(String attendanceDateInput) {
        return LocalDate.of(MODIFIABLE_YEAR_VALUE, MODIFIABLE_MONTH_VALUE, Integer.parseInt(attendanceDateInput));
    }
}
