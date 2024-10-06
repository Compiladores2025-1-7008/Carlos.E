package main.java;

import java.io.IOException;
import main.jflex.Lexer;

public class Parser implements ParserInterface {
    private Lexer lexer;
    private int actual;
    
    // Variable que contiene el token actual (clase léxica) que se está procesando.
    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    
    /**
     * Constructor del Parser que recibe un objeto Lexer.
     * 
     * @param lexer Instancia del analizador léxico que genera los tokens.
     */
    public void eat(int claseLexica) {
        if(actual == claseLexica) {
            try {
                actual = lexer.yylex();
            } catch (IOException ioe) {
                System.err.println("Failed to read next token");
            }
        }
        else
            System.err.println("Se esperaba el token: "+ actual); 
    }

    /**
     * Método para reportar un error de sintaxis. Muestra un mensaje de error junto
     * con el número de línea donde se encuentra el error.
     * 
     * @param msg Mensaje de error a mostrar.
     */
    public void error(String msg) {
        System.err.println("ERROR DE SINTAXIS: "+msg+" en la línea "+lexer.getLine());
    }

    /**
     * Método principal del parser que comienza el análisis sintáctico. Inicializa
     * la lectura del primer token y luego llama al no terminal inicial 'S'.
     */
    public void parse() {
        try {
            this.actual = lexer.yylex();
        } catch (IOException ioe) {
            System.err.println("Error: No fue posible obtener el primer token de la entrada.");
            System.exit(1);
        }
        S();
        if(actual == 0) //llegamos al EOF sin error
            System.out.println("La cadena es aceptada");
        else 
            System.out.println("La cadena no pertenece al lenguaje generado por la gramática");
    }

    public void S() { // S() = programa() → declaraciones sentencias
        declaraciones();
        sentencias();
    }

    public void declaraciones() { // declaraciones → declaracion declaraciones'
        declaracion();
        declaracionesPrima();
    }

    public void declaracionesPrima() { // declaraciones' → declaracion declaraciones' | ε
        if (actual == ClaseLexica.INT || actual == ClaseLexica.FLOAT) {
            declaracion();
            declaracionesPrima();
        }
        // Si es ε (otro token), no hacemos nada (opcional)
    }

    public void declaracion() { // declaracion → tipo lista_var ;
        tipo();
        listaVar();
        eat(ClaseLexica.PUNTO_COMA);
    }

    public void tipo() { // tipo → int | float
        if (actual == ClaseLexica.INT) {
            eat(ClaseLexica.INT);
        } else if (actual == ClaseLexica.FLOAT) {
            eat(ClaseLexica.FLOAT);
        } else {
            error("Se esperaba 'int' o 'float'");
        }
    }

    public void listaVar() { // lista_var → identificador lista_var'
        eat(ClaseLexica.ID);
        listaVarPrima();
    }

    public void listaVarPrima() { // lista_var' → , identificador lista_var' | ε
        if (actual == ClaseLexica.COMA) {
            eat(ClaseLexica.COMA);
            eat(ClaseLexica.ID);
            listaVarPrima();
        }
        // Si es ε, no hacemos nada (opcional)
    }

    public void sentencias() { // sentencias → sentencia sentencias'
        sentencia();
        sentenciasPrima();
    }

    public void sentenciasPrima() { // sentencias' → sentencia sentencias' | ε
        if (actual == ClaseLexica.ID || actual == ClaseLexica.IF || actual == ClaseLexica.WHILE) {
            sentencia();
            sentenciasPrima();
        }
        // Si es ε, no hacemos nada (opcional)
    }

    public void sentencia() { // sentencia → identificador = expresion ; | if (...) | while (...)
        if (actual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
            eat(ClaseLexica.IGUAL);
            expresion();
            eat(ClaseLexica.PUNTO_COMA);
        } else if (actual == ClaseLexica.IF) {
            eat(ClaseLexica.IF);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
            eat(ClaseLexica.ELSE);
            sentencias();
        } else if (actual == ClaseLexica.WHILE) {
            eat(ClaseLexica.WHILE);
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
            sentencias();
        } else {
            error("Error en la 'sentencia'. Token inesperado: " + actual);
        }
    }

    public void expresion() { // expresion → termino expresion'
        termino();
        expresionPrima();
    }

    public void expresionPrima() { // expresion' → + termino expresion' | - termino expresion' | ε
        if (actual == ClaseLexica.MAS) {
            eat(ClaseLexica.MAS);
            termino();
            expresionPrima();
        } else if (actual == ClaseLexica.MENOS) {
            eat(ClaseLexica.MENOS);
            termino();
            expresionPrima();
        }
        // Si es ε, no hacemos nada (opcional)
    }

    public void termino() { // termino → factor termino'
        factor();
        terminoPrima();
    }

    public void terminoPrima() { // termino' → * factor termino' | / factor termino' | ε
        if (actual == ClaseLexica.MULTIPLICACION) {
            eat(ClaseLexica.MULTIPLICACION);
            factor();
            terminoPrima();
        } else if (actual == ClaseLexica.DIVISION) {
            eat(ClaseLexica.DIVISION);
            factor();
            terminoPrima();
        }
        // Si es ε, no hacemos nada (opcional)
    }

    public void factor() { // factor → identificador | numero | ( expresion )
        if (actual == ClaseLexica.ID) {
            eat(ClaseLexica.ID);
        } else if (actual == ClaseLexica.NUM) {
            eat(ClaseLexica.NUM);
        } else if (actual == ClaseLexica.LPAR) {
            eat(ClaseLexica.LPAR);
            expresion();
            eat(ClaseLexica.RPAR);
        } else {
            error("Error en el 'factor'. Token inesperado: " + actual);
        }
    }

}