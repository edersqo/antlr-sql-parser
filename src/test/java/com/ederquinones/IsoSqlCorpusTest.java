package com.ederquinones;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

/** Verifies the ISO SQL grammar against valid and invalid statement corpora. */
public class IsoSqlCorpusTest {

  private static final Path VALID = Paths.get("src/test/resources/iso/valid");

  private static final Path INVALID = Paths.get("src/test/resources/iso/invalid");

  @TestFactory
  Stream<DynamicTest> validSql() throws IOException {
    return sqlFiles(VALID).map(this::validSqlTest);
  }

  @TestFactory
  Stream<DynamicTest> invalidSql() throws IOException {
    return sqlFiles(INVALID).map(this::invalidSqlTest);
  }

  private DynamicTest validSqlTest(Path path) {
    return DynamicTest.dynamicTest(
        path.getFileName().toString(),
        () ->
            assertDoesNotThrow(
                () -> parse(path),
                "Expected valid SQL in %s to parse without errors".formatted(path)));
  }

  private DynamicTest invalidSqlTest(Path path) {
    return DynamicTest.dynamicTest(
        path.getFileName().toString(),
        () ->
            assertThrows(
                IllegalArgumentException.class,
                () -> parse(path),
                "Expected %s to fail parsing".formatted(path.getFileName())));
  }

  private Stream<Path> sqlFiles(Path directory) {
    try (var stream = Files.walk(directory)) {
      return stream
          .filter(Files::isRegularFile)
          .filter(file -> file.toString().endsWith(".sql"))
          .toList()
          .stream();
    } catch (IOException e) {
      throw new UncheckedIOException("Cannot read SQL corpus from: " + directory, e);
    }
  }

  private void parse(Path path) throws IOException {
    IsoSql.parse(Files.readString(path));
  }
}
