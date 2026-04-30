import java.io.InputStream;

public class FilServidorXat extends Thread {
    
    private InputStream input;

    public FilServidorXat(String nom, InputStream input){
        super(nom);
        this.input = input;
    }

    public InputStream getInput(){
        return input;
    }

    @Override
    public void run(){

    }

}
