package view.output;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void displayFunctionPrompt() {
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        String dayOfWeek = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.println(OutputPrompt.DISPLAY_FUNCTION_SELECTION_PROMPT.format(month, day, dayOfWeek));
    }

    public static void displaySpacing() {
        System.out.println();
    }
}