Ext.define('MyApp.model.MainEngineModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'MO_ID'
        },
        {
            name: 'MO_NAME'
        },
        {
            name: 'CPU'
        },
        {
            name: 'MEM'
        },
        {
            name: 'DISK'
        },
        {
            name: 'SYSLOG'
        },
        {
            name: 'STATUS'
        },
        {
            name: 'CURRENT_GRADE'
        },
        {
            name: 'CPU_LEVEL'
        },
        {
            name: 'MEM_LEVEL'
        },
        {
            name: 'DISK_LEVEL'
        }
    ]
});