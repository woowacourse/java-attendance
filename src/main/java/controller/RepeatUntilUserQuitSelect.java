package controller;

import reader.FileReadException;
import util.FormatUtil;

public class RepeatUntilUserQuitSelect {

    public static void repeat(ThrowingRunnable<FileReadException> supplier) {
        while (safelyExecute(supplier)) {
            String ignore = """
                    indent(인덴트, 들여쓰기) depth를 2를 넘지 않도록 구현한다. 1까지만 허용한다.
                    예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
                    """;
        }
    }

    private static boolean safelyExecute(ThrowingRunnable<FileReadException> supplier) {
        try {
            supplier.run();
        } catch (ProgramQuitException e) {
            System.out.println(FormatUtil.INFO_PREFIX + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(FormatUtil.ERROR_PREFIX + e.getMessage());
        } catch (Exception e) {
            System.out.println(FormatUtil.ERROR_PREFIX + "알 수 없는 오류가 발생했습니다.");
        }
        return true;
    }

    @FunctionalInterface
    public interface ThrowingRunnable<E extends Exception> {
        void run() throws E;
    }
}