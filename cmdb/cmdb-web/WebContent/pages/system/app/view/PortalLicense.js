
Ext.define('SYS.view.PortalLicense', {
	extend : 'Ext.form.Panel',
	alias : 'widget.PortalLicense',
	title : "License信息",
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
                    fieldLabel: '到期日期'
                },
                {
                    xtype: 'textfield',
                    anchor: '100%',
                    id:'licenseMax',
                    name:'licenseMax',
                    disabled: true,
                    fieldLabel: '监控最大数量'
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
			waitTitle:"请稍候",
			waitMsg:"正在加载表单数据，请稍候...",
			url : 'loadLicense',
			success : function(form, action) {
				
			},
			failure : function(form, action) {
				Ext.MessageBox.hide();
				Ext.MessageBox.alert('提示', action.result.msg);
			}
		}); 
    }
});