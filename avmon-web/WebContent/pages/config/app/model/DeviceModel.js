Ext.define('CFG.model.DeviceModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'device_name'
        },
        {
            name: 'device_type'
        },
        {
            name: 'device_ip'
        },
        {
            name: 'device_desc'
        },
        {
            name: 'report_date'
        }
    ]
});