package zidium.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class DateHelperTest {

    public DateHelperTest() {
    }

    /**
     * Test of toISO8601UTC method, of class DateHelper.
     */
    @Test
    public void testToISO8601UTC() {
        System.out.println("toISO8601UTC");
        Date date = DateHelper.createDate(2018, 2, 20, 23, 40, 59);
        String expResult = "2018-02-20 20:40:59.000";
        String result = DateHelper.toISO8601UTC(date);
        assertEquals(expResult, result);
    }
    
    /**
     * Была странная ошибка, ПО не могло прочитать из БД LineRow поле "StatisticsUpdateTime"
     * Получали: error ISO8601UTC format length: 2018-08-07 14:58:03.2018
     * Проверим, что ВСЕ возможеные значения дат не мог конвертироваться в строку длиннее 23 символов
     */
    @Test
    public void testToISO8601UTC_allRange() throws ParseException {
        System.out.println("toISO8601UTC");
        Date date = DateHelper.createDate(2018, 8, 7, 0, 0, 0);
        Date nextDay = DateHelper.addDays(date, 1);
        while (date.getTime() < nextDay.getTime()) {            
            String text = DateHelper.toISO8601UTC(date);
            assertEquals(23, text.length());
            Date fromString = DateHelper.fromISO8601UTC(text);
            assertEquals(date.getTime(), fromString.getTime());
            int ms = RandomHelper.getDefault().getInt(10, 200);
            date = DateHelper.addMilliseconds(date, ms);
        }        
    }

    /**
     * Test of fromISO8601UTC method, of class DateHelper.
     */
    @Test
    public void testFromISO8601UTC() throws Exception {
        System.out.println("fromISO8601UTC");

        // без мс
        String dateStr = "2018-02-20 20:40:59";
        Date expResult = DateHelper.createDate(2018, 2, 20, 23, 40, 59);
        Date result = DateHelper.fromISO8601UTC(dateStr);
        assertEquals(expResult, result);

        // с мс
        dateStr = "2018-02-20 20:40:59.987";
        expResult = DateHelper.createDate(2018, 2, 20, 23, 40, 59, 987);
        result = DateHelper.fromISO8601UTC(dateStr);
        assertEquals(expResult, result);
        
        // фиксим ошибку        
        dateStr = "2018-08-07 14:58:03.2018";
        expResult = DateHelper.createDate(2018, 8, 7, 17, 58, 3);
        result = DateHelper.fromISO8601UTC(dateStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of trimSeconds method, of class DateHelper.
     */
    @Test
    public void testtrimMilliseconds() throws ParseException {
        System.out.println("trimSeconds");
        Date date = new Date(43235);
        Date expResult = new Date(43000);
        Date result = DateHelper.trimMilliseconds(date);
        assertEquals(expResult, result);

        date = new Date();
        result = DateHelper.trimMilliseconds(date);
        int mills = (int) (result.getTime() % 1000);
        assertEquals(0, mills);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date a = format.parse("2018-02-21 20:45:50.758");
        Date b = DateHelper.trimMilliseconds(a);
        String result2 = format.format(b);
        assertEquals("2018-02-21 20:45:50.000", result2);
    }

    /**
     * Test of trimTime method, of class DateHelper.
     */
    @Test
    public void testTrimTime() {
        System.out.println("trimTime");
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 1, 20, 23, 40, 59);
        cal.set(Calendar.MILLISECOND, 400);
        Date date1 = cal.getTime();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date expResult = cal.getTime();
        Date result = DateHelper.trimTime(date1);
        assertEquals(expResult, result);
    }

    /**
     * Test of addDays method, of class DateHelper.
     */
    @Test
    public void testAddDays() {
        System.out.println("addDays");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 1, 20, 23, 40, 59);
        cal.set(Calendar.MILLISECOND, 400);
        Date date = cal.getTime();

        // проверим формат
        String result = format.format(date);
        assertEquals("2018-02-20 23:40:59.400", result);

        // - 2 дня
        result = format.format(DateHelper.addDays(date, -2));
        assertEquals("2018-02-18 23:40:59.400", result);

        // - 1 день
        result = format.format(DateHelper.addDays(date, -1));
        assertEquals("2018-02-19 23:40:59.400", result);

        // 0 день
        result = format.format(DateHelper.addDays(date, 0));
        assertEquals("2018-02-20 23:40:59.400", result);

        // + 1 день
        result = format.format(DateHelper.addDays(date, 1));
        assertEquals("2018-02-21 23:40:59.400", result);

        // + 2 день
        result = format.format(DateHelper.addDays(date, 2));
        assertEquals("2018-02-22 23:40:59.400", result);

        // + 8 дней
        result = format.format(DateHelper.addDays(date, 8));
        assertEquals("2018-02-28 23:40:59.400", result);

        // в февраля 28 дней, потом новый месяц
        // + 9 дней
        result = format.format(DateHelper.addDays(date, 9));
        assertEquals("2018-03-01 23:40:59.400", result);
    }

    /**
     * Test of addSeconds method, of class DateHelper.
     */
    @Test
    public void testAddSeconds() {
        System.out.println("addSeconds");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 1, 20, 23, 59, 58);
        cal.set(Calendar.MILLISECOND, 999);
        Date date = cal.getTime();

        // проверим формат
        String result = format.format(date);
        assertEquals("2018-02-20 23:59:58.999", result);

        // + 1 секунда
        result = format.format(DateHelper.addSeconds(date, 1));
        assertEquals("2018-02-20 23:59:59.999", result);

        // + 2 секунды
        result = format.format(DateHelper.addSeconds(date, 2));
        assertEquals("2018-02-21 00:00:00.999", result);
    }

    /**
     * Test of sameDay method, of class DateHelper.
     */
    @Test
    public void testSameDay() throws ParseException {
        System.out.println("sameDay");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // одинаковая дата
        Date a = format.parse("2018-02-21 00:00:00.000");
        Date b = format.parse("2018-02-21 00:00:00.001");
        assertTrue(DateHelper.sameDay(a, b));

        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2018-02-21 00:00:10.000");
        assertTrue(DateHelper.sameDay(a, b));

        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2018-02-21 00:10:00.000");
        assertTrue(DateHelper.sameDay(a, b));

        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2018-02-21 10:00:00.000");
        assertTrue(DateHelper.sameDay(a, b));

        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2018-02-21 23:59:59.999");
        assertTrue(DateHelper.sameDay(a, b));

        // разные даты
        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2018-02-22 00:00:00.000");
        assertFalse(DateHelper.sameDay(a, b));

        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2018-03-21 00:00:00.000");
        assertFalse(DateHelper.sameDay(a, b));

        a = format.parse("2018-02-21 00:00:00.000");
        b = format.parse("2019-02-21 00:00:00.000");
        assertFalse(DateHelper.sameDay(a, b));
    }
    
    /**
     * Была ошибка, когда в сессиях LastUpdateTime была другой даты, чем StartTime
     * Хотя перед обновлением, выполняется проверка SameDay
     * Поэтому на всякий случай проверим весь диапазон
     * 
     * Детали ошибки:
     * Режим - СТОП
     * CREATE TIME (UTC): 2018-08-05 21:37:34.260
     * START TIME (UTC):  2018-08-05 21:00:00.000
     * LAST UPDATE (UTC): 2018-08-06 10:28:10.000
     */
    @Test
    public void testSameDay_allRange() throws ParseException {
        System.out.println("sameDay_allRange");

        Date data = DateHelper.createDate(2018, 8, 5, 0, 0, 0);
        Date current = data;
        Date nextDay = DateHelper.addDays(data, 1);
        while (current.getTime() < nextDay.getTime()) {            
            boolean same = DateHelper.sameDay(data, current);
            assertTrue(same);
            int ms = RandomHelper.getDefault().getInt(10, 100);
            current = DateHelper.addMilliseconds(current, ms);
        }
        assertFalse(DateHelper.sameDay(data, current));        
    }
    
    /**
     * Была ошибка, когда в сессиях LastUpdateTime была другой даты, чем StartTime
     * Хотя перед обновлением, выполняется проверка SameDay
     * Поэтому на всякий случай проверим весь диапазон
     * 
     * Детали ошибки:
     * Режим - СТОП
     * CREATE TIME (UTC): 2018-08-05 21:37:34.260
     * START TIME (UTC):  2018-08-05 21:00:00.000
     * LAST UPDATE (UTC): 2018-08-06 10:28:10.000
     */
    @Test
    public void testSameDay_allRangeTextCheck() throws ParseException {
        System.out.println("sameDay_allRange");

        Date date = new Date();
        for (int i = 0; i < 1000; i++) {
            int seconds = RandomHelper.getDefault().getInt(10, 60*60*24);
            Date old = date;
            date = DateHelper.addSeconds(date, seconds);
            boolean same1 = DateHelper.sameDay(old, date);
            String d1 = DateHelper.getRussianDateTimeString(old).substring(0,10);
            String d2 = DateHelper.getRussianDateTimeString(date).substring(0,10);
            boolean same2 = d1.equals(d2);
            assertEquals(same1, same2);
        }        
    }

    @Test
    public void testCreateDate() {
        Date date = DateHelper.createDate(2018, 6, 16, 23, 55, 59);
        String text = DateHelper.toISO8601UTC(date);
        assertEquals("2018-06-16 20:55:59.000", text);
    }

    @Test
    public void testCreateCalendarFromDate() {
        Date date = DateHelper.createDate(2018, 12, 31, 23, 59, 59);
        Calendar calendar = DateHelper.createCalendar(date);
        Date newDate = calendar.getTime();
        assertTrue(newDate.compareTo(date) == 0);
    }
    
    @Test
    public void testToISO8601UTC_multithread() throws InterruptedException {
        System.out.println("toISO8601UTC");
        
        int count = 1000 * 1000 * 3;
        Thread t1 = new Thread(()->{
            for(int i=0;i<count;i++){
                Date date = DateHelper.createDate(2018, 2, 20, 23, 40, 59);
                String expResult = "2018-02-20 20:40:59.000";
                String result = DateHelper.toISO8601UTC(date);
                assertEquals(expResult, result);
            }
        });
        t1.start();
        
        Thread t2 = new Thread(()->{
            for(int i=0;i<count;i++){
                Date date = DateHelper.createDate(2019, 1, 24, 21, 33, 48, 123);
                String expResult = "2019-01-24 18:33:48.123";
                String result = DateHelper.toISO8601UTC(date);
                assertEquals(expResult, result);
            }
        });
        t2.start();        
        t2.join();        
    }
}
