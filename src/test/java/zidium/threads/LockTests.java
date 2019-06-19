package zidium.threads;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;


public class LockTests {
    
    private static ArrayList<String> _values = new ArrayList<String>();
    private static Object _lock = new Object();
    
    public static void add(String value) throws InterruptedException{
        //Thread.sleep(500);  
        synchronized(_lock){
           _values.add(value); 
           Thread.sleep(500);  
        }        
    }     

    /**
     * Проверим, что блокировки выполняются последовательно (в той же последовательности)
     */
    @Test
    public void test1() throws Exception {
        System.out.println("start");   
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyRun("i" + i));
            threads[i].start();
            Thread.sleep(200);
        }  
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        for (int i = 0; i < threads.length; i++) {
            assertEquals("i" + i, _values.get(i));
        }
    } 
    
    class MyRun implements Runnable{

        private String _value;
        
        public MyRun(String value){
            _value = value;
        }
        
        @Override
        public void run() {
            try {
                LockTests.add(_value);
            } 
            catch (InterruptedException ex) {
            }
        }        
    }
}
