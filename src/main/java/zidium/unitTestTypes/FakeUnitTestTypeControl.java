package zidium.unitTestTypes;

public class FakeUnitTestTypeControl implements IUnitTestTypeControl {

    @Override
    public boolean isFake() {
        return true;
    }

    @Override
    public String getId() {
        return "";
    }

}
