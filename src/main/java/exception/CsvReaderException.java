package exception;

public enum CsvReaderException implements ExceptionMessage {

    INVALID_SITUATION("CSV 파일을 읽는 중 오류 발생: %s"),
    ;

    public final String message;

    CsvReaderException(String message) {
        this.message = message;
    }
    @Override
    public String getRawMessage() {
        return message;
    }
}