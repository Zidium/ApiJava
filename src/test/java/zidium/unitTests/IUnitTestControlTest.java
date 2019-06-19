package zidium.unitTests;

import org.junit.Test;
import static org.junit.Assert.*;
import zidium.client.IZidiumClient;
import zidium.client.ZidiumTestHelper;
import zidium.components.IComponentControl;
import zidium.dto.sendUnitTestResult.SendUnitTestResultResponse;
import zidium.unitTestTypes.FakeUnitTestTypeControl;
import zidium.unitTestTypes.GetOrCreateUnitTestTypeIdProvider;
import zidium.unitTestTypes.IUnitTestTypeControl;

public class IUnitTestControlTest {
    
    public IUnitTestControlTest() {
    }
    
    @Test
    public void testIsFake() {
        System.out.println("isFake");
        IZidiumClient client = ZidiumTestHelper.getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        assertFalse(component.isFake());
        
        // несуществующий (неверный тип)
        IUnitTestTypeControl type = new FakeUnitTestTypeControl();
        IUnitTestControl unitTest = component.getOrCreateUnitTest("test", type);
        assertTrue(unitTest.isFake());
        
        // нормальная проверка (должна успешно создаться)
        type = client.getUnitTestType(new GetOrCreateUnitTestTypeIdProvider(client, "test"));
        unitTest = component.getOrCreateUnitTest("test", type);
        assertFalse(unitTest.isFake());
    }

    @Test
    public void testSendResult_UnitTestResult() {
        System.out.println("SendResult");
        IZidiumClient client = ZidiumTestHelper.getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        assertFalse(component.isFake());
        
        // несуществующий (неверный тип)
        IUnitTestTypeControl type = new FakeUnitTestTypeControl();
        IUnitTestControl unitTest = component.getOrCreateUnitTest("test", type);
        SendUnitTestResultResponse response = unitTest.SendResult(UnitTestResult.Success);
        assertFalse(response.success());
        
        // нормальная проверка (должна успешно создаться)
        type = client.getOrCreateUnitTestType("testType");
        unitTest = component.getOrCreateUnitTest("test", type);
        response = unitTest.SendResult(UnitTestResult.Success);
        assertTrue(response.success());
    }
    
    @Test
    public void testSendResult_UnitTestResult_String() {
        System.out.println("SendResult");
        IZidiumClient client = ZidiumTestHelper.getTestClient();
        IComponentControl component = ZidiumTestHelper.getRandomComponent(client);
        assertFalse(component.isFake());
        
        // несуществующий (неверный тип)
        IUnitTestTypeControl type = new FakeUnitTestTypeControl();
        IUnitTestControl unitTest = component.getOrCreateUnitTest("test", type);
        SendUnitTestResultResponse response = unitTest.SendResult(UnitTestResult.Success, "ok");
        assertFalse(response.success());
        
        // нормальная проверка (должна успешно создаться)
        type = client.getOrCreateUnitTestType("testType");
        unitTest = component.getOrCreateUnitTest("test", type);
        response = unitTest.SendResult(UnitTestResult.Success, "ok");
        assertTrue(response.success());
    }    
}
