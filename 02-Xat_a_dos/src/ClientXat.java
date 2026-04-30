import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

public class ClientXat {
    
    private Socket socket;
    private OutputStream sortida;
    private InputStream entrada;

    public void connecta(){
        try {
            
            InetAddress adreca = InetAddress.getByName(ServidorXat.HOST);
            socket = new Socket(adreca, ServidorXat.PORT);
            sortida = socket.getOutputStream();
            entrada = socket.getInputStream();

            
        } catch (IOException e) {
            System.err.println("Error al connectar: " + e.getMessage());
        }
    }

    public void enviaMissatge(String missatge){
         
        PrintWriter out = new PrintWriter(sortida);
        out.println(missatge);
    }

    public void tancarClient(){
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error tancant: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        ClientXat client = new ClientXat();
        client.connecta();
        FilLectorCX fil = new FilLectorCX("Client", client.sortida);
        fil.start();
        Scanner scanner = new Scanner(System.in);
        String linia;
        while ((linia = scanner.nextLine()) != null){
            client.enviaMissatge(linia);
        }
        scanner.close();
        client.tancarClient();
        

    }


}
