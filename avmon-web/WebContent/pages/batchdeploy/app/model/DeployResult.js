Ext.define('MyApp.model.DeployResult', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'moId'
        },
        {
            name: 'monitorInstanceId'
        },
        {
            name: 'monitor'
        },
        {
            name: 'msg'
        }
    ]
});