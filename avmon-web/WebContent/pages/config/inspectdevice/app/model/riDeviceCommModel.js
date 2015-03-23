Ext.define('MyApp.model.riDeviceCommModel', {
    extend: 'Ext.data.Model',
    idProperty: 'TEMP_ID',
    fields: [
        {
            name: 'DEVICE_IP'
        },
        {
            name: 'COMM_CODE'
        },
        {
            name: 'COMM_VALUE'
        },
        {
            name: 'USR'
        },
        {
            name: 'PWD'
        },
        {
            name: 'QUIT_MODE1'
        },
        {
            name: 'QUIT_MODE2'
        },
        {
            name: 'TEMP_ID'
        }
    ]
});