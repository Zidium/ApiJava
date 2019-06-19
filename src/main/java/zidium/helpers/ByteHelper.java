package zidium.helpers;

import java.util.Base64;
import java.util.List;

public class ByteHelper {

    public static byte setBit(byte b, int position, boolean value) {
        if (value) {
            // set a bit
            return (byte) (b | (1 << position));
        }
        // un-set a bit
        return (byte) (b & ~(1 << position));
    }

    public static String toBinaryString(byte b) {
        String bin = Integer.toBinaryString(b & 0xFF);
        return String.format("%8s", bin).replace(' ', '0');
    }

    public static boolean getBit(byte b, int position) {
        return ((b >> position) & 1) == 1;
    }

    /**
     * Получает число из байта BSD формата (Двоично-десятичный код)
     *
     * @param b
     * @return
     */
    public static int parseBCD(byte b) {
        int d0 = (b & 0x0F);
        int d1 = ((b & 0xF0) >> 4);
        return d1 * 10 + d0;
    }

    /**
     * Переводит число в двоично-десятичный код
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte toBcdByte(int value) throws Exception {
        if (value < 0) {
            throw new Exception("value < 100");
        }
        if (value > 99) {
            throw new Exception("value > 99");
        }
        int d1 = value / 10;
        int d0 = value % 10;
        int result = ((d1 << 4) & 0xF0) + (d0 & 0x0F);
        return (byte) result;
    }

    public static boolean areEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static int byte255ToInt32(byte value) {
        return (int) value & 0xFF;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] toPrimitiveArray(Byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i];
        }
        return result;
    }

    public static byte[] toPrimitiveArray(List<Byte> bytes) {
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }

    public static String toHex(byte b) {
        return String.format("%02x", b);
    }
    
    public static String toHex(List<Byte> byteList){
        byte[] bytes = toPrimitiveArray(byteList);
        return byteArrayToHex(bytes);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String byteArrayToHex(byte[] bytes, int length) {
        StringBuilder sb = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            byte b = bytes[i];
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String byteListToHex(List<Byte> list) {
        StringBuilder sb = new StringBuilder(list.size() * 2);
        for (byte b : list) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] getArrayCopy(byte[] data, int start, int length) {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = data[start + i];
        }
        return result;
    }

    public static byte[] reverseArray(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[data.length - i - 1];
        }
        return result;
    }

    public static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte fromHex2Chars(String hex) throws Exception {
        if (hex == null) {
            throw new Exception("hex is null");
        }
        if (hex.length() != 2) {
            throw new Exception("hex.length()!=2");
        }
        return (byte) ((Character.digit(hex.charAt(0), 16) << 4)
                + Character.digit(hex.charAt(1), 16));
    }

    public static byte fromHex(String hex) throws Exception {
        if (hex == null) {
            throw new Exception("hex is null");
        }
        if (hex.length() == 4) {
            hex = hex.substring(2);
        }
        return fromHex2Chars(hex);
    }
}
