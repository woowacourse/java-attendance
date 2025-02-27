package controller;

import io.writer.Writer;

public class AttendanceControllerExceptionWriteProxy implements AttendanceController {
    
    private final AttendanceController attendanceController;
    private final Writer writer;
    
    public AttendanceControllerExceptionWriteProxy(final AttendanceController attendanceController, final Writer writer) {
        this.attendanceController = attendanceController;
        this.writer = writer;
    }
    
    @Override
    public void run() {
        try {
            attendanceController.run();
        } catch (RuntimeException e) {
            writer.writeLine("[ERROR] " + e.getMessage());
            throw e;
        }
    }
}
