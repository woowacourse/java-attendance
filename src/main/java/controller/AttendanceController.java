package controller;

import domain.AttendanceBook;
import domain.AttendanceSystem;
import dto.AttendanceRecordDto;
import util.Parser;
import view.FileInput;
import view.Input;
import view.Output;

import java.time.LocalTime;
import java.util.List;

public class AttendanceController {
    private final FileInput fileInput;
    private final AttendanceSystem attendanceSystem;
    private final Input input;
    private final Output output;

    public AttendanceController(FileInput fileInput, Input input, Output output) {
        this.fileInput = fileInput;
        this.input = input;
        this.output = output;
        attendanceSystem = new AttendanceSystem();
    }

    public void start() {
        initCrew();
        startMenuLoop();
    }

    private void startMenuLoop() {
        boolean continueLoop = true;
        while(continueLoop) {
            continueLoop = handleMenuSelection();
        }
    }

    private boolean handleMenuSelection() {
        String menuSelection = input.getMenuInput(attendanceSystem.TODAY);
        if(menuSelection.equals("1")) {
            attend();
        }
        return true;
    }

    private void attend() {
        String name = input.getNameInput();
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        LocalTime time = Parser.stringToLocalTime(input.getTimeInput());
        attendanceBook.attendance(attendanceSystem.TODAY, time);

    }

    private void initCrew() {
        List<AttendanceRecordDto> attendanceRecords = fileInput.getFileInit();
        attendanceRecords.forEach(attendanceRecordDto -> {
            attendanceSystem.editAttendance(attendanceRecordDto.nickname(),
                    attendanceRecordDto.attendanceDateTime().toLocalDate(),
                    attendanceRecordDto.attendanceDateTime().toLocalTime());
        });
    }
}
