/**
 * Escáner que detecta números y palabras
*/

%%

%public
%class Lexer
%standalone

// Definiciones de patrones
digito=[0-9]
letra=[a-zA-Z]
hexadecimal=("0x"({digito}|[A-F])*)
identificador=({letra}({letra}|{digito}|_){0,31})
espacio=[ \t\n\r]+

// Palabras reservadas
reservado = "if" | "else" | "while" | "for" | "return" | "int"

%%

// Reglas léxicas
{espacio} {System.out.print("Encontre un espacio en blanco" + "\n");}
{hexadecimal} {System.out.print("Encontre un Hexadecimal: " +yytext()+ "\n" );}
{reservado} { System.out.print("Encontre la palabra reservada: " +yytext()+ "\n"); }
{identificador} { System.out.print("Encontre un identificador valido: "+yytext()+"\n"); }
