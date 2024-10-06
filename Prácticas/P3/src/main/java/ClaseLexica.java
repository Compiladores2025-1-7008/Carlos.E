package main.java;

/**
 * Esta clase define las constantes que representan las clases léxicas
 * utilizadas por el analizador léxico (Lexer). Cada clase léxica está
 * asociada con un valor entero para identificar los tokens de manera
 * eficiente durante el análisis sintáctico.
 */
public class ClaseLexica {
    public static final int INT = 1;
    public static final int FLOAT = 2;
    public static final int ID = 3;
    public static final int IGUAL = 4;
    public static final int MAS = 5;
    public static final int MENOS = 6;
    public static final int MULTIPLICACION = 7;
    public static final int DIVISION = 8;
    public static final int PUNTO_COMA = 9;
    public static final int COMA = 10;
    public static final int LPAR = 11;
    public static final int RPAR= 12;
    public static final int IF = 13;
    public static final int ELSE = 14;
    public static final int WHILE = 15;
    public static final int NUM = 16;
    public static final int EPSILON = 17;
}