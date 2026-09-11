parser grammar MiniSqlParser;

options {
  tokenVocab = MiniSqlLexer;
}

query
  : SELECT selectList FROM tableName whereClause? SEMICOLON? EOF
  ;

selectList
  : STAR
  | identifier (COMMA identifier)*
  ;

tableName
  : identifier
  ;

whereClause
  : WHERE condition
  ;

condition
  : identifier comparisonOperator value
  ;

comparisonOperator
  : EQ
  | GT
  | LT
  | GTE
  | LTE
  ;

value
  : NUMBER
  | STRING
  ;

identifier
  : IDENTIFIER
  ;