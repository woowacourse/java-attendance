package controller;

import util.outputHandler.OutputHandler;

public class AttendanceControllerExceptionHandleProxy implements AttendanceController {
    
    private final OutputHandler outputHandler;
    private final AttendanceController target;
    
    public AttendanceControllerExceptionHandleProxy(OutputHandler outputHandler, AttendanceController target) {
        this.outputHandler = outputHandler;
        this.target = target;
    }
    
    @Override
    public void run() throws Exception {
        try {
            target.run();
        } catch (Exception e) {
            outputHandler.handle("[ERROR] " + e.getMessage());
        }
    }
}
