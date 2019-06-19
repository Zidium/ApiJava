package zidium.threads;

public class CancelationTokenException extends Exception {
    public CancelationTokenException(){
        super("job is canceled");
    } 
}
