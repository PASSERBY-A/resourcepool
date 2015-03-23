/*
 * File: app/store/HostTreeStore.js
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

Ext.define('MyApp.store.HostTreeStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'MyApp.model.HostTreeNode'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'MyJsonTreeStore',
            model: 'MyApp.model.HostTreeNode',
            nodeParam: 'id',
            root: {
                expanded: true,
                text: avmon.alarm.facilityInformation,
                id: 'root'
            },
            proxy: {
                type: 'ajax',
                url: '../performance/menuTree?checkBox=true',
                reader: {
                    type: 'json',
                    idProperty: 'id'
                }
            }
        }, cfg)]);
    }
});