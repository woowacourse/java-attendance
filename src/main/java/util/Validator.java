package util;

import java.util.Objects;

public class Validator {
    
    public static void validateNull(Object o) {
        if (Objects.isNull(o)) {
            throw new IllegalArgumentException("null이 입력되었습니다");
        }
    }
}
