parser grammar SqlParser;

options {
  tokenVocab = SqlLexer;
}

query
  : SELECT IDENTIFIER FROM IDENTIFIER EOF
  ;
