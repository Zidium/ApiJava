package zidium.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class DateHelper {    
 
    private static SimpleDateFormat getIso8601UtcSecondsFormat(){
        // UTC
        TimeZone tz = TimeZone.getTimeZone("UTC"); 
        
        // формат с секундами
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(tz);
        return df;
    }
    
    private static SimpleDateFormat getIso8601UtcMillisecondsFormat(){
        // UTC
        TimeZone tz = TimeZone.getTimeZone("UTC");
        
        // формат с миллисекундами
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(tz);
        return df;
    }
    
    private static SimpleDateFormat getIso8601MillisecondsFormat(){
        // формат с мс
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    private static SimpleDateFormat getIso8601SecondsFormat(){
        // формат с секундами
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    public static String getRussianDateTimeString(Date date){
        if (date == null)
            return "null";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    
    public static String getRussianDateString(Date date){
        if (date == null)
            return "null";
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
            
    
    /**
     * Переводит дату в строку ISO8601UTC (для sqlite)
     * @param date
     * @return
     */
    public static String toISO8601UTC(Date date) {
        if (date == null)
            return null;
        
        return getIso8601UtcMillisecondsFormat().format(date);
    }
    
   
    /**
     * Получает дату из строки ISO8601UTC (из sqlite)
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date fromISO8601UTC(String dateStr) throws ParseException {
        if (dateStr == null)
            return null;
        
        if (dateStr.length()==19){
            return getIso8601UtcSecondsFormat().parse(dateStr);
        }
        if (dateStr.length()==23){
            return getIso8601UtcMillisecondsFormat().parse(dateStr);
        }
        
        // попытка исправить ошибку: error ISO8601UTC format length: 2018-08-07 14:58:03.2018
        if (dateStr.length() > 19){
            dateStr = dateStr.substring(0, 19); 
            return getIso8601UtcSecondsFormat().parse(dateStr);            
        }
        throw new ParseException("error ISO8601UTC format length: " + dateStr, 0);
    }
    
    /**
     * Переводит дату в строку ISO8601 (для sqlite)
     * @param date
     * @return
     */
    public static String toISO8601(Date date) {
        if (date == null)
            return null;
        
        return getIso8601MillisecondsFormat().format(date);
    }
    
   
    /**
     * Получает дату из строки ISO8601 (из sqlite)
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date fromISO8601(String dateStr) throws ParseException {
        if (dateStr == null)
            return null;
        
        if (dateStr.length()==19){
            return getIso8601SecondsFormat().parse(dateStr);
        }
        if (dateStr.length()==23){
            return getIso8601MillisecondsFormat().parse(dateStr);
        }
        throw new ParseException("error ISO8601 format length: " + dateStr, 0);
    }
    
    public static String getTimeString(Date date){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(date);
    }
    
    public static boolean between(Date date, Date from, Date to){
        if (date.getTime() < from.getTime()){
            return false;
        }
        if (date.getTime() > to.getTime()){
            return false;
        }
        return true;
    }
    
    public static Date trimMilliseconds(Date date){
        long time = date.getTime();
        return new Date((time / 1000) * 1000);
    }
    
    public static Date trimTime(Date date){
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Date addDays(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
    
    public static Date addHours(Date date, int hours){
        long mills = date.getTime() + hours * 60 * 60 * 1000;
        return new Date(mills);
    }
    
    public static Date addMinutes(Date date, int minutes){
        long mills = date.getTime() + minutes * 60 * 1000;
        return new Date(mills);
    }
    
    public static Date addSeconds(Date date, int seconds){
        long mills = date.getTime() + seconds * 1000;
        return new Date(mills);
    }
    
    public static Date addMilliseconds(Date date, long milliseconds){
        long mills = date.getTime() + milliseconds;
        return new Date(mills);
    }
    
    public static Date add(Date date, Duration duration){
        long ms = duration.toMillis();
        return addMilliseconds(date, ms);
    }
    
    public static boolean sameDay(Date a, Date b){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(a);
        cal2.setTime(b);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                          cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) &&
                          cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }
    
    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    
    public static Date createDate(int year, int month, int day, int hours, int minutes, int seconds){
       return createDate(year, month, day, hours, minutes, seconds, 0);
    }
    
    public static Date createDate(int year, int month, int day, int hours, int minutes, int seconds, int ms){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        calendar.set(year, month - 1, day, hours, minutes, seconds); 
        calendar.set(Calendar.MILLISECOND, ms);
        return calendar.getTime();
    }
    
    public static Calendar createCalendar(int year, int month, int day, int hours, int minutes, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        calendar.set(year, month - 1, day, hours, minutes, seconds); 
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    
    public static Calendar createCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
    public static XMLGregorianCalendar createXmlCalendar(int year, int month, int day, int hours, int minutes, int seconds) throws DatatypeConfigurationException{
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        calendar.set(year, month - 1, day, hours, minutes, seconds); 
        calendar.set(Calendar.MILLISECOND, 0);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }
    
    public static XMLGregorianCalendar createXmlCalendar(Date date) throws DatatypeConfigurationException{
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
    
    public static boolean CompareDatePart(Date date1, Date date2){
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.equals(localDate2);
    }
}
