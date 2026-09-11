package com.ederquinones;

import com.ederquinones.sql.minisql.MiniSqlLexer;
import com.ederquinones.sql.minisql.MiniSqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public final class MiniSql {

  private MiniSql() {}

  public static MiniSqlParser.QueryContext parse(final String sql) {
    CodePointCharStream input = CharStreams.fromString(sql);
    MiniSqlLexer lexer = new MiniSqlLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MiniSqlParser parser = new MiniSqlParser(tokens);

    return parser.query();
  }

  public static String printTree(final String sql) {
    CodePointCharStream input = CharStreams.fromString(sql);
    MiniSqlLexer lexer = new MiniSqlLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MiniSqlParser parser = new MiniSqlParser(tokens);
    MiniSqlParser.QueryContext tree = parser.query();

    return tree.toStringTree(parser);
  }
}
