parser grammar SqlParser;

options {
  tokenVocab = SqlLexer;
}

// ********************************************************************************
// Query expressions
// ********************************************************************************

// <query> ::=
//
query
  : querySpecification SEMICOLON? EOF
  ;

// <query specification> ::=
//    SELECT [ <set quantifier> ] <select list> <table expression>
querySpecification
  : SELECT setQuantifier? selectList tableExpression
  ;

// <set quantifier> ::=
//    DISTINCT
//  | ALL
setQuantifier
  : DISTINCT
  | ALL
  ;

// <select list> ::=
//    <asterisk>
//  | <select sublist>  [ { <comma>  <select sublist>  }... ]
selectList
  : ASTERISK
  | selectSublist (COMMA selectSublist)*
  ;

// <select sublist> ::=
//    <derived column>
//  | <qualified asterisk>
selectSublist
  : derivedColumn
  | qualifiedAsterisk
  ;

// <derived column> ::=
//   <value expression> [ <as clause> ]
derivedColumn
  : valueExpression asClause?
  ;

// <as clause> ::=
//   [ AS ] <column name>
asClause
  : AS? columnName
  ;

// Simplified <column name>.
// TODO(ISO): Implement the complete identifier/name grammar.
columnName
  : IDENTIFIER
  ;

// <qualified asterisk> ::=
//    <asterisked identifier chain>  <period>  <asterisk>
//  | <all fields reference>
// TODO(ISO): Add:
//   - allFieldsReference
qualifiedAsterisk
  : asteriskedIdentifierChain PERIOD ASTERISK
  ;

// <asterisked identifier chain> ::=
//  <asterisked identifier>  [ { <period>  <asterisked identifier>  }... ]
asteriskedIdentifierChain
  : asteriskedIdentifier (PERIOD asteriskedIdentifier)*
  ;

// <asterisked identifier> ::=
//  <identifier>
asteriskedIdentifier
  : IDENTIFIER
  ;

// ********************************************************************************
// Value expressions
// ********************************************************************************

// <value expression> ::=
//     <common value expression>
//   | <boolean value expression>
//   | <row value expression>
// TODO: Add booleanValueExpression and rowValueExpression.
valueExpression
  : commonValueExpression
  ;

//<common value expression> ::=
//         <numeric value expression>
//     |   <string value expression>
//     |   <datetime value expression>
//     |   <interval value expression>
//     |   <user-defined type value expression>
//     |   <reference value expression>
//     |   <collection value expression>
// TODO(ISO): Add the remaining <common value expression> alternatives:
//   - stringValueExpression
//   - datetimeValueExpression
//   - intervalValueExpression
//   - userDefinedTypeValueExpression
//   - referenceValueExpression
//   - collectionValueExpression
commonValueExpression
  : numericValueExpression
  ;

// <numeric value expression> ::=
//    <term>
//  | <numeric value expression>  <plus sign>  <term>
//  | <numeric value expression>  <minus sign>  <term>

numericValueExpression
  : term
  | numericValueExpression plusSign term
  | numericValueExpression minusSign term
  ;

// <term> ::=
//    <factor>
//  | <term>  <asterisk>  <factor>
//  | <term>  <solidus>  <factor>
term
  : factor
  | term ASTERISK factor
  | term SOLIDUS factor
  ;

// <factor> ::=
//  [ <sign>  ] <numeric primary>
factor
  : sign? numericPrimary
  ;

sign
  : plusSign
  | minusSign
  ;

plusSign
  : PLUS_SIGN
  ;

minusSign
  : MINUS_SIGN
  ;

//<numeric primary> ::=
//    <value expression primary>
//  | <numeric value function>
// TODO(ISO): Add the remaining <numeric primary> alternative:
//   - numericValueFunction
numericPrimary
  : valueExpressionPrimary
  ;

// ********************************************************************************
// Value expressions primary
// ********************************************************************************

// <value expression primary> ::=
//    <parenthesized value expression>
//  | <nonparenthesized value expression primary>
valueExpressionPrimary
  : parenthesizedValueExpression
  | nonParenthesizedValueExpressionPrimary
  ;

//<parenthesized value expression> ::=
//  <left paren>  <value expression>  <right paren>
parenthesizedValueExpression
  : LEFT_PAREN valueExpression RIGHT_PAREN
  ;

//<nonparenthesized value expression primary> ::=
//       <unsigned value specification>
//     | <column reference>
//     | <set function specification>
//     | <window function>
//     | <nested window function>
//     | <scalar subquery>
//     | <case expression>
//     | <cast specification>
//     | <field reference>
//     | <subtype treatment>
//     | <method invocation>
//     | <static method invocation>
//     | <new specification>
//     | <attribute or method reference>
//     | <reference resolution>
//     | <collection value constructor>
//     | <array element reference>
//     | <multiset element reference>
//     | <next value expression>
//     | <routine invocation>
//     | <row pattern navigation operation>
//     | <JSON value function>
nonParenthesizedValueExpressionPrimary
  : unsignedValueSpecification
  | columnReference
  ;

unsignedValueSpecification
  : UNSIGNED_INTEGER
  ;

// ********************************************************************************
// Identifiers / column references
// ********************************************************************************

columnReference
  : identifierChain
  ;

identifierChain
  : IDENTIFIER (PERIOD IDENTIFIER)*
  ;

// ********************************************************************************
// Table expressions
// ********************************************************************************

//<table expression> ::=
//  <from clause>
//      [ <where clause>  ]
//      [ <group by clause>  ]
//      [ <having clause>  ]
//      [ <window clause>  ]
tableExpression
  : fromClause
  ;

fromClause
  : FROM tableReference
  ;

tableReference
  : identifierChain
  ;
