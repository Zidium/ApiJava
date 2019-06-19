package zidium.http;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;


public class ZHttpClientTest {
    
    public ZHttpClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

   
    /**
     * Проверяет таймаут на чтение
     */
    @Test
    public void testSetReadTimeout() throws IOException {
        System.out.println("setReadTimeout");
        ZHttpClient client = new ZHttpClient();        
        String text = client.getString("http://fakesite.zidium.net/ok?wait=1");
        boolean success = text.indexOf("Все хорошо")>=0;
        assertTrue("error", success);
        
        int timeout = 1000;
        client.setConnectionTimeout(timeout);
        client.setReadTimeout(timeout);
        
        thrown.expect(SocketTimeoutException.class);
        text = client.getString("http://fakesite.zidium.net/ok?wait=2"); 
    }

    /**
     * Проверяет получение байт
     */
    @Test
    public void testGetBytes() throws Exception {
        System.out.println("getBytes");
        String url = "http://fakesite.zidium.net";
        ZHttpClient instance = new ZHttpClient();
        byte[] expResult = null;
        byte[] result = instance.getBytes(url);
        assertTrue(result.length > 10);
        
        // assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * проверяем получение строки
     */
    @Test
    public void testGetString() throws Exception {
        System.out.println("getString");
        String url = "http://fakesite.zidium.net";
        ZHttpClient instance = new ZHttpClient();        
        String text = instance.getString(url);
        boolean success = text.indexOf("Все хорошо")>=0;
        assertTrue("error", success);
    }

    /**
     * Test of postBytes method, of class ZHttpClient.
     */
    @Test
    public void testPostBytes_String_byteArr() throws Exception {
        System.out.println("postBytes");
        String url = "http://recursion.api.zidium.net/1.0/GetServerTime";
        byte[] data = new byte[]{ 0x00, 0x00, 0x00};
        ZHttpClient instance = new ZHttpClient();
        byte[] result = instance.postBytes(url, data);
        assertTrue(result.length > 10);
    } 
}
