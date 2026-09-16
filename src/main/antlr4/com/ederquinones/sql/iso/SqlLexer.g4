lexer grammar SqlLexer;

import CommonSqlLexer;

options {
    caseInsensitive = true;
}

// Keywords
SELECT    : 'SELECT';
DISTINCT  : 'DISTINCT';
ALL       : 'ALL';
FROM      : 'FROM';
AS        : 'AS';

// Operator
PLUS_SIGN  : '+';
MINUS_SIGN : '-';
ASTERISK   : '*';
SOLIDUS    : '/';

// Separators / punctuation
COMMA       : ',';
PERIOD      : '.';
SEMICOLON   : ';';
LEFT_PAREN  : '(';
RIGHT_PAREN : ')';

// Literals
UNSIGNED_INTEGER
  : [0-9]+
  ;
