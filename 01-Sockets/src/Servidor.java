import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor{

    public final static int PORT = 7777;
    public final static String HOST = "localhost";
    private ServerSocket srvSocket;
    private Socket clientSocket;

    public void connecta(){
        try {
            
            srvSocket = new ServerSocket(PORT);
            System.out.printf("Servidor en marxa a %s:%d%n", HOST, PORT );
            System.out.printf("Esperant connexions a %s:%d%n", HOST, PORT );

            clientSocket = srvSocket.accept();
            System.out.println("Client connectat: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error al connectar: " + e.getMessage());
        }
    }

    public void repDades(){

        try{
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );

                String linia;
                while ((linia = entrada.readLine()) != null) {
                    System.out.println("Rebut: " + linia);
                }
        } catch (IOException e) {
            System.err.println("Error rebent dades: " + e.getMessage());
        }
    }

    public void tanca(){
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (srvSocket != null && !srvSocket.isClosed()) {
                srvSocket.close();
            }
            System.out.println("Servidor tancat.");
        } catch (IOException e) {
            System.err.println("Error tancant: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Servidor srv = new Servidor();
        srv.connecta();
        srv.repDades();
        srv.tanca();
    }

}