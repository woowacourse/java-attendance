package model;

public class Crew {
    private final String name;

    public Crew(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Crew targetCrew)) {
            return false;
        }
        return name.equals(targetCrew.name);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(name); //TODO : 성능 개선?? 이거 왜 주석..?
//    }
}
