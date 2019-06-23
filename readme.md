# Zidium Api for Java
Это официальное Api на Java для системы мониторинга Zidium

## Подключение
Добавьте раздел в ваш pom.xml:

    <dependency>
        <groupId>net.zidium</groupId>
        <artifactId>apijava</artifactId>
        <version>1.0.3</version>
    </dependency>

Используйте самую новую доступную версию.

## Настройка
Для хранения настроек рекомендуем создать файл zidium.properties в папке с выполняемым jar-файлом.

В файле zidium.properties укажите название вашего аккаунта и секретный ключ из ЛК:

    account=MYACCOUNT
    secretKey=7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F

Если вы используете развёрнутый у вас Zidium, а не облачный сервис, то укажите также адрес службы Api:

    url=http://localhost:61000/

## Использование

### Получение экземпляра клиента

Получение клиента с загрузкой настроек из zidium.properties:

    IZidiumClient client = ZidiumClient.getDefault();

Получение клиента с явным указанием настроек:

    IZidiumClient client = new ZidiumClient("MYACCOUNT", "7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F", "http://localhost:61000/");

Получение клиента с загрузкой настроек из указанного файла:

    IZidiumClient client = ZidiumClient.loadFromConfig("zidium.properties");

**Важно!**
Перед завершением приложения нужно выполнить запись всех закешированных данных:

    client.getEventManager().flush();
    client.getLogManager().flush();

### Получение компонента

Сначала получите корневой компонент:

    IComponentControl root = client.getRootComponentControl();

В нём создайте дочерний компонент:

    IComponentControl component = root.getOrCreateChild("MyComponent");

Можно создавать компоненты любого уровня вложенности:

    IComponentControl childComponent = component.getOrCreateChild("MyChildComponent");

Уникальность имени компонента проверяется в рамках родителя.

Можно также получить компонент по Id, если он известен заранее:

    IComponentControl component = client.getComponentControl("...");

### Отправка проверки

Получите экземпляр проверки:

    IUnitTestControl unitTest = component.getOrCreateUnitTest("Проверка", "Тип проверки");

Отправьте результат:

    unitTest.SendResult(UnitTestResult.Success);

### Отправка метрики

    component.sendMetric("Метрика", 100);

### Отправка события

Отправка события с ошибкой:

    client.addError("Текст ошибки", exception);

Отправка произвольного события:

    ZidiumEvent eventData = new ZidiumEvent();
    eventData.setMessage("Текст события");
    eventData.setTypeDisplayName("Тип события");
    eventData.setImportance(EventImportance.SUCCESS);
    component.addEvent(eventData);

### Запись в лог

    ILog log = component.getLog();
    log.info("Текст лога");

## Самостоятельная сборка

Для сборки потребуется NetBeans IDE 8.2 или выше.
Проект использует Maven версии 3.0.5.

### Выполнение юнит-тестов

Перед запуском тестов нужно в файле src\test\resources\zidium.properties указать параметры вашего тестового аккаунта.
