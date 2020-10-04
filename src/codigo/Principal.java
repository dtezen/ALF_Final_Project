package codigo;
import java.io.File;

public class Principal {
    
    //Función Princiapl (Main)
    public static void main(String[] args) 
    {   
        //Ruta para crear el analizador sintáctico.
        String ruta = "C:/Users/Dennys Tezén.DESKTOP-5QCDR6M/Documents/NetBeansProjects/analizador_lexico/src/codigo/Lexer.flex";
        generarLexer(ruta); //Llamando al método creado para crear el Lexer.
    }
    
    //Método para crear el Lexer.
    //Se le ingresa la ruta como parámetro.
    public static void generarLexer(String ruta){
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }
}