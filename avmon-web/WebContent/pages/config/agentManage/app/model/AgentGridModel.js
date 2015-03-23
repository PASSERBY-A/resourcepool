Ext.define('MyApp.model.AgentGridModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'agentId'
        },
        {
            name: 'ip'
        },
        {
            name: 'hostName'
        },
        {
            name: 'agentVersion'
        },
        {
            name: 'gateway'
        },
        {
            name: 'agentStatus'
        },
        {
            name: 'agentHostStatus'
        },
        {
            name: 'moId'
        },
        {
            name: 'osIcon'
        },
        {
            name: 'os'
        },
        {
            name: 'osVersion'
        },
        {
        		convert: function(v, rec) {
                    if(v==null || v==""){
                        return null;
                    }
                    else{
                        return new Date(v);
                    }

                },
                name: 'lastHeartbeatTime',
                type: 'date'
        },
        {
            convert: function(v, rec) {
                if(v==null || v==""){
                    return null;
                }
                else{
                    return new Date(v);
                }

            },
            name: 'lastUpdateTime',
            type: 'date'
        },
        {
            name: 'ampCount'
        },
        {
            name: 'ampCount1'
        },
        {
            name: 'agentCollectFlag'
        },
        {
            name: 'status'
        },
        {
          name: 'updateStatus'
        }
    ]
});