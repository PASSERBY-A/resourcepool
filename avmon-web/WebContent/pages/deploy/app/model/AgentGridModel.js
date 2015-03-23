/*
 * File: app/model/AgentGridModel.js
 *
 * This file was generated by Sencha Architect version 2.1.0.
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

Ext.define('app.model.AgentGridModel', {
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
            name: 'ampCount'
        },
        {
            name: 'ampCount1'
        }
    ]
});