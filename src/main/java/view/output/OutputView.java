package view.output;

import static view.output.OutputPrompt.DISPLAY_ATTENDANCE_RECORD_BY_CREW;
import static view.output.OutputPrompt.DISPLAY_ATTENDANCE_RECORD_COUNT_BY_CREW;
import static view.output.OutputPrompt.DISPLAY_ATTENDANCE_RECORD_PROMPT;
import static view.output.OutputPrompt.DISPLAY_CHECK_ATTENDANCE_RESULT;
import static view.output.OutputPrompt.DISPLAY_FUNCTION_SELECTION_PROMPT;
import static view.output.OutputPrompt.DISPLAY_MODIFY_ATTENDANCE_RESULT;
import static view.output.OutputPrompt.DISPLAY_PENALTY_BY_CREW;
import static view.output.OutputPrompt.DISPLAY_PENALTY_CREWS_PROMPT;
import static view.output.OutputPrompt.DISPLAY_PENALTY_CREWS_RESULT;

import dto.CheckAttendanceRecordResponse;
import dto.CheckAttendanceResponse;
import dto.ModifyAttendanceResponse;
import dto.PenaltyCrewResponse;
import dto.PenaltyResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public void displayFunctionPrompt() {
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        String dayOfWeek = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.println(DISPLAY_FUNCTION_SELECTION_PROMPT.format(month, day, dayOfWeek));
    }

    public void displayCheckAttendanceResult(CheckAttendanceResponse response) {
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        String dayOfWeek = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.println(DISPLAY_CHECK_ATTENDANCE_RESULT.format(month, day, dayOfWeek,
                response.time().format(formatter), response.attendanceStatus()));
    }

    public void displayModifyAttendanceResult(ModifyAttendanceResponse response) {
        System.out.println(DISPLAY_MODIFY_ATTENDANCE_RESULT.format(
                response.date().getMonthValue(), response.date().getDayOfMonth(),
                response.date().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                response.previousTime(), response.previousStatus(),
                response.modifiedTime(), response.modifiedStatus()
        ));
    }

    public void displayCheckAttendanceRecord(String name, List<CheckAttendanceRecordResponse> responses) {
        System.out.println(DISPLAY_ATTENDANCE_RECORD_PROMPT.format(name));
        for (CheckAttendanceRecordResponse response : responses) {
            System.out.println(DISPLAY_ATTENDANCE_RECORD_BY_CREW.format(
                    response.date().getMonthValue(), response.date().getDayOfMonth(),
                    response.date().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                    response.time().format(formatter), response.attendanceStatus()
            ));


        }
    }

    public void displayCheckAttendanceRecordResult(PenaltyResponse response) {
        System.out.println(DISPLAY_ATTENDANCE_RECORD_COUNT_BY_CREW.format(
                response.attendCount(), response.lateCount(), response.absentCount()
        ));
        if (!response.penalty().isEmpty()) {
            System.out.println(DISPLAY_PENALTY_BY_CREW.format(response.penalty()));
        }
    }

    public void displayCheckPenaltyCrewResult(List<PenaltyCrewResponse> responses) {
        System.out.println(DISPLAY_PENALTY_CREWS_PROMPT.getFormat());
        for (PenaltyCrewResponse response : responses) {
            System.out.println(DISPLAY_PENALTY_CREWS_RESULT.format(
                    response.name(), response.absentCount(), response.lateCount(), response.penalty()
            ));
        }
    }

    public static void displaySpacing() {
        System.out.println();
    }

    public static void displayErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        displaySpacing();
    }
}