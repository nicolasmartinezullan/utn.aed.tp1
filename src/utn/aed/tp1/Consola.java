package utn.aed.tp1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
public class Consola {
    /* Todos los metodos de esta clase son estaticos, y por lo tanto pueden ser invocados sin tener que crear objetos de la clase. Es 
     * suficiente con nombrar la clase al invocar el metodo:   int x = Consola.readInt(); */

    /* Lee un string desde teclado. El string termina con un salto de linea
     * @return el string leido (sin el salto de linea) */
    public static String readLine() {
        int ch;
        String r = "";
        boolean done = false;
        while (!done) {
            try {
                ch = System.in.read();
                if (ch < 0 || (char) ch == '\n') {
                    done = true;
                }
                else {
                    if ((char) ch != '\r') {
                        r = r + (char) ch;
                    }
                }
            }
            catch (java.io.IOException e) {
                done = true;
            }
        }
        return r;
    }

    /* Lee un integer desde teclado. La entrada termina con un salto de linea
     * @return el valor cargado, como un int */
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.print("Por favor, ingrese un numero entero: ");
            }
        }
    }

    /* Lee un double desde teclado. La entrada termina con un salto de linea
     * @return el valor cargado, como un double */
    public static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(readLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.print("Por favor, ingrese un numero decimal: ");
            }
        }
    }

    /* Lee un float desde teclado. La entrada termina con un salto de linea
     * @return el valor cargado, como un float */
    public static float readFloat() {
        while (true) {
            try {
                return Float.parseFloat(readLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.print("Por favor, ingrese un numero decimal: ");
            }
        }
    }
    
    //<editor-fold defaultstate="expanded" desc="Traido desde In.class">
    
    /**
     * Objeto utilizado para parsear la linea introducida por el usuario
     * mediante el teclado.
     *
     * @see #getNextToken(String)
     */
    private static StringTokenizer st;
    /**
     * Objeto de I/O (entrada/salida) utilizado para leer los datos desde el
     * teclado.
     *
     * @see #getNextToken(String)
     */
    private static BufferedReader source;

    /**
     * Metodo utilizado para leer un caracter.
     *
     * @return el primer caracter de la cadena introducida o '\0' (caracter
     * nulo) si se introdujo una cadena vacia.
     * @see #readString()
     */
    public static char readChar() {
        char car = '\0';
        String str;
        str = getNextToken("\0");
        if (str.length() > 0) {
            car = str.charAt(0);
            st = new StringTokenizer(str.substring(1));
        }
        return car;
    }

    /**
     * Metodo utilizado para obtener el siguiente elemento del objeto parseador
     * de la entrada. Los elementos estaran definidos por el delimitador que se
     * recibe como parametro.
     *
     * @param delim delimitador a utilizar durante el parseo de la entrada. Si
     * el parametro es <code>null</code> se tomar�n los delimitadores indicados
     * en {@link #readString() readString()}.
     * @return siguiente elemento del parseador de cadenas, considerando como
     * separadores al par&aacute;metro recibido.
     * @see #getNextToken()
     * @see #st
     * @see "Documentaci&oacute;n de la clase <code>StringTokenizer</code> en el
     * sitio oficial de Java: <a
     * href="http://java.sun.com">http://java.sun.com</a>."
     */
    private static String getNextToken(String delim) {
        String input;
        String retVal = "";
        try {
            if ((st == null) || !st.hasMoreElements()) {
                if (source == null) {
                    source = new BufferedReader(new InputStreamReader(System.in));
                }
                input = source.readLine();
                st = new StringTokenizer(input);
            }
            if (delim == null) {
                delim = " \t\n\r\f";
            }
            retVal = st.nextToken(delim);
        }
        catch (NoSuchElementException e1) {
            // si ocurre una excepci�n, no hacer nada
        }
        catch (IOException e2) {
            // si ocurre una excepci�n, no hacer nada
        }
        return retVal;
    }
    
    //</editor-fold>
}