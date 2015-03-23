Ext.define('SYS.view.PortalLicense', {
	extend : 'Ext.form.Panel',
	alias : 'widget.PortalLicense',
	title : avmon.system.licenseMessage,
	bodyPadding: 10,
	initComponent : function() {
		var me = this;

		Ext.apply(this, {
			items: [
                {
                    xtype: 'textfield',
                    id:'licenseDate',
                    name:'licneseDate',
                    anchor: '100%',
                    disabled: true,
                    fieldLabel: avmon.system.expiryDate
                },
                {
                    xtype: 'textfield',
                    anchor: '100%',
                    id:'licenseMax',
                    name:'licenseMax',
                    disabled: true,
                    fieldLabel: avmon.system.maxMonitorNumber
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onFormAfterRender,
                    scope: me
                }
            }
		});
		this.callParent(arguments);
	},

    onFormAfterRender: function(abstractcomponent, options) {
		abstractcomponent.load({
			waitTitle:avmon.system.waiting,
			waitMsg:avmon.system.loadingFormData,
			url : 'loadLicense',
			success : function(form, action) {
				
			},
			failure : function(form, action) {
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);
			}
		}); 
    }
});