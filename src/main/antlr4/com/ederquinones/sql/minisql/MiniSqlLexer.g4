lexer grammar MiniSqlLexer;

import CommonSqlLexer;

options {
    caseInsensitive = true;
}

SELECT : 'SELECT';
FROM   : 'FROM';
WHERE  : 'WHERE';

GTE : '>=';
LTE : '<=';
EQ  : '=';
GT  : '>';
LT  : '<';

STAR      : '*';
COMMA     : ',';
SEMICOLON : ';';

NUMBER
    : [0-9]+ ('.' [0-9]+)?
    ;

STRING
    : '\'' ~'\''* '\''
    ;
