package view;

import static view.OutputMessages.DISPLAY_ABSENT_COUNT;
import static view.OutputMessages.DISPLAY_ATTENDANCE_COUNT;
import static view.OutputMessages.DISPLAY_ATTENDANCE_RECORD_PROMPT;
import static view.OutputMessages.DISPLAY_ATTENDANCE_RECORD_RESULT;
import static view.OutputMessages.DISPLAY_CHECK_ATTENDANCE_RESULT;
import static view.OutputMessages.DISPLAY_HAS_PENALTY;
import static view.OutputMessages.DISPLAY_LATE_COUNT;
import static view.OutputMessages.DISPLAY_MODIFY_ATTENDANCE_RESULT;
import static view.OutputMessages.DISPLAY_PENALTY_CREW;
import static view.OutputMessages.DISPLAY_PENALTY_PROMPT;

import domain.PenaltyStatus;
import dto.AttendanceRecordResponse;
import dto.CrewPenaltyResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public static void displaySpacing() {
        System.out.println();
    }

    public void displayFunctionSelectionPrompt() {
        String koreanDayOfWeek = LocalDate.now().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.println(
                OutputMessages.DATE_PROMPT.format(LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(),
                        koreanDayOfWeek));
        System.out.println(OutputMessages.FIRST_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.SECOND_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.THIRD_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.FOURTH_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.QUIT_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.GUIDE_PROMPT.getFormat());
    }

    public void displayCheckAttendanceResult(AttendanceRecordResponse response) {
        int month = response.date().getMonthValue();
        int day = response.date().getDayOfMonth();
        String koreanDayOfWeek = response.date().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String formattedTime = response.time().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println(DISPLAY_CHECK_ATTENDANCE_RESULT.format(month, day, koreanDayOfWeek, formattedTime,
                response.attendanceStatus().getMessage()));
    }

    public void displayModifyAttendanceResult(ModifyAttendanceResponse response) {
        int month = response.date().getMonthValue();
        int day = response.date().getDayOfMonth();
        String koreanDayOfWeek = response.date().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedOriginalTime = response.originalTime().format(dateTimeFormatter);
        String formattedModifiedTime = response.modifiedTime().format(dateTimeFormatter);

        System.out.println(DISPLAY_MODIFY_ATTENDANCE_RESULT.format(month, day, koreanDayOfWeek, formattedOriginalTime,
                response.originalStatus(), formattedModifiedTime,
                response.modifiedStatus()));
    }

    public void displayAttendanceRecordByName(String name, List<AttendanceRecordResponse> attendanceRecords,
                                              TotalRecordsResponse totalRecords, String penalty) {
        System.out.printf(DISPLAY_ATTENDANCE_RECORD_PROMPT.format(name));
        displaySpacing();
        for (AttendanceRecordResponse attendanceRecord : attendanceRecords) {
            int month = attendanceRecord.date().getMonthValue();
            int day = attendanceRecord.date().getDayOfMonth();
            String koreanDayOfWeek = attendanceRecord.date().getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.KOREAN);
            String time = "--:--";
            if (attendanceRecord.time() != null) {
                time = attendanceRecord.time().format(DateTimeFormatter.ofPattern("HH:mm"));
            }
            System.out.println(DISPLAY_ATTENDANCE_RECORD_RESULT.format(month, day, koreanDayOfWeek, time,
                    attendanceRecord.attendanceStatus().getMessage()));
        }
        displaySpacing();
        System.out.println(DISPLAY_ATTENDANCE_COUNT.format(totalRecords.attendanceCount()));
        System.out.println(DISPLAY_LATE_COUNT.format(totalRecords.lateCount()));
        System.out.println(DISPLAY_ABSENT_COUNT.format(totalRecords.absentCount()));
        displaySpacing();
        if (!penalty.isBlank()) {
            System.out.println(DISPLAY_HAS_PENALTY.format(penalty));
        }
        displaySpacing();
    }

    public void displayPenaltyCrew(List<CrewPenaltyResponse> responses) {

        responses.sort(Comparator.comparing(CrewPenaltyResponse::penaltyStatus)
                .thenComparing(response -> response.absentCount() + response.lateCount() / 3)
                .reversed()
                .thenComparing(CrewPenaltyResponse::name)
        );

        System.out.println(DISPLAY_PENALTY_PROMPT.getFormat());
        for (CrewPenaltyResponse response : responses) {
            if (response.penaltyStatus() == PenaltyStatus.NONE) {
                continue;
            }
            System.out.println(
                    DISPLAY_PENALTY_CREW.format(response.name(), response.absentCount(), response.lateCount(),
                            response.penaltyStatus().getMessage()));
        }
    }

    public void displayErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        displaySpacing();
    }
}