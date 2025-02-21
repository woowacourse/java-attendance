package view;

import domain.AllCrew;

public class InputValidator {
    public static void validateMenuInput(String menu) {
        if(!menu.matches("[1234Qq]")) {
            throw new IllegalArgumentException("존재하는 메뉴 번호를 입력해주세요.");
        }
    }

    public static void validateName(String name, AllCrew allCrew) {
        if(!allCrew.containsCrewName(name)) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
    }
}
