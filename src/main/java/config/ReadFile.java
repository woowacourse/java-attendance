package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class ReadFile<E, T> {
    private static final int HEADER = 1;

    public T loadFile(final Path filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toFile()));

            List<E> instances = bufferedReader.lines()
                    .skip(HEADER)
                    .map(this::createInstance)
                    .toList();

            return createInstances(new ArrayList<>(instances));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("[ERROR] 파일 위치가 올바르지 않습니다");
        }
    }

    protected abstract E createInstance(final String line);

    protected abstract T createInstances(final List<E> instances);
}
