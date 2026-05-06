import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientXat {
    
    private Socket socket;
    private ObjectOutputStream sortida;
    private ObjectInputStream entrada;

    public void connecta(){
        try {
            
            InetAddress adreca = InetAddress.getByName(ServidorXat.HOST);
            socket = new Socket(adreca, ServidorXat.PORT);
            System.out.printf("Client connectat a %s:%d%n", ServidorXat.HOST,ServidorXat.PORT);
            sortida = new ObjectOutputStream(socket.getOutputStream());
            sortida.flush();
            entrada = new ObjectInputStream(socket.getInputStream());
            System.out.println("Flux d'entrada i sortida creat");

            
        } catch (IOException e) {
            System.err.println("Error al connectar: " + e.getMessage());
        }
    }

    public void enviaMissatge(String missatge){
        try {
            System.out.println("Enviant missatge: " + missatge);
            sortida.writeObject(missatge);
            sortida.flush();
        } catch (Exception e) {}
        
    }

    public void tancarClient(){
        try {
            System.out.println("Tancant client...");
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

        try {
            FilLectorCX fil = new FilLectorCX("Client", client.entrada);
            fil.start();
            System.out.println("Fil de lectura iniciat");
        } catch (Exception e) {}
        
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String linia = scanner.nextLine();
            client.enviaMissatge(linia);
            if (linia.equals("sortir")) break;
        }
        scanner.close();

 
        client.tancarClient();
        System.out.println("Client tancat");

        

    }


}
