package zidium.logs;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import zidium.helpers.DateHelper;
import zidium.helpers.ExceptionHelper;
import zidium.helpers.GuidHelper;
import zidium.client.IZidiumClient;
import zidium.client.ZidiumTestHelper;
import zidium.components.IComponentControl;
import zidium.components.IComponentIdProvider;
import zidium.dto.ExtentionPropertyDto;
import zidium.dto.getLogs.GetLogsRequestData;
import zidium.dto.getLogs.GetLogsResponse;
import zidium.dto.getLogs.LogMessageDto;
import zidium.webServices.IZidiumTransport;

public class ILogTest {
    
    public ILogTest() {
    }
    
    private IZidiumClient getTestClient(){
        return ZidiumTestHelper.getTestClient();
    }
        
    @Test
    public void testIsFake() {
        System.out.println("isFake");
         
        // root
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertFalse(log.isFake());
        
        // invalid-id
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertTrue(log.isFake());
        
        // null
        component = client.getComponentControl(new IComponentIdProvider() {
            @Override
            public String getComponentId() {
                return null;
            }
        });
        log = component.getLog();
        assertTrue(log.isFake());
    }
    
    @Test
    public void testIsTraceEnabled() {
        System.out.println("isTraceEnabled");
        
        // online
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertFalse(log.isTraceEnabled()); // Trace is disabled by default
        
        // fake
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertFalse(log.isTraceEnabled());
    }

    /**
     * Test of isDebugEnabled method, of class ILog.
     */
    @Test
    public void testIsDebugEnabled() {
        System.out.println("isDebugEnabled");
        // online
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertFalse(log.isDebugEnabled()); // Debug is disabled by default
        
        // fake
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertFalse(log.isDebugEnabled());
    }
   
    @Test
    public void testIsInfoEnabled() {
        System.out.println("isInfoEnabled");
        // online
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertTrue(log.isInfoEnabled());
        
        // fake
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertFalse(log.isInfoEnabled());
    }

    @Test
    public void testIsWarningEnabled() {
        System.out.println("isWarningEnabled");
        // online
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertTrue(log.isWarningEnabled());
        
        // fake
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertFalse(log.isWarningEnabled());
    }
    
    @Test
    public void testIsErrorEnabled() {
        System.out.println("isErrorEnabled");
        // online
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertTrue(log.isErrorEnabled());
        
        // fake
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertFalse(log.isErrorEnabled());
    }
    
    @Test
    public void testIsFatalEnabled() {
        System.out.println("isFatalEnabled");
        // online
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertTrue(log.isFatalEnabled());
        
        // fake
        component = client.getComponentControl("invalid-id");
        log = component.getLog();
        assertFalse(log.isFatalEnabled());
    }
    
    private LogMessageDto validateLogExists(
            IZidiumClient client, 
            IComponentControl componentControl, 
            String message,
            String level) throws Exception{
        IZidiumTransport transport = client.getTransport();
        GetLogsRequestData filter = new GetLogsRequestData();
        filter.ComponentId = componentControl.getId();
        Date fromTime = DateHelper.addSeconds(new Date(), -60);
        filter.From = fromTime;
        filter.Levels = new String[]{level};
        GetLogsResponse response = transport.getLogs(filter);
        if (response.success()==false){
            throw new Exception("get logs response error");
        }
        for (LogMessageDto log : response.Data) {
            if (message.equals(log.Message)){
                return log;
            }
        }
        throw new Exception("log message not found");
    }

    
    @Test
    @Ignore("Trace is disabled by default")
    public void testTrace_String() throws Exception {
        System.out.println("trace");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        log.trace(message);
        client.getLogManager().flush();
        validateLogExists(client, component, message, LogLevel.TRACE);
    }
    
    private Exception getCatchedException(){
        try{
            int val = Integer.parseInt("dsd");
        }
        catch(Exception e){
            return e;
        }
        return null;
    }
    
    private void validateStackProperty(LogMessageDto logMessage, Exception exception) throws Exception{
        for (ExtentionPropertyDto property : logMessage.Properties) {
            if ("Stack".equals(property.Name)){
                String stack = ExceptionHelper.getStackTraceAsString(exception);
                assertEquals(stack, property.Value);
                return;
            }
        }
        throw new Exception("stack property not found");
    }
    
    @Test
    @Ignore("Trace is disabled by default")
    public void testTrace_String_Exception() throws Exception {
        System.out.println("trace");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        log.trace(message, exception);
        client.getLogManager().flush();
        LogMessageDto logMessage = validateLogExists(client, component, message, LogLevel.TRACE);
        validateStackProperty(logMessage, exception);
    }

    /**
     * Test of debug method, of class ILog.
     */
    @Test
    @Ignore("Debug is disabled by default")
    public void testDebug_String() throws Exception {
        System.out.println("debug");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        log.debug(message);
        client.getLogManager().flush();
        validateLogExists(client, component, message, LogLevel.DEBUG);
    }
    
    @Test
    @Ignore("Debug is disabled by default")
    public void testDebug_String_Exception() throws Exception {
        System.out.println("debug");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        log.debug(message, exception);
        client.getLogManager().flush();
        LogMessageDto logMessage = validateLogExists(client, component, message, LogLevel.DEBUG);
        validateStackProperty(logMessage, exception);
    }

    /**
     * Test of info method, of class ILog.
     */
    @Test
    public void testInfo_String() throws Exception {
        System.out.println("info");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        log.info(message);
        client.getLogManager().flush();
        validateLogExists(client, component, message, LogLevel.INFO);
    }

    
    @Test
    public void testInfo_String_Exception() throws Exception {
        System.out.println("info");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        log.info(message, exception);
        client.getLogManager().flush();
        LogMessageDto logMessage = validateLogExists(client, component, message, LogLevel.INFO);
        validateStackProperty(logMessage, exception);
    }

    @Test
    public void testWarning_String() throws Exception {
        System.out.println("warning");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        log.warning(message);
        client.getLogManager().flush();
        validateLogExists(client, component, message, LogLevel.WARNING);
    }

   
    @Test
    public void testWarning_String_Exception() throws Exception {
        System.out.println("warning");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        log.warning(message, exception);
        client.getLogManager().flush();
        LogMessageDto logMessage = validateLogExists(client, component, message, LogLevel.WARNING);
        validateStackProperty(logMessage, exception);
    }

    @Test
    public void testError_String() throws Exception {
        System.out.println("error");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        log.error(message);
        client.getLogManager().flush();
        validateLogExists(client, component, message, LogLevel.ERROR);
    }

    @Test
    public void testError_String_Exception() throws Exception {
        System.out.println("error");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        log.error(message, exception);
        client.getLogManager().flush();
        LogMessageDto logMessage = validateLogExists(client, component, message, LogLevel.ERROR);
        validateStackProperty(logMessage, exception);
    }

    
    @Test
    public void testFatal_String() throws Exception {
        System.out.println("fatal");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        log.fatal(message);
        client.getLogManager().flush();
        validateLogExists(client, component, message, LogLevel.FATAL);
    }

    @Test
    public void testFatal_String_Exception() throws Exception {
        System.out.println("fatal");
        IZidiumClient client = getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        ILog log = component.getLog();
        String message = "test message " + GuidHelper.getRandom();
        Exception exception = getCatchedException();
        log.fatal(message, exception);
        client.getLogManager().flush();
        LogMessageDto logMessage = validateLogExists(client, component, message, LogLevel.FATAL);
        validateStackProperty(logMessage, exception);
    }
    
    @Test
    public void testReloadConfig() throws Exception {
        System.out.println("reloadConfig");
        IZidiumClient client = getTestClient();
        IComponentControl component = client.getRootComponentControl();
        ILog log = component.getLog();
        assertFalse(log.isFake());
        assertTrue(log.reloadConfig());        
    }
}
