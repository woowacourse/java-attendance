package controller;

import java.util.Map;
import view.Menu;

public class AttendanceHandler {

    private final Map<Menu, MenuExecutor> executors;

    public AttendanceHandler(Map<Menu, MenuExecutor> executors) {
        this.executors = executors;
    }

    public void handle(Menu menu) {
        executors.get(menu).execute();
    }
}
