
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;


public class FilLectorCX extends Thread{

    private ObjectOutputStream output;

    public FilLectorCX(String nom, ObjectOutputStream output){
        super(nom);
        this.output = output;

    }

    @Override
    public void run(){

        Scanner scanner = new Scanner(System.in);
        try {
            while (scanner.hasNextLine()) {
                String linia = scanner.nextLine();
                output.writeObject(linia);
                output.flush();
                System.out.println("Enviant missatge: " + linia);
                if (linia.equals(ServidorXat.MSG_SORTIR)) break;
            }
        } catch (IOException e) {
            System.out.println("Error enviant missatge: " + e.getMessage());
        } finally {
            scanner.close();
        }

    }
    
}
