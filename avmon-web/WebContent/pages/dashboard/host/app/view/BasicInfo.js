Ext.define('MyApp.view.BasicInfo', {
    extend: 'Ext.form.Panel',
    alias: 'widget.basicInfo',
    height: 290,
    id: 'basicInfo',
    width: 260,
    bodyPadding: 10,
    title: avmon.dashboard.generalInfomation,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.config.system,
                    labelWidth: 70,
                    name: 'OS',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.batchdeploy.version,
                    labelWidth: 70,
                    name: 'OSVERSION',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.alarm.operationSystem,
                    labelWidth: 70,
                    name: 'BUSINESSSYSTEM',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.alarm.position,
                    labelWidth: 70,
                    name: 'POSITION',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.alarm.usage,
                    labelWidth: 70,
                    name: 'USAGE',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.alarm.administrator,
                    labelWidth: 70,
                    name: 'OWNER',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.dashboard.cpuModel,
                    labelWidth: 70,
                    name: 'CPUMODEL',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.dashboard.operateFrequency,
                    labelWidth: 70,
                    name: 'RUNRATE',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.dashboard.operateTime,
                    labelWidth: 70,
                    name: 'RUNTIME',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.dashboard.currentUsers,
                    labelWidth: 70,
                    name: 'USERCOUNT',
                    value: 'loading...'
                },
                {
                    xtype: 'displayfield',
                    anchor: '100%',
                    fieldLabel: avmon.dashboard.currentAlarmNumber,
                    labelWidth: 70,
                    name: 'ALARMCOUNT',
                    value: 'loading...'
                }
            ]
        });
        me.callParent(arguments);
    }
});