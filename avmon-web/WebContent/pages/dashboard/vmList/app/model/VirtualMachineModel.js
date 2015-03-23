Ext.define('vmList.model.VirtualMachineModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'name'
        },
        {
            name: 'status'
        },
        {
            name: 'host'
        },
        {
            name: 'usedSpace'
        },
        {
            name: 'hostFre'
        },
        {
            name: 'hostMem'
        },
        {
            name: 'clientSystem'
        },
        {
            name: 'mem'
        },
        {
            name: 'uuid'
        },
        {
            name: 'remark'
        },
        {
            name: 'alarmEnable'
        },
        {
            name: 'clientMemUsage'
        }
    ]
});