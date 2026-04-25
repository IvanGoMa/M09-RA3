import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
   
    private final int PORT = Servidor.PORT;
    private final String HOST = Servidor.HOST;
    private Socket socket;
    private PrintWriter out;

    public void connecta(){
        try {
            
            InetAddress adreca = InetAddress.getByName(HOST);
            socket = new Socket(adreca, PORT);

            
        } catch (IOException e) {
            System.err.println("Error al connectar: " + e.getMessage());
        }
    }

    public void tanca(){
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error tancant: " + e.getMessage());
        }
    }

    public void envia(String msg){
        try {
            
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(msg);
        } catch (IOException e) {
            System.err.println("Error enviant dades: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connecta();
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adéu!");
        client.tanca();
    }
}
