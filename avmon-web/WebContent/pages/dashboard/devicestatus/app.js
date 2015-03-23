Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    models: [
        'MainEngineModel'
    ],
    stores: [
        'MainEngineStore'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp',
    launch: function() {
        Ext.devicestatus.application = this;
        // 初次加载传值
        var deviceList = Ext.getCmp('deviceList');
        var deviceStoreProxy = deviceList.getStore().getProxy();
        deviceStoreProxy.extraParams.mo = Ext.devicestatus.mo;
        deviceStoreProxy.extraParams.biz = Ext.devicestatus.biz;
        deviceStoreProxy.extraParams.condition = '4';
        Ext.devicestatus.condition = '4';
        deviceList.getStore().load();
        // 定时刷新页面
        setInterval(this.updateDeviceStatus, 50000);
    },
    updateDeviceStatus: function() {
        // 判断当前活动页面是否为设备状态页面
        if(window.parent.isShow("performance.deviceStatusPanel")){
            var deviceList = Ext.getCmp('deviceList');
            var deviceStoreProxy = deviceList.getStore().getProxy();
            deviceStoreProxy.extraParams.mo = Ext.devicestatus.mo;
            deviceStoreProxy.extraParams.condition = Ext.devicestatus.condition;
            deviceList.getStore().load();
        }
    },
    refreshByLoad: function() {
        var deviceList = Ext.getCmp('deviceList');
        var deviceStoreProxy = deviceList.getStore().getProxy();
        deviceStoreProxy.extraParams.mo = Ext.devicestatus.mo;
        deviceStoreProxy.extraParams.condition = Ext.devicestatus.condition;
        deviceList.getStore().load();
    }
});