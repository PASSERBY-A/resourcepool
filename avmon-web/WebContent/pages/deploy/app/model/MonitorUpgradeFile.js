Ext.define('app.model.MonitorUpgradeFile', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'os'
        },
        {
            name: 'osVersion'
        },
        {
            name: 'agentFile'
        },
        {
            name: 'agentVersion'
        }
    ]
});