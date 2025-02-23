package domain;

@FunctionalInterface
public interface Command {
    void execute();
}
