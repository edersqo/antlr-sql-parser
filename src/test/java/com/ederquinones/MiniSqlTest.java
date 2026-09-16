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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/** Tests the public MiniSQL parser facade. */
public class MiniSqlTest {

  private static final Path VALID = Paths.get("src/test/resources/minisql/valid");

  private static final Path INVALID = Paths.get("src/test/resources/minisql/invalid");

  /** Verifies that valid SQL produces a parse tree. */
  @Test
  public void parsesValidSql() {
    assertDoesNotThrow(() -> MiniSql.parse("SELECT name FROM users"));
  }

  /** Verifies that parser errors are exposed to callers. */
  @Test
  public void rejectsInvalidSyntax() {
    assertThrows(SqlParseException.class, () -> MiniSql.parse("SELECT FROM users"));
  }

  /** Verifies that lexer errors are exposed to callers. */
  @Test
  public void rejectsInvalidCharacters() {
    assertThrows(SqlParseException.class, () -> MiniSql.parse("SELECT name FROM users @"));
  }

  @TestFactory
  Stream<DynamicTest> validCorpus() {
    return sqlFiles(VALID)
        .map(
            path ->
                DynamicTest.dynamicTest(
                    path.getFileName().toString(),
                    () -> assertDoesNotThrow(() -> MiniSql.parse(Files.readString(path)))));
  }

  @TestFactory
  Stream<DynamicTest> invalidCorpus() {
    return sqlFiles(INVALID)
        .map(
            path ->
                DynamicTest.dynamicTest(
                    path.getFileName().toString(),
                    () ->
                        assertThrows(
                            SqlParseException.class, () -> MiniSql.parse(Files.readString(path)))));
  }

  private Stream<Path> sqlFiles(final Path directory) {
    try (var stream = Files.walk(directory)) {
      return stream
          .filter(Files::isRegularFile)
          .filter(file -> file.toString().endsWith(".sql"))
          .toList()
          .stream();
    } catch (IOException cause) {
      throw new UncheckedIOException("Cannot read SQL corpus: " + directory, cause);
    }
  }
}
