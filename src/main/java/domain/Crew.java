package domain;

public class Crew {
    private final String name;

    private Crew(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("[ERROR] Crew name cannot be null or empty");
        }
        if(name.length()<2 || name.length()>4){
            throw new IllegalArgumentException("[ERROR] Crew name must be between 2 and 4 characters");
        }

        this.name = name;
    }

    public static Crew of(String name) {
        return new Crew(name);
    }
}
