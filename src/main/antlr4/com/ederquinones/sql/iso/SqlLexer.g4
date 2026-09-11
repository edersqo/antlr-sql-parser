lexer grammar SqlLexer;

import CommonSqlLexer;

options {
    caseInsensitive = true;
}

SELECT : 'SELECT';
FROM   : 'FROM';

IDENTIFIER
    : [a-z_] [a-z0-9_]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;