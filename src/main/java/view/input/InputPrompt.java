package view.input;

public enum InputPrompt {
    GUIDE_FUNCTION_INPUT("기능을 선택해 주세요.");

    private final String format;

    InputPrompt(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... arg) {
        return String.format(format, arg);
    }
}