package attendance.model;

public record Crew(String name){

    public Crew {
        validate(name);
    }

    private void validate(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("크루의 이름은 1자 이상 5자 이하여야 합니다.");
        }
        if(name.length() >= 5) {
            throw new IllegalArgumentException("크루의 이름은 1자 이상 5자 이하여야 합니다.");
        }
    }

}
