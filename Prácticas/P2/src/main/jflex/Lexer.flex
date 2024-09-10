/**
 * Escáner que detecta el lenguaje C_1
*/

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{
public Token actual;
%}

%public
%class Lexer
%standalone
%unicode

%%
[\t\r\n]+  {/* Espacios */}
// Acciones léxicas
"int"           {System.out.println(new Token(ClaseLexica.INT, yytext()));}
"float"         {System.out.println(new Token(ClaseLexica.FLOAT, yytext()));}
"if"            {System.out.println(new Token(ClaseLexica.IF, yytext()));} 
"while"         {System.out.println(new Token(ClaseLexica.WHILE, yytext()));}
"else"          {System.out.println(new Token(ClaseLexica.ELSE, yytext()));}
"("             {System.out.println(new Token(ClaseLexica.LPAR, yytext()));}
")"             {System.out.println(new Token(ClaseLexica.RPAR, yytext()));}
";"             {System.out.println(new Token(ClaseLexica.PYC, yytext()));}
","             {System.out.println(new Token(ClaseLexica.COMA, yytext()));}
//Números enteros y decimales
([1-9[0-9]+|0]).([0-9]*|([0-9]e[0-9]))          {System.out.println(new Token(ClaseLexica.NUMERO, yytext()));}
//Identificadores validos (empiezan con una letra y tiene maximo 32 caracteres)
([a-z_][a-z_0-9]*){1,31}                   {System.out.println(new Token(ClaseLexica.ID, yytext()));}

