package domain;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

public class StringParser {

    public static List<Crew> parse(List<String> inputCrews) {
        List<Crew> crews = new ArrayList<>();

        return inputCrews.stream()
                .map(inputCrew -> {
                    String[] s = inputCrew.split(",");
                    return new Crew(s[0], s[1]);
                }).collect(toList());
    }
}
