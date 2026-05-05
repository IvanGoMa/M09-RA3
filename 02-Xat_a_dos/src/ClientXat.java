import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientXat {
    
    private Socket socket;
    private ObjectOutputStream sortida;
    private ObjectInputStream entrada;

    public void connecta(){
        try {
            
            InetAddress adreca = InetAddress.getByName(ServidorXat.HOST);
            socket = new Socket(adreca, ServidorXat.PORT);
            sortida = new ObjectOutputStream(socket.getOutputStream());
            sortida.flush();
            entrada = new ObjectInputStream(socket.getInputStream());

            
        } catch (IOException e) {
            System.err.println("Error al connectar: " + e.getMessage());
        }
    }

    public void enviaMissatge(String missatge){
        try {
            sortida.writeObject(missatge);
            sortida.flush();
        } catch (Exception e) {}
        
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

        try {
            FilLectorCX fil = new FilLectorCX("Client", client.sortida);
            fil.start();
        } catch (Exception e) {}
        
        try {
            String missatge;
            while ((missatge = (String) client.entrada.readObject()) != null) {
                System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
            }
        } catch (Exception e) {
            System.out.println("El servidor ha tancat la connexió.");
        }
 
        client.tancarClient();

        

    }


}
