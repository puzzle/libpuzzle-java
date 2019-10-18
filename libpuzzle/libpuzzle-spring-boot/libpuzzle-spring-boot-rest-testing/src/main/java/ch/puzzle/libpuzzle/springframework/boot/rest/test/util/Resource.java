package ch.puzzle.libpuzzle.springframework.boot.rest.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Resource {

    public static File file(String path) {
        var resource = Resource.class.getClassLoader().getResource(path);
        if (null == resource) {
            throw new IllegalStateException(String.format(
                    "Test resource %s does not exist.",
                    path
            ));
        }
        var file = new File(resource.getFile());
        if (!file.exists()) {
            throw new IllegalStateException(String.format(
                    "Test resource %s does not exist.",
                    file.getAbsolutePath()
            ));
        }
        return file;
    }

    public static String content(String path) {
        return content(path, StandardCharsets.UTF_8);
    }

    public static String content(String path, Charset charset) {
        try {
            return Files.readAllLines(file(path).toPath(), charset).stream().collect(Collectors.joining());
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format(
                            "Unable to load test resource content (path=%s, charset=%s): '%s'",
                            path,
                            charset,
                            e.getMessage()
                    ),
                    e
            );
        }
    }


}
