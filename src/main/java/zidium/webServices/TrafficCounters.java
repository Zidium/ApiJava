package zidium.webServices;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Счётчики входящего и исходящего траффика
 */
public class TrafficCounters {
    
    private final AtomicLong _download = new AtomicLong();
    
    private final AtomicLong _upload = new AtomicLong();
    
    public void addDownload(long size){
        _download.addAndGet(size);
    }
    
    public void addUpload(long size){
        _upload.addAndGet(size);
    }
    
    public long getDownloadAndClear(){
        return _download.getAndSet(0);
    }
    
    public long getUploadAndClear(){
        return _upload.getAndSet(0);
    }
}
