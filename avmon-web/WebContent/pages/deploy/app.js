/*
 * File: app.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.1.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.1.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({

    requires: [
        'app.view.ConfigPanel',
        'app.view.PropertyGrid',
        'app.view.AddMoWindow',
        'app.view.SelectMoTypeWindow'
    ],
    models: [
        'Monitor',
        'Kpi',
        'Property',
        'MonitorProperty',
        'MonitorType',
        'AgentDownload',
        'MoType',
        'MonitorUpgradeFile',
        'AgentGridModel',
        'AmpNormalScheduleModel',
        'HostPingModel'
    ],
    stores: [
        'Menus',
        'Monitors',
        'Kpis',
        'Properties',
        'MonitorProperties',
        'MonitorTypes',
        'HostTypeList',
        'AgentDownloads',
        'MoTypeTrees',
        'UpgradeMonitors',
        'MonitorUpgradeFiles',
        'AgentGridJsonStore',
        'AmpNormalScheduleStore',
        'HostPingStore'
    ],
    views: [
        'ConfigPanel',
        'PropertyGrid',
        'AddMoWindow',
        'SelectMoTypeWindow',
        'NormalAmpSetWindow'
    ],
    autoCreateViewport: true,
    controllers: [
        'MenuTree',
        'ConfigPanel'
    ],
    name: 'app'
});
