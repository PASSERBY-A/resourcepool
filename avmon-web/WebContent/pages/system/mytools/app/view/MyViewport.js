Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    layout: {
        type: 'fit'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    frame: true,
                    layout: {
                        type: 'absolute'
                    },
                    title: avmon.system.widget,
                    items: [
                        {
                            xtype: 'textareafield',
                            x: 30,
                            y: 20,
                            height: 92,
                            itemId: 'regText',
                            width: 780,
                            fieldLabel: avmon.system.regex,
                            name: 'regText'
                        },
                        {
                            xtype: 'textareafield',
                            x: 30,
                            y: 140,
                            height: 92,
                            itemId: 'targetText',
                            width: 780,
                            fieldLabel: avmon.system.targetString,
                            name: 'targetText'
                        },
                        {
                            xtype: 'button',
                            x: 140,
                            y: 270,
                            height: 50,
                            width: 150,
                            text: avmon.system.validate,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'image',
                            x: 310,
                            y: 270,
                            height: 50,
                            id: 'imgResult',
                            width: 70
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var regText=button.up('form').down('#regText').value;
        var targetText=button.up('form').down('#targetText').value;

        Ext.Ajax.request({
            url: '../../../system/checkRegText',
            params: {regText:regText,targetText:targetText},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                if(obj.regMatched){
                    Ext.getCmp("imgResult").setSrc('images/yes.gif');
                }
                else{
                    Ext.getCmp("imgResult").setSrc('images/no.gif');
                }
            },
            failure: function(response, opts) {
            }
        });


    }

});