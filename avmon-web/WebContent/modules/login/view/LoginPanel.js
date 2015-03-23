Ext.define('login.view.LoginPanel', {
    extend: 'Ext.panel.Panel',

    border: 0,
    frame: false,
    height: 742,
    width: 1163,
    layout: {
        align: 'stretch',
        pack: 'center',
        type: 'vbox'
    },
    bodyCls: 'login-background',
    title: '',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'container',
                    flex: 1
                },
                {
                    xtype: 'container',
                    height: 300,
                    layout: {
                        align: 'stretch',
                        type: 'hbox'
                    },
                    items: [
                        {
                            xtype: 'container',
                            flex: 1.3
                        },
                        {
                            xtype: 'form',
                            border: 0,
                            frame: true,
                            padding: 0,
                            width: 484,
                            layout: {
                                type: 'absolute'
                            },
                            frameHeader: false,
                            items: [
                                {
                                    xtype: 'image',
                                    height: 114,
                                    width: 484,
                                    src: '../resources/images/login_banner.png'
                                },
                                {
                                    xtype: 'textfield',
                                    x: 40,
                                    y: 130,
                                    width: 360,
                                    name: 'userId',
                                    fieldLabel: avmon.modules.userId,
                                    labelAlign: 'right',
                                    labelPad: 20,
                                    labelSeparator: ' '
                                },
                                {
                                    xtype: 'textfield',
                                    x: 40,
                                    y: 170,
                                    width: 360,
                                    inputType: 'password',
                                    name: 'password',
                                    fieldLabel: avmon.modules.password,
                                    labelAlign: 'right',
                                    labelPad: 20,
                                    labelSeparator: ' '
                                },
                                {
                                    xtype: 'combobox',
                                    x: 290,
                                    y: 200,
                                    width: 110,
                                    name: 'theme',
                                    fieldLabel: '',
                                    labelAlign: 'right',
                                    labelPad: 20,
                                    labelSeparator: ' ',
                                    displayField: 'display',
                                    store: {
                                        fields: [
                                            'value',
                                            'display'
                                        ],
                                        data: [
                                            {
                                                value: '',
                                                display: avmon.modules.defaultThemme
                                            },
                                            {
                                                value: '-gray',
                                                display: avmon.modules.grayTheme
                                            },
                                            {
                                                value: '-access',
                                                display: avmon.modules.blackTheme
                                            },
                                            {
                                                value: '-neptune',
                                                display: avmon.modules.conciseTest
                                            }
                                        ]
                                    },
                                    valueField: 'value'
                                },
                                {
                                    xtype: 'button',
                                    x: 70,
                                    y: 240,
                                    height: 30,
                                    width: 100,
                                    text: avmon.modules.login,
                                    type: 'submit',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    x: 190,
                                    y: 240,
                                    height: 30,
                                    width: 100,
                                    text: avmon.modules.reset,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'checkboxfield',
                                    x: 130,
                                    y: 200,
                                    name: 'ckSavePass',
                                    fieldLabel: '',
                                    boxLabel: avmon.modules.savePasswordInLocal
                                },
                                {
                                    xtype: 'button',
                                    x: 310,
                                    y: 240,
                                    height: 30,
                                    width: 110,
                                    text: avmon.modules.licenseRegister,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick2,
                                            scope: me
                                        }
                                    }
                                }
                            ],
                            listeners: {
                                afterrender: {
                                    fn: me.onFormAfterRender,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'container',
                            flex: 1
                        }
                    ]
                },
                {
                    xtype: 'container',
                    flex: 1
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, options) {

        button.disable();
        var userId=button.up("form").getForm().findField('userId').getValue();
        var password=button.up("form").getForm().findField('password').getValue();
        var savePass=button.up("form").getForm().findField('ckSavePass').getValue();
        var theme=button.up("form").getForm().findField('theme').getValue();

        var processBar = Ext.MessageBox.show({
            msg: avmon.modules.logining,
            progressText: avmon.modules.starting,
            width:300,
            wait:true
        });
        
        Ext.Ajax.request({
            url: '../login',
            timeout: 60000,
            params: {userId:userId,password:password},
            success: function(response, opts) {
            	processBar.close();
                var obj=Ext.decode(response.responseText);
                if(obj.errorMsg){
                    Ext.Msg.alert(avmon.modules.loginFailed,obj.errorMsg);
                }
                else{
                	Ext.avmon={};
                    Ext.avmon.loginUserId=obj.userId;
                    Ext.avmon.loginUserName=obj.userName;
                    showMainWindow(theme);
                }
                if(savePass){
                    Ext.util.Cookies.set('avmon.pass',password, new Date(new Date().getTime()+(1000*60*60*24*365)));
                }
                else{
                    Ext.util.Cookies.set('avmon.pass','', new Date(new Date().getTime()+(1000*60*60*24*365)));
                }
                Ext.util.Cookies.set('avmon.userid',userId, new Date(new Date().getTime()+(1000*60*60*24*365)));
                Ext.util.Cookies.set('avmon.theme',theme, new Date(new Date().getTime()+(1000*60*60*24*365)));

                button.enable();
            },
            failure: function(response, opts) {
            	processBar.close();
                Ext.MessageBox.alert(avmon.modules.wrong,avmon.modules.wrongRetry);
                button.enable();
            }
        });

    },

    onButtonClick1: function(button, e, options) {
        button.up("form").getForm().reset();

    },

    onFormAfterRender: function(abstractcomponent, options) {
        this.down("form").getForm().findField('userId').setValue("admin");
        var lastUserId=Ext.util.Cookies.get('avmon.userid');
        var lastPassword=Ext.util.Cookies.get('avmon.pass');
        var theme=Ext.util.Cookies.get('avmon.theme');

        if(lastUserId!=null){
            this.down("form").getForm().findField('userId').setValue(lastUserId);
            this.down("form").getForm().findField('password').focus();
        }
        else{
            this.down("form").getForm().findField('userId').focus();
        }
        //alert(lastPassword);
        if(lastPassword!=null){
            this.down("form").getForm().findField('password').setValue(lastPassword);
            //alert(this.down("form").getForm().findField('ckSavePass').getValue());
            this.down("form").getForm().findField('ckSavePass').setValue(true);
        }

        if(theme!=null){
            this.down("form").getForm().findField('theme').setValue(theme);
        }
        else{
            this.down("form").getForm().findField('theme').setValue("");
        }



    },

    onButtonClick2: function(button, e, options) {
        var userId=button.up("form").getForm().findField('userId').getValue();
        var password=button.up("form").getForm().findField('password').getValue();
        Ext.Ajax.request({
            url: '../getRegCode',
            params: {userId:userId,password:password},
            success: function(response, opts) {

                var obj=Ext.decode(response.responseText);

                var editForm = Ext.create('Ext.form.Panel', {
                    bodyPadding: 5,
                    border: false,
                    frame: true,
                    buttonAlign: 'center',
                    xtype: 'filedset',
                    url: '../registLicense',
                    defaultType: 'textfield',
                    items: [
                    {
                        labelStyle : 'text-align:left;width:80;',
                        width:340,
                        fieldLabel: avmon.modules.initialRegistrationCode,
                        name: 'regCode',
                        fieldStyle:'padding-top:3px;background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: #000000 0px solid;',
                        value:obj.regCode,
                        allowBlank: false
                    }
                    ,{
                        labelStyle : 'text-align:left;width:120;',
                        width:360,
                        fieldLabel: avmon.modules.registrationCode,
                        name: 'license',
                        allowBlank: false
                    }
                    ],
                    buttons: [{
                        text: avmon.modules.register,
                        formBind: true, //only enabled once the form is valid
                        disabled: true,
                        iconCls:'icon-save',
                        handler: function() {
                            var submitForm = this.up('form').getForm();
                            if (submitForm.isValid()) {
                                submitForm.submit({
                                    method: 'POST',
                                    waitMsg:avmon.modules.registering,
                                    success: function(form, action) {
                                        Ext.MessageBox.alert(avmon.common.reminder, action.result.msg,function(){eidtWin.close();});

                                    }
                                });
                            }
                        }
                    },{
                        text: avmon.common.close,
                        iconCls:'icon-close',
                        handler: function() {
                            eidtWin.close();
                        }
                    }]
                });
                var eidtWin = Ext.create('Ext.window.Window',{
                    title: avmon.modules.registerLicense,
                    height: 140,
                    width:400,
                    hidden: true,
                    iconCls:'icon-form',
                    layout: 'fit',
                    plain: true,
                    modal:true,
                    closable: true,
                    closeAction: 'hide',
                    items: [editForm]
                });

                eidtWin.show();

            },
            failure: function(response, opts) {
                Ext.MessageBox.alert(avmon.modules.wrong,avmon.modules.wrongRetry);
            }
        });
    }

});