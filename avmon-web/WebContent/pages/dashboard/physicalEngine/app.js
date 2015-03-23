Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    models: [
        'ErrorLogModel',
        'BasicInfoModel',
        'ImageModel',
        'BucketModel',
        'Network',
        'MemModel',
        'CpuModel',
        'DiskModel',
        'NetworkSendModel',
        'NetworkReceiveModel',
        'NetworkViewModel',
        'DiskViewModel',
        'NetworkListModel',
        'DiskListModel',
        'NetworkInfoModel'
    ],
    stores: [
        'ErrorLogStore',
        'BasicInfoStore',
        'imagesStore',
        'NetworkStore',
        'MemStore',
        'CpuStore',
        'DiskStore',
        'NetworkSendStore',
        'NetworkReceiveStore',
        'MemReceiveStore',
        'NetworkViewStore',
        'MemSendStore',
        'DiskViewStore',
        'NetworkListStore',
        'DiskListStore',
        'NetworkInfoStore'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp',

    launch: function() {
		Ext.physicalEngine.application = this;
		Ext.physicalEngine.application.loadAll();
            },
    loadAll: function() {
            	Ext.physicalEngine.application = this;
                var moId=Ext.physicalEngine.moId;
                if (moId != null && moId != '') {
                    this.loadBasicInfo();
                    this.loadNetwork();
                    this.loadMem();
                    this.loadCpu();
                    this.loadDisk();
                    this.loadNetworkSend();
                    this.loadNetworkReceive();
                    this.loadDiskWrite();
                    this.loadDiskRead();
                    // 定制定时刷新
                    setInterval(this.refresh_10s, 600000);
                }
    },
    loadErrorLog: function() {},
    loadCourse: function() {
        if (Ext.physicalEngine.moId != null && Ext.physicalEngine.moId != '') {
            Ext.Ajax.request({
                url: 'courseUse',
                params: {mo:Ext.physicalEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("courseChart").store.loadData(obj,false);
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
        }
    },
    loadMem: function() {
        if (Ext.physicalEngine.moId != null && Ext.physicalEngine.moId != '') {
            Ext.Ajax.request({
                url: 'memUse',
                params: {mo:Ext.physicalEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    var mem_user = obj[0].MEM_USR;
                    var level = obj[0].MEM_LEVEL;
                    //Ext.getCmp("memChart").store.loadData(obj,false);
                    Ext.getCmp('memPanel').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../physicalEngine/littleColumn.jsp?kpi='+mem_user+'&level='+level+'"></iframe>');
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
         // 统计信息
            Ext.Ajax.request({
                url: 'memBasic',
                params: {mo:Ext.physicalEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("memForm").getForm().setValues(obj);
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            }); 
        }
    },
    loadCpu: function() {
        if (Ext.physicalEngine.moId != null && Ext.physicalEngine.moId != '') {
            Ext.Ajax.request({
                url: 'cpuUse',
                params: {mo:Ext.physicalEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    var cpu_user = obj[0].CPU_USR;
                    var cpu_level = obj[0].CPU_LEVEL;
                    //Ext.getCmp("cpuChart").store.loadData(obj,false);
                    Ext.getCmp('gaugePanel').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../physicalEngine/gauge.jsp?cpu_user='+cpu_user+'&level='+cpu_level+'"></iframe>');
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
        }
    },
    loadDisk: function() {
        if (Ext.physicalEngine.moId != null && Ext.physicalEngine.moId != '') {
            var activeGrid = Ext.getCmp('bucketMemData');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.moId = Ext.physicalEngine.moId;
            activeGrid.getStore().load();
        }
    },

    loadAlarm: function() {},

    refresh_10s: function() {

    },

    loadBasicInfo: function() {
        var moId = Ext.physicalEngine.moId;
        if (moId != null && moId != '') {
            var activeGrid = Ext.getCmp('basicInfoGrid');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.treeId = Ext.physicalEngine.moId;
            activeGrid.getStore().load();
            Ext.getCmp("hostImage").setSrc('images/SYS_All.png');
            Ext.Ajax.request({
                url: 'basicInfo',
                params: {mo:moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("hostImage").setSrc('images/'+obj.hostImage);
                },
                failure: function(response, opts) {
                }
            });
        }
    },

    loadNetwork: function() {
        var moId = Ext.physicalEngine.moId;

        if (moId != null && moId != '') {
        	var activeGrid = Ext.getCmp('networkGrid');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.treeId = Ext.physicalEngine.moId;
            activeGrid.getStore().load();
        }
    },
    loadNetworkSend: function() {
        Ext.Ajax.request({
            url: 'networkSend',
            params: {mo:Ext.physicalEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("networkLineSend").store.loadData(obj.history,false);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("networkSendPic").setSrc('images/arrow_G_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("networkSendPic").setSrc('images/arrow_G_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("networkSendPic").setSrc('images/arrow_G_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("networkSendForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadNetworkReceive: function() {
        Ext.Ajax.request({
            url: 'networkReceive',
            params: {mo:Ext.physicalEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("networkLineReceive").store.loadData(obj.history,false);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("netwrokReceivePic").setSrc('images/arrow_R_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("netwrokReceivePic").setSrc('images/arrow_R_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("netwrokReceivePic").setSrc('images/arrow_R_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("networkReceiveForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadDiskWrite: function() {
        Ext.Ajax.request({
            url: 'diskWrite',
            params: {mo:Ext.physicalEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("diskWriteChart").store.loadData(obj.history,false);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("diskWritePic").setSrc('images/arrow_G_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("diskWritePic").setSrc('images/arrow_G_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("diskWritePic").setSrc('images/arrow_G_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("diskWriteForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadDiskRead: function() {
        Ext.Ajax.request({
            url: 'diskRead',
            params: {mo:Ext.physicalEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("diskReadChart").store.loadData(obj.history,false);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("diskReadPic").setSrc('images/arrow_R_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("diskReadPic").setSrc('images/arrow_R_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("diskReadPic").setSrc('images/arrow_R_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("diskReadForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadNetworkList: function() {
        var activeGrid = Ext.getCmp('networkListGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeGrid.getStore().load();
        var engineListGrid = Ext.getCmp('engineListGrid');
        var engineListGridProxy = engineListGrid.getStore().getProxy();
        engineListGrid.getStore().load();
    },
    loadDiskList: function() {
        var activeGrid = Ext.getCmp('DiskListGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeGrid.getStore().load();
    }
});
