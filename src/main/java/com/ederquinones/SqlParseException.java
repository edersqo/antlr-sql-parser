package com.ederquinones;

/** Indicates that a SQL statement contains a lexical or syntax error. */
public final class SqlParseException extends IllegalArgumentException {

  /** Serialization identifier. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates an exception for an error reported by ANTLR.
   *
   * @param line one-based input line containing the error
   * @param col zero-based input column containing the error
   * @param msg error description
   */
  public SqlParseException(final int line, final int col, final String msg) {
    super("line %d:%d %s".formatted(line, col, msg));
  }
}
