Ext.define('MyApp.model.Network', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'NAME'
        },
        {
            name: 'type'
        },
        {
            name: 'send'
        },
        {
            name: 'recv'
        },
        {
            name: 'usage'
        },
        {
            name: 'LAN_OPKTS'
        },
        {
            name: 'LAN_IPKTS'
        }
    ]
});