package zidium.threads;

import org.junit.Test;
import static org.junit.Assert.*;

public class TimeoutTaskTest {
    
    public TimeoutTaskTest() {
    }    
    
    @Test
    public void testIsTimeout() {
        System.out.println("isTimeout");
        
        // нет таймаута
        TimeoutTask task = new TimeoutTask(2);
        task.doWork(() -> {
            // ничего не делаем
        });
        assertFalse(task.isTimeout());      
        
        // есть таймаут
        task = new TimeoutTask(2);
        task.doWork(() -> {
            // выполняем работу более 2 сек
            Thread.sleep(2200);
        });
        assertTrue(task.isTimeout());
    }   
    

    @Test
    public void testGetTimeoutStackTraceElements() {
        System.out.println("getTimeoutStackTraceElements");
        
        TimeoutTask task = new TimeoutTask(2);
        task.doWork(() -> {
            // выполняем работу более 2 сек
            Thread.sleep(2200);
        });
        assertTrue(task.isTimeout());
        assertNotNull(task.getTimeoutStackTraceElements());
    }  
    
    @Test
    public void testException() {
        System.out.println("testException");
        
        // нет таймаута
        TimeoutTask task = new TimeoutTask(2);
        task.doWork(() -> {
            throw new Exception("test");
        });
        assertFalse(task.isTimeout());
    }   
}
