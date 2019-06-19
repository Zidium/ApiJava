package zidium.threads;

import org.junit.Test;
import static org.junit.Assert.*;


public class LoopProcessorTests {
    
    public LoopProcessorTests() {
    }
    
    public static int Value = 0;

    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        Value = 0;
        CancelationToken cancelationToken = new CancelationToken();
        LoopProcessorAction action = () -> {
            Value = Value + 1;
        };
        try(LoopProcessor loop = new LoopProcessor(action, 1, cancelationToken)){
            loop.start();
            Thread.sleep(300);
            assertTrue(Value > 5);
        }        
    }

    @Test
    public void testBeginStop() throws Exception {
        System.out.println("testBeginStop");
        Value = 0;
        CancelationToken cancelationToken = new CancelationToken();
        LoopProcessorAction action = () -> {
            Value = Value + 1;
        };
        try(LoopProcessor loop = new LoopProcessor(action, 1, cancelationToken)){
            assertTrue(loop.isStoped());
            loop.start();
            Thread.sleep(300);
            assertFalse(loop.isStoped());
            assertTrue(Value > 5);
            loop.beginStop();
            Thread.sleep(100);
            int v1 = Value;
            Thread.sleep(100);
            int v2 = Value;
            assertEquals(v1, v2);
            assertTrue(loop.isStoped());
        }
    } 

    @Test
    public void testIsStoped() {
    }

    @Test
    public void testWaitStoped1() throws Exception {
        System.out.println("testBeginStop");
        CancelationToken cancelationToken = new CancelationToken();
        
        // случай, когда срабатывает таймаут
        LoopProcessorAction action = () -> {                           
            try {
                Thread.sleep(800);
            } 
            catch (InterruptedException ex) {
            }
        };
        try(LoopProcessor loop = new LoopProcessor(action, 1, cancelationToken)){            
            assertTrue(loop.isStoped());
            loop.start();
            Thread.sleep(300);
            assertFalse(loop.isStoped());
            loop.beginStop();            
            loop.waitStoped(100);
            assertFalse(loop.isStoped());
        }
        
        // случай, когда НЕ срабатывает таймаут        
        try(LoopProcessor loop = new LoopProcessor(action, 1, cancelationToken)){
            assertTrue(loop.isStoped());
            loop.start();
            Thread.sleep(300);
            assertFalse(loop.isStoped());
            loop.beginStop();            
            loop.waitStoped(1000);
            assertTrue(loop.isStoped());
        }
    }

    @Test
    public void testClose() throws Exception {
    }
}
