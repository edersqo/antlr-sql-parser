package com.ederquinones;

import com.ederquinones.sql.iso.SqlLexer;
import com.ederquinones.sql.iso.SqlParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/** Provides convenience methods for parsing ISO SQL statements. */
public final class IsoSql {

  private IsoSql() {
    throw new AssertionError("Utility class must not be instantiated");
  }

  /**
   * Parses an ISO SQL statement.
   *
   * @param sql SQL statement to parse
   * @return the root of the generated parse tree
   */
  public static SqlParser.QueryContext parse(final String sql) {
    CodePointCharStream input = CharStreams.fromString(sql);
    SqlLexer lexer = new SqlLexer(input);
    lexer.removeErrorListeners();
    lexer.addErrorListener(ErrorListener.INSTANCE);

    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SqlParser parser = new SqlParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(ErrorListener.INSTANCE);

    return parser.query();
  }

  /**
   * Parses an ISO SQL statement and returns its parse tree as text.
   *
   * @param sql SQL statement to parse
   * @return the parse tree in Lisp-style notation
   */
  public static String printTree(final String sql) {
    CodePointCharStream input = CharStreams.fromString(sql);
    SqlLexer lexer = new SqlLexer(input);
    lexer.removeErrorListeners();
    lexer.addErrorListener(ErrorListener.INSTANCE);

    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SqlParser parser = new SqlParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(ErrorListener.INSTANCE);

    SqlParser.QueryContext tree = parser.query();
    return tree.toStringTree(parser);
  }

  /** Converts ANTLR errors into exceptions exposed by the parser API. */
  private static final class ErrorListener extends BaseErrorListener {

    /** Shared stateless listener. */
    private static final ErrorListener INSTANCE = new ErrorListener();

    @Override
    public void syntaxError(
        final Recognizer<?, ?> recognizer,
        final Object offendingSymbol,
        final int line,
        final int charPositionInLine,
        final String msg,
        final RecognitionException cause) {
      throw new SqlParseException(line, charPositionInLine, msg);
    }
  }
}
