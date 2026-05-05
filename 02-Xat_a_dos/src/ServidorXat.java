import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;

public class ServidorXat{
    public final static int PORT = 9999;
    public final static String HOST = "localhost";
    public final static String MSG_SORTIR = "sortir";
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

    public String getNom(InputStream input, ObjectOutputStream output){
        String nom = null;
        try {
            output.writeObject("Escriu el teu nom");
            nom = new BufferedReader(new InputStreamReader(input)).readLine();
        } catch (Exception e) {}
        return nom;
        
    }

    public static void main(String[] args) {
        ServidorXat srv = new ServidorXat();
        srv.iniciarServidor();

        try {
            Socket clientSocket = srv.serverSocket.accept();
            ObjectOutputStream sortida = new ObjectOutputStream(clientSocket.getOutputStream());
            sortida.flush();
            ObjectInputStream entrada = new ObjectInputStream(clientSocket.getInputStream());
            String nom = srv.getNom(entrada, sortida);
            FilServidorXat fil = new FilServidorXat(nom,entrada);
            
            Scanner scanner = new Scanner(System.in);
            String linia;
            while ((linia = scanner.nextLine()) != null){
                sortida.writeObject(linia);
                sortida.flush();
                if (linia.equals(ServidorXat.MSG_SORTIR)) break;
            }
            scanner.close();
            fil.join();
            clientSocket.close();

        } catch (Exception e) {}
        

    }
}   

