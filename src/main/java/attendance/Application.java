package attendance;

public class Application {
    public static void main(String[] args) {
        try (var dependencyConfig = new DependencyConfig()) {
            var controller = dependencyConfig.getController();
            controller.run();
        }
    }
}
