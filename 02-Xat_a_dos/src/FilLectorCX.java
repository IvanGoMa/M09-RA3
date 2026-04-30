
import java.io.OutputStream;

public class FilLectorCX extends Thread{

    private OutputStream output;

    public FilLectorCX(String nom, OutputStream output){
        super(nom);
        this.output = output;

    }

    @Override
    public void run(){
        while(true){
            
        }
    }
    
}
