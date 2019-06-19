/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zidium.helpers;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class ByteHelperTest {
    
    public ByteHelperTest() {
    }   
    
    
    @Test
    public void testByteArrayToHex() {
        System.out.println("byteArrayToHex");
        byte[] bytes = new byte[]{(byte)0xB5,0x01,0x00,0x01,0x02};
        
        byte b0 = bytes[0];
        byte b1 = (byte)181;
        byte b2 = (byte)0xb5;
        
        assertEquals(b0, b1);
        assertEquals(b1, b2);
        assertEquals(b2, b0);
        
        String expResult = "b501000102";
        String result = ByteHelper.byteArrayToHex(bytes);
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testByteArrayToHex2() {
        System.out.println("byteArrayToHex2");
        byte[] bytes = new byte[]{(byte)0xB5,0x01,0x00,0x01,0x02};               
        String expResult = "b5010001";
        String result = ByteHelper.byteArrayToHex(bytes, 4);
        assertEquals(expResult, result);
    } 

    /**
     * Test of setBit method, of class ByteHelper.
     */
    @Test
    public void testSetBit() {
        System.out.println("setBit");
        
        byte b = 0;
        String binary = ByteHelper.toBinaryString(b);
        assertEquals("00000000", binary);
        
        b = ByteHelper.setBit(b, 0, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000001", binary);
        
        b = ByteHelper.setBit(b, 1, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000011", binary);
        
        b = ByteHelper.setBit(b, 2, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000111", binary);
        
        b = ByteHelper.setBit(b, 6, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("01000111", binary);
        
        b = ByteHelper.setBit(b, 7, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11000111", binary);
        
        b = ByteHelper.setBit(b, 3, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11001111", binary);
        
        b = ByteHelper.setBit(b, 4, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11011111", binary);
        
        b = ByteHelper.setBit(b, 5, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11111111", binary);
        
        b = ByteHelper.setBit(b, 7, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("01111111", binary);
        
        b = ByteHelper.setBit(b, 6, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00111111", binary);
        
        b = ByteHelper.setBit(b, 5, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00011111", binary);
        
        b = ByteHelper.setBit(b, 4, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00001111", binary);
        
        b = ByteHelper.setBit(b, 3, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000111", binary);
        
        b = ByteHelper.setBit(b, 2, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000011", binary);
        
        b = ByteHelper.setBit(b, 1, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000001", binary);
        
        b = ByteHelper.setBit(b, 0, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000000", binary);
    }

    /**
     * Test of toBinaryString method, of class ByteHelper.
     */
    @Test
    public void testToBinaryString() {
        System.out.println("toBinaryString");
        byte b = 0;
        String binary = ByteHelper.toBinaryString(b);
        assertEquals("00000000", binary);
        
        b = ByteHelper.setBit(b, 0, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000001", binary);
        
        b = ByteHelper.setBit(b, 1, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000011", binary);
        
        b = ByteHelper.setBit(b, 2, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000111", binary);
        
        b = ByteHelper.setBit(b, 6, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("01000111", binary);
        
        b = ByteHelper.setBit(b, 7, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11000111", binary);
        
        b = ByteHelper.setBit(b, 3, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11001111", binary);
        
        b = ByteHelper.setBit(b, 4, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11011111", binary);
        
        b = ByteHelper.setBit(b, 5, true);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("11111111", binary);
        
        b = ByteHelper.setBit(b, 7, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("01111111", binary);
        
        b = ByteHelper.setBit(b, 6, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00111111", binary);
        
        b = ByteHelper.setBit(b, 5, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00011111", binary);
        
        b = ByteHelper.setBit(b, 4, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00001111", binary);
        
        b = ByteHelper.setBit(b, 3, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000111", binary);
        
        b = ByteHelper.setBit(b, 2, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000011", binary);
        
        b = ByteHelper.setBit(b, 1, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000001", binary);
        
        b = ByteHelper.setBit(b, 0, false);
        binary = ByteHelper.toBinaryString(b);
        assertEquals("00000000", binary);
    }

    @Test
    public void testGetBit() {
        System.out.println("getBit");
        byte b = 0b01010101;
        
        assertTrue(ByteHelper.getBit(b, 0));
        assertTrue(ByteHelper.getBit(b, 2));
        assertTrue(ByteHelper.getBit(b, 4));
        assertTrue(ByteHelper.getBit(b, 6));
        
        assertFalse(ByteHelper.getBit(b, 1));
        assertFalse(ByteHelper.getBit(b, 3));
        assertFalse(ByteHelper.getBit(b, 5));
        assertFalse(ByteHelper.getBit(b, 7));
    }
    
    @Test
    public void testParseBCD() {
        System.out.println("parseBCD");       
        assertEquals(31, ByteHelper.parseBCD((byte)0b00110001));
    }
    
    @Test
    public void testtoBcdByte() throws Exception {
        System.out.println("toBcdByte");
        byte b1 = (byte)0b00110001;
        byte b2 = ByteHelper.toBcdByte(31);
        assertEquals(b1, b2);
    }
    
    @Test
    public void testBcd() throws Exception {
        System.out.println("testBcd");
        for (int i = 0; i < 100; i++) {
            byte b = ByteHelper.toBcdByte(i);
            int value = ByteHelper.parseBCD(b);
            assertEquals(i, value);
        }
    }
}
