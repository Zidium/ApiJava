# Zidium Api for Java
��� ����������� Api �� Java ��� ������� ����������� Zidium

## �����������
�������� ������ � ��� pom.xml:

<dependency>
    <groupId>net.zidium</groupId>
    <artifactId>apijava</artifactId>
    <version>1.0.3</version>
</dependency>

����������� ����� ����� ��������� ������.

## ���������
��� �������� �������� ����������� ������� ���� zidium.properties � ����� � ����������� jar-������.

� ����� zidium.properties ������� �������� ������ �������� � ��������� ���� �� ��:

account=MYACCOUNT
secretKey=7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F

���� �� ����������� ���������� � ��� Zidium, � �� �������� ������, �� ������� ����� ����� ������ Api:

url=http://localhost:61000/

## �������������

### ��������� ���������� �������

��������� ������� � ��������� �������� �� zidium.properties:

IZidiumClient client = ZidiumClient.getDefault();

��������� ������� � ����� ��������� ��������:

IZidiumClient client = new ZidiumClient("MYACCOUNT", "7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F", "http://localhost:61000/");

��������� ������� � ��������� �������� �� ���������� �����:

IZidiumClient client = ZidiumClient.loadFromConfig("zidium.properties");

### ��������� ����������

������� �������� �������� ���������:

IComponentControl root = client.getRootComponentControl();

� �� �������� �������� ���������:

IComponentControl component = root.getOrCreateChild("MyComponent");

����� ��������� ���������� ������ ������ �����������:

IComponentControl childComponent = component.getOrCreateChild("MyChildComponent");

������������ ����� ���������� ����������� � ������ ��������.

����� ����� �������� ��������� �� Id, ���� �� �������� �������:

IComponentControl component = client.getComponentControl("...");

### �������� ��������

�������� ��������� ��������:

IUnitTestControl unitTest = component.getOrCreateUnitTest("��������", "��� ��������");

��������� ���������:

unitTest.SendResult(UnitTestResult.Success);

### �������� �������

component.sendMetric("�������", 100);

### �������� �������

�������� ������� � �������:

client.addError("����� ������", exception);

�������� ������������� �������:

ZidiumEvent eventData = new ZidiumEvent();
eventData.setMessage("����� �������");
eventData.setTypeDisplayName("��� �������");
eventData.setImportance(EventImportance.SUCCESS);
component.addEvent(eventData);

### ������ � ���

ILog log = component.getLog();
log.info("����� ����");

## ��������������� ������

��� ������ ����������� NetBeans IDE 8.2 ��� ����.
������ ���������� Maven ������ 3.0.5.

### ���������� ����-������

����� �������� ������ ����� � ����� src\test\resources\zidium.properties ������� ��������� ������ ��������� ��������.