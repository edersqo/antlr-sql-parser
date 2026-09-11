lexer grammar CommonSqlLexer;

options {
    caseInsensitive = true;
}

IDENTIFIER : [a-zA-Z_] [a-zA-Z0-9_]*;
