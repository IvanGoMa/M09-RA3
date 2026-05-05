import java.io.ObjectInputStream;

public class FilServidorXat extends Thread {
    
    private ObjectInputStream input;

    public FilServidorXat(String nom, ObjectInputStream input){
        super(nom);
        this.input = input;
    }

    public ObjectInputStream getInput(){
        return input;
    }

    @Override
    public void run(){
        
        String missatge;
        try {
            while (true) {
                System.out.print("Missatge ('sortir' per tancar): ");
                missatge = (String) input.readObject();
                System.out.println("Rebut: " + missatge);
                if (missatge.equals(ServidorXat.MSG_SORTIR)) {
                    System.out.println("Fil de xat finalitzat.");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Connexió tancada: " + e.getMessage());
        }

    }

}
