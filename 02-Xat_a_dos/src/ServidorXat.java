import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;
import java.io.InputStream;

public class ServidorXat{
    public final static int PORT = 9999;
    public final static String HOST = "localhost";
    private final String MSG_SORTIR = "sortir";
    private ServerSocket serverSocket;

    public void iniciarServidor(){
        try {
            
            serverSocket = new ServerSocket(PORT);
            System.out.printf("Servidor en marxa a %s:%d%n", HOST, PORT );
            System.out.printf("Esperant connexions a %s:%d%n", HOST, PORT );
            
        } catch (IOException e) {
            System.err.println("Error al connectar: " + e.getMessage());
        }
    }

    public void pararServidor(){
        try {
            
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.out.println("Servidor tancat.");
        } catch (IOException e) {
            System.err.println("Error tancant: " + e.getMessage());
        }
    }

    public String getNom(InputStream input, OutputStream output){
        PrintWriter out =  new PrintWriter(output,true);
        out.println("Escriu el teu nom");
        String nom;
        try {
            nom = new BufferedReader(new InputStreamReader(input)).readLine();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            return nom;
        }
        
    }

    public static void main(String[] args) {
        ServidorXat srv = new ServidorXat();
        srv.iniciarServidor();
        try {
            Socket clientSocket = srv.serverSocket.accept();
            InputStream entrada = clientSocket.getInputStream();
            OutputStream sortida = clientSocket.getOutputStream();
            FilServidorXat fil = new FilServidorXat("hola",entrada);
            
            Scanner scanner = new Scanner(System.in);
            String linia;
            PrintWriter out = new PrintWriter(sortida);
            while ((linia = scanner.nextLine()) != null){
                out.println(linia);
            }
            fil.join();
            clientSocket.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
        

    }
}   

