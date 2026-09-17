lexer grammar CommonSqlLexer;

IDENTIFIER
    : [a-z_] [a-z0-9_]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
