import java.io.ObjectInputStream;


public class FilLectorCX extends Thread{

    private ObjectInputStream input;

    public FilLectorCX(String nom, ObjectInputStream input){
        super(nom);
        this.input = input;

    }

    @Override
    public void run(){

        try {
            String missatge;
            while ((missatge = (String) input.readObject()) != null) {
                System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
            }
        } catch (Exception e) {
            System.out.println("El servidor ha tancat la connexió.");
        }


    }
    
}
