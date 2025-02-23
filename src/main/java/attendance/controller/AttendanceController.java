package attendance.controller;

import attendance.domain.AttendanceTimeStatus;
import attendance.domain.CrewAttendanceRepository;
import attendance.util.FileLoader;
import attendance.view.DataFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceController {
    private static final Map<String, Runnable> operations = new HashMap<>();

    public void run() {
        CrewAttendanceRepository crewAttendanceRepository = initData();
        initOperations(crewAttendanceRepository);
        String option;
        while (!(option = getInputOption()).equals("Q")) {
            operations.get(option).run();
        }
    }

    private CrewAttendanceRepository initData() {
        return new CrewAttendanceRepository(FileLoader.loadAll(DataFileReader.read()));
    }

    private void initOperations(CrewAttendanceRepository crewAttendanceRepository) {
        operations.put("1", () -> registerAttendance(crewAttendanceRepository));
        operations.put("2", () -> modifyAttendance(crewAttendanceRepository));
        operations.put("3", () -> queryAttendance(crewAttendanceRepository));
        operations.put("4", () -> queryWarningCrews(crewAttendanceRepository));
    }

    private String getInputOption() {
        OutputView.printOptions();
        return InputView.readOption();
    }

    private void registerAttendance(CrewAttendanceRepository crewAttendanceRepository) {
        String name = InputView.readNickName();
        final LocalTime localTime = InputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), localTime);
        crewAttendanceRepository.add(name, localDateTime);
        OutputView.printAddedAttendance(localDateTime);
    }

    private void modifyAttendance(CrewAttendanceRepository crewAttendanceRepository) {
        String name = InputView.readModifyNickName();
        final int day = InputView.readModifyDay();
        LocalTime newTime = InputView.readModifyTime();
        LocalDateTime newLocalDateTime = LocalDateTime.of(LocalDate.of(2024, 12, day), newTime);
        AttendanceTimeStatus prevAttendanceTimeStatus = crewAttendanceRepository.update(name, newLocalDateTime);
        OutputView.printModifiedAttendance(prevAttendanceTimeStatus, newLocalDateTime);
    }

    private void queryAttendance(CrewAttendanceRepository crewAttendanceRepository) {
        String name = InputView.readNickName();
        OutputView.printQueryAttendance(name, crewAttendanceRepository);
    }

    private void queryWarningCrews(CrewAttendanceRepository crewAttendanceRepository) {
        OutputView.printWarningCrews(crewAttendanceRepository);
    }
}
