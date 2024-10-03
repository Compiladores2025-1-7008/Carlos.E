import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lexer extends javax.swing.JFrame {
    // Estado actual.
    public static int estado = 0;
    // Posición actual con respecto al editor.
    public int posicion = 0;
    // Texto de entrada del análisis.
    public String fuente = "";
    // Se analizará carácter por carácter el String fuente. 'caracter'
    // representará el símbolo.
    public char caracter;
    // Es el carácter o conjunto de caracteres que representan una 
    // acción, atributo, etc. en el lenguaje.
    public String lexema = "";
    // Es el nombre del Token asignado
    public String token = "";
    // Lista completa de lexemas encontradas en el String fuente.
    public ArrayList<String> listaLexema = new ArrayList<>();
    // Indica el tipo de lexema en la lista lexema.
    public ArrayList<String> listaToken = new ArrayList<>();

    // Componentes de la interfaz gráfica
    public javax.swing.JTextArea destino = new javax.swing.JTextArea();
    public javax.swing.JButton jButton1 = new javax.swing.JButton();
    public javax.swing.JTextArea texto = new javax.swing.JTextArea();

    public Lexer() {
        destino.setEditable(false);
        jButton1.setText("Iniciar");
    }

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public void iniciarProceso() {
        caracter = fuente.charAt(posicion);
        switch (estado) {
            case 0:
                if (caracter == ':') {
                    estado = 4;
                    lexema += Character.toString(caracter);
                } else if (caracter == '+') {
                    lexema += Character.toString(caracter);
                    token = "SUMA";
                    addList(lexema, token);
                    lexema = "";
                } else if (Character.isLetter(caracter)) {
                    estado = 1;
                    lexema += Character.toString(caracter);
                } else if (Character.isDigit(caracter)) {
                    estado = 2;
                    lexema += Character.toString(caracter);
                } else if (caracter == '0'| caracter == '.') {
                    estado = 3;
                    lexema += Character.toString(caracter);
                } else if (esEspacio(caracter)) {
                    // Ignorar espacios
                } else {
                    lexema = "";
                }
                break;
            case 1:
                if (Character.isLetter(caracter)) {
                    token = "ID";
                    lexema += Character.toString(caracter);
                } else {
                    addList(lexema, "ID");
                    estado = 0;
                    lexema = "";
                }
                break;
            case 2:
                if (caracter == '.') {
                    token = "REAL";
                    lexema += Character.toString(caracter);
                    estado = 3;
                }  else if (Character.isDigit(caracter)) {
                    token = "ENT";
                    lexema += Character.toString(caracter);
                } else {
                    addList(lexema, "ENT");
                    estado = 0;
                    lexema = "";
                }
                break;
            case 3:
                if (Character.isDigit(caracter)|caracter == '.' ) {
                    token = "REAL";
                    lexema += Character.toString(caracter);
                } else {
                    addList(lexema, "REAL");
                    lexema = "";
                    estado = 0;
                }
                break;
            case 4:
                if (caracter == '=' && lexema.length() == 2) {
                    lexema += Character.toString(caracter);
                    addList(lexema, "ASIG");
                    lexema = "";
                    estado = 0;
                } else if (caracter == ':') {
                    token = "ASIG";
                    lexema += Character.toString(caracter);
                } else {
                    lexema = "";
                }
                break;
            default:
                break;
        }

        // Incrementar posición y continuar el análisis si hay más caracteres
        posicion++;
        if (posicion < fuente.length()) {
            iniciarProceso();
        } else {
            if (!lexema.isEmpty()) addList(lexema, token); // Manejar último lexema
            imprimirLista();
        }
    }

    private boolean esEspacio(char c) {
        return c == '\n' || c == '\t' || c == ' ';
    }

    private void imprimirLista() {
        String auxiliar = "Token ------- Lexema\n";
        for (int i = 0; i < listaLexema.size(); i++) {
            auxiliar += listaToken.get(i) + " ------- " + listaLexema.get(i) + "\n";
        }
        System.out.print(auxiliar);
    }

    private void addList(String lex, String token) {
        listaLexema.add(lex);
        listaToken.add(token);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Lexer <ruta-del-archivo>");
            return;
        }

        String filePath = args[0];
        try {
            String input = readFile(filePath);
            Lexer lexer = new Lexer();
            lexer.fuente = input.trim();
            lexer.iniciarProceso();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
