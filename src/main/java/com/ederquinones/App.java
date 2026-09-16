package com.ederquinones;

/** Main application entry point for testing the MiniSQL parser. */
public final class App {

  /** Prevents instantiation of this utility class. */
  private App() {
    throw new AssertionError("Utility class must not be instantiated");
  }

  /**
   * Application entry point.
   *
   * <p>Parses and prints a sample query using {@link MiniSql}.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {

    // SQL statement that will be parsed by the MiniSQL grammar.
    String sql =
        """
            SELECT name, price
            FROM products
            WHERE price > 100;
            """;

    // Parse the SQL string using the MiniSql facade.
    var tree = MiniSql.parse(sql);

    // Print the resulting ANTLR parse tree.
    System.out.println(tree.toStringTree());
  }
}
