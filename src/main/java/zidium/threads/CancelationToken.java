package zidium.threads;


public class CancelationToken {
    
    private boolean _canceled = false;
    
    public boolean isCanceled(){
        return _canceled;
    }
    
    public void throwExceptionIfCanceled() throws CancelationTokenException{
        if (_canceled){
            throw new CancelationTokenException();
        }
    }
    
    public void setCanceled(){
        _canceled = true;
    }
}
