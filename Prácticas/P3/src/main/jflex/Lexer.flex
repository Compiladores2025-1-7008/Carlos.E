package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

public Token actual;
public int getLine() { return yyline; }

%}

%public
%class Lexer
%standalone
%unicode
%line

// Espacios en blanco: puede ser cualquier combinación de espacio, tabulación o nueva línea.
espacio=[ \t\n]
// Identificadores: secuencia de letras o guiones bajos que puede incluir números (máximo 31 caracteres).
id = ([a-z_][a-z_0-9]*){1,31}
// Números enteros: secuencia de uno o más dígitos.
numero = ([0-9]+)

//////////////////////////////////////////////////////////////////////////
// Sección de reglas léxicas
//////////////////////////////////////////////////////////////////////////

%%

{espacio}+ { /* Ignoramos espacios en blanco */ }
"int"    {  System.out.println("Encontramos una palabra reservada"); return ClaseLexica.INT; }
"float"  { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.FLOAT; }
"if"     { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.IF; }
"else"   { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.ELSE; }
"while"  { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.WHILE; }
"="      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.IGUAL; }
"+"      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.MAS; }
"-"      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.MENOS; }
"*"      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.MULTIPLICACION; }
"/"      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.DIVISION; }
";"      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.PUNTO_COMA; }
","      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.COMA; }
"("      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.LPAR; }
")"      { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.RPAR; }
{id}  {  System.out.println("Encontramos una palabra reservada"); return ClaseLexica.ID; }
{numero} {  System.out.println("Encontramos una palabra reservada"); return ClaseLexica.NUM; }

<<EOF>>  { return 0; }  // Al llegar al final del archivo (EOF), retornamos 0 indicando que no hay más tokens.
.        { return -1; }  // Cualquier otro carácter no reconocido se trata como un error y devuelve -1.