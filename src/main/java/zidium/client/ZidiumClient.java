package zidium.client;

import zidium.webServices.IZidiumWebService;
import zidium.webServices.IZidiumTransport;
import zidium.webServices.ZidiumTransport;
import zidium.webServices.ZidiumWebService;
import zidium.events.IExceptionToEventConverter;
import zidium.events.IEventManager;
import zidium.components.IComponentControl;
import zidium.events.EventManager;
import zidium.components.FakeComponentControl;
import zidium.components.ComponentControlWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zidium.components.GetOrCreateChildComponentIdProvider;
import zidium.helpers.PropertiesHelper;
import zidium.components.GetOrCreateRootComponentIdProvider;
import zidium.components.IComponentIdProvider;
import zidium.components.ValidateComponentIdProvider;
import zidium.dto.Token;
import zidium.dto.getOrAddComponent.GetOrAddComponentRequestData;
import zidium.events.ExceptionToEventConverter;
import zidium.logs.ILogManager;
import zidium.unitTestTypes.GetOrCreateUnitTestTypeIdProvider;
import zidium.unitTestTypes.IUnitTestTypeControl;
import zidium.unitTestTypes.IUnitTestTypeIdProvider;
import zidium.unitTestTypes.UnitTestTypeControlWrapper;

public class ZidiumClient implements IZidiumClient {

    private IZidiumTransport _transport;
    private IEventManager _eventManager;
    private ILogManager _logManager;
    private IComponentControl _defaultComponentControl;
    private IExceptionToEventConverter _exceptionToEventConverter;
    private ITimeService _timeService;

    private static final Logger _logger = LogManager.getLogger(ZidiumClient.class);
    private static IZidiumClient _defaultClient;
    private IComponentControl _rootComponentControl;

    public ZidiumClient(String account, String secretKey, String apiUrl) {
        String url;
        
        if (apiUrl != null) {
            url = apiUrl;
        } else {
            url = "https://" + account + ".api.zidium.net/1.0/";
        }
        
        IZidiumWebService webService = new ZidiumWebService(url);
        Token token = new Token();
        token.Account = account;
        token.SecretKey = secretKey;
        _transport = new ZidiumTransport(token, webService);
        _eventManager = new EventManager(this);
        _logManager = new zidium.logs.LogManager(this);
        _defaultComponentControl = new FakeComponentControl();
        _timeService = new TimeService(_transport);
        _exceptionToEventConverter = new ExceptionToEventConverter(this);
    }

    public static IZidiumClient getDefault() {
        if (_defaultClient == null) {
            _defaultClient = loadFromConfig("zidium.properties");
        }
        return _defaultClient;
    }

    public static IZidiumClient loadFromConfig(String propertiesFile) {
        try {
            PropertiesHelper properties = PropertiesHelper.fromFile(propertiesFile);
            String account = properties.getRequiredString("account");
            String secretKey = properties.getRequiredString("secretKey");
            String url = properties.getString("url");
            return new ZidiumClient(account, secretKey, url);
        } catch (Exception ex) {
            return new ZidiumClient("unknown", "unknown", null);
        }
    }

    public static void setDefault(IZidiumClient client) {
        _defaultClient = client;
    }

    @Override
    public IComponentControl getDefaultComponentControl() {
        return _defaultComponentControl;
    }

    @Override
    public void setDefaultComponentControl(IComponentControl componentControl) {
        _defaultComponentControl = componentControl;
    }

    @Override
    public IEventManager getEventManager() {
        return _eventManager;
    }

    @Override
    public IZidiumTransport getTransport() {
        return _transport;
    }

    @Override
    public IExceptionToEventConverter getExceptionToEventConverter() {
        return _exceptionToEventConverter;
    }

    @Override
    public void setExceptionToEventConverter(IExceptionToEventConverter converter) {
        _exceptionToEventConverter = converter;
    }

    @Override
    public IComponentControl getComponentControl(IComponentIdProvider provider) {
        String componentId = null;
        return new ComponentControlWrapper(this, provider, componentId);
    }

    @Override
    public IComponentControl getComponentControl(String id) {
        IComponentIdProvider provider = new ValidateComponentIdProvider(this, id);
        return new ComponentControlWrapper(this, provider, id);
    }

    @Override
    public ITimeService getTimeService() {
        return _timeService;
    }

    @Override
    public ILogManager getLogManager() {
        return _logManager;
    }

    @Override
    public IComponentControl getRootComponentControl() {
        if (_rootComponentControl == null) {
            IComponentIdProvider provider = new GetOrCreateRootComponentIdProvider(this);
            _rootComponentControl = getComponentControl(provider);
        }
        return _rootComponentControl;
    }
    
    @Override
    public IComponentControl getOrCreateComponentControl(GetOrAddComponentRequestData data) {
        GetOrCreateChildComponentIdProvider provider = new GetOrCreateChildComponentIdProvider(this, data);
        return getComponentControl(provider);
    }

    @Override
    public IComponentControl setDefaultComponentControl(IComponentIdProvider provider) {
        IComponentControl componentControl = getComponentControl(provider);
        setDefaultComponentControl(componentControl);
        return componentControl;
    }

    @Override
    public IUnitTestTypeControl getUnitTestType(IUnitTestTypeIdProvider provider) {
        return new UnitTestTypeControlWrapper(this, provider);
    }

    @Override
    public IUnitTestTypeControl getOrCreateUnitTestType(String name) {
        return getUnitTestType(new GetOrCreateUnitTestTypeIdProvider(this, name));
    }
    
}
