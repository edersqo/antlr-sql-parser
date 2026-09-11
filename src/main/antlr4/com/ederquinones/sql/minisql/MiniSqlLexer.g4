lexer grammar MiniSqlLexer;

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

IDENTIFIER
    : [a-z_] [a-z0-9_]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;