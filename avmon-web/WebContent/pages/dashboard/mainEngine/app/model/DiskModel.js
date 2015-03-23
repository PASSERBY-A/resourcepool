Ext.define('MyApp.model.DiskModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'DISK_NAME'
        },
        {
            name: 'BUSY_RATE'
        },
        {
            name: 'RW_RATE'
        },
        {
            name: 'TRAN_BYTES'
        }
    ]
});