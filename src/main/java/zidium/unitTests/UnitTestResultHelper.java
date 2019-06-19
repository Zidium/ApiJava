package zidium.unitTests;


public class UnitTestResultHelper {
    
    public static String toString(UnitTestResult result){
        if (result == UnitTestResult.Alarm){
            return "Alarm";
        }
        if (result == UnitTestResult.Warning){
            return "Warning";
        }
        if (result == UnitTestResult.Success){
            return "Success";
        }
        if (result == UnitTestResult.Unknown){
            return "Unknown";
        }
        return "Unknown";
    }    
}
