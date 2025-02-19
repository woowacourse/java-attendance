package attendance.controller;

import attendance.domain.AttendanceRepository;
import attendance.util.FileLoader;
import attendance.view.DataFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {

    public void run() throws IOException {
        AttendanceRepository attendanceRepository = initData();
        registerAttendance(attendanceRepository);
    }

    private AttendanceRepository initData(){
        return new AttendanceRepository(FileLoader.loadAll(DataFileReader.read()));
    }

    private void registerAttendance(AttendanceRepository attendanceRepository) throws IOException {
        String name = InputView.readNickName();
        final LocalTime localTime = InputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), localTime);
        attendanceRepository.add(name, localDateTime);
        OutputView.printAddedAttendance(localDateTime);
    }

}
