Ext.define('main.controller.MainFrame', {
    extend: 'Ext.app.Controller',

    onLaunch: function() {



        Ext.avmon.mainMenuClick=this.mainMenuClick;
        Ext.avmon.subMenuClick=this.subMenuClick;

        Ext.apply(Ext.form.VTypes, {
            password : function(val, field) {
                if (field.initialPassField) {
                    var pwd = Ext.getCmp(field.initialPassField);
                    return (val == pwd.getValue());
                }
                return true;
            },
            passwordText : avmon.modules.passwordNotMatch
        });


        Ext.modifyPwd=this.modifyPwd;

    },

    modifyPwd: function() {

        var editForm = Ext.create('Ext.form.Panel', {
            bodyPadding: 5,
            border: false,
            frame: true,
            buttonAlign: 'center',
            xtype: 'filedset',
            url: '../updateUserPwd',
            defaultType: 'textfield',
            items: [

            {
                labelStyle : 'text-align:right;width:80;',
                width:340 ,
                fieldLabel: avmon.modules.account,
                name: 'account',
                value:Ext.avmon.loginUserId,
                readOnly:true,
                allowBlank: false
            }
            ,{
                labelStyle : 'text-align:right;width:80;',
                width:340 ,
                fieldLabel: avmon.modules.originalPassword,
                name: 'oldPassword',
                id:'oldPassword',
                inputType:'password',
                allowBlank: false
            }
            ,{
                labelStyle : 'text-align:right;width:80;',
                width:340 ,
                fieldLabel: avmon.modules.newPassword,
                name: 'password',
                id:'password',
                inputType:'password',
                allowBlank: false
            }
            ,{
                labelStyle : 'text-align:right;width:80;',
                width:340 ,
                fieldLabel: avmon.modules.confirmPassword,
                name: 'repassword',
                inputType:'password',
                vtype : 'password',
                initialPassField : 'password',
                allowBlank: false
            }
            ],
            buttons: [{
                text: avmon.common.save,
                formBind: true, //only enabled once the form is valid
                disabled: true,
                iconCls:'icon-save',
                handler: function() {
                    var submitForm = this.up('form').getForm();
                    if (submitForm.isValid()) {
                        submitForm.submit({
                            method: 'POST',
                            waitMsg:avmon.common.saving,
                            success: function(form, action) {
                                Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);
                                eidtWin.hide();
                            },
                            failure:function(form, action){
                                Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);//'系统异常，请联系管理员!');
                            }
                        });
                    }
                }
            },{
                text: avmon.common.close,
                iconCls:'icon-close',
                handler: function() {
                    this.up('form').getForm().reset();
                    eidtWin.hide();
                }
            }]
        });

        var eidtWin = Ext.create('Ext.window.Window',{
            title: avmon.modules.changePassword,
            height: 210,
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

    subMenuClick: function(evt, item) {


        Ext.each(Ext.getCmp("subMenuPanel").query("label"),function(item){
            item.removeCls("button2-pressed");
        });

        var label=Ext.getCmp(item.id);
        if(label){
            label.addCls("button2-pressed");
        }

        var tabPanel=Ext.getCmp('mainFrame_TabPanel');
        
        if(item.id=="mainFrame_labMenu1"){
        	//alarm
        	Ext.avmon.currentActiveModule="alarm";
            var p=Ext.getCmp("tabAlarm");
            tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
                p.loadFlag=1;
            	setTimeout(function(){
            	    Ext.MessageBox.show({
            	        msg: avmon.common.loadingWait,
            	        progressText: 'Loading...',
            	        width:300,
            	        wait:true,
            	        waitConfig: {interval:200},
            	        icon:'icon-download'
            	    });
            	},500);
            	setTimeout(function(){
                	p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../pages/alarm/index.jsp"></iframe>',
                			false,function(){
                		setTimeout(function(){
                			Ext.MessageBox.hide();
                		},600);
    				});
            	},10);
            }

        }
        else if(item.id=="mainFrame_labMenu2"){
        	//performance
        	Ext.avmon.currentActiveModule="performance";
            var p=Ext.getCmp("tabPerformance");
            tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
            	 p.loadFlag=1;
                 p.removeAll(true);
                 var pp=createPerformancePanel();
                 //console.dir(pp);
                 p.add(pp);
                 //p.doLayout();
                 //p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../performancetree/index"></iframe>');
            }
        }
        else if(item.id=="mainFrame_labMenu3"){
        	Ext.avmon.currentActiveModule="report";
        	var p=Ext.getCmp("tabReport");
        	tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
            	p.loadFlag=1;
            	p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../report/index"></iframe>');
            }
        }
        else if(item.id=="mainFrame_labMenu4"){
        	Ext.avmon.currentActiveModule="config";
        	var p=Ext.getCmp("tabConfig");
        	tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
            	p.loadFlag=1;
            	p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../config/main"></iframe>');
            }
        }
        else if(item.id=="mainFrame_labMenu5"){
        	Ext.avmon.currentActiveModule="deploy";
            var p=Ext.getCmp("tabDeploy");
            tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
                p.loadFlag=1;
                p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../deploy/index"></iframe>');
            }

        }
        else if(item.id=="mainFrame_labMenu6"){
        	Ext.avmon.currentActiveModule="system";
        	var p=Ext.getCmp("tabSystem");
        	tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
            	p.loadFlag=1;
            	var tree=p.down("treepanel");
            	p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../system/main?type=system"></iframe>');
            }
        } else if(item.id=="mainFrame_labMenu7"){
        	Ext.avmon.currentActiveModule="kpiPerformance";
        	var p=Ext.getCmp("tabKpiPerformance");
        	tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
            	p.loadFlag=1;
                p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../kpiAnalysis/index"></iframe>');
            }
        }
        else if(item.id=="mainFrame_labMenu8"){
        	//alarm
        	Ext.avmon.currentActiveModule="simSystem";
            var p=Ext.getCmp("tabSim");
            tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
        }
        else if(item.id=="mainFrame_labMenu9"){
        	//网络巡检配置
        	Ext.avmon.currentActiveModule="polling";
            var p=Ext.getCmp("tabPolling");
        	tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
            	p.loadFlag=1;
            	var tree=p.down("treepanel");
            	var path="/root/" + tree.getRootNode().firstChild.internalId;
		    	tree.expandPath(path, 'id', '/', function(bSuccess, oLastNode){
		    		if(bSuccess){
		    			tree.getSelectionModel().select(oLastNode,true);
		    		}
		    		else{
		    			//alert('未找到节点'+moId);
		    		}
		    	});
            }
        }
      //add by mark start
        else if(item.id=="mainFrame_labMenu10"){        	
        	Ext.avmon.currentActiveModule="equipmentCenter";
            var p=Ext.getCmp("tabEquipmentCenter");            
            tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
                p.loadFlag=1;
                p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../equipmentCenter/index"></iframe>');
            }
        }
        
        else if(item.id=="mainFrame_labMenu11"){        	
        	Ext.avmon.currentActiveModule="classMgr";
            var p=Ext.getCmp("tabClassMgr");            
            tabPanel.getLayout().setActiveItem(p);
            Ext.avmon.currentTabPanel=p;
            if(!p.loadFlag){
                p.loadFlag=1;
                p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../classMgr/index"></iframe>');
            }
        }
        //--add by mark end
        

    },

    mainMenuClick: function(evt, item) {


        Ext.each(Ext.getCmp("mainMenuPanel").query("label"),function(item){
            item.removeCls("button1-pressed");
        });

        var label=Ext.getCmp(item.id);
        if(label){
            label.addCls("button1-pressed");
        }

        if(item.id=="labelMonitorCenter"){
        	if(!mainFrame_labMenu1_hidden){ Ext.getCmp("mainFrame_labMenu1").show(); }
        	if(!mainFrame_labMenu2_hidden){ Ext.getCmp("mainFrame_labMenu2").show(); }
        	if(!mainFrame_labMenu3_hidden){ Ext.getCmp("mainFrame_labMenu3").show(); }
        	if(!mainFrame_labMenu4_hidden){ Ext.getCmp("mainFrame_labMenu4").show(); }
        	if(!mainFrame_labMenu5_hidden){ Ext.getCmp("mainFrame_labMenu5").show(); }
        	if(!mainFrame_labMenu6_hidden){ Ext.getCmp("mainFrame_labMenu6").show(); }
        	if(!mainFrame_labMenu7_hidden){ Ext.getCmp("mainFrame_labMenu7").show(); }
        	if(!mainFrame_labMenu8_hidden){ Ext.getCmp("mainFrame_labMenu8").show(); }
        	if(!mainFrame_labMenu9_hidden){ Ext.getCmp("mainFrame_labMenu9").hide(); }
        	//add by mark start
        	if(!mainFrame_labMenu10_hidden){ Ext.getCmp("mainFrame_labMenu10").show(); }
//        	if(!mainFrame_labMenu11_hidden){ Ext.getCmp("mainFrame_labMenu11").show(); }
        	//--add by mark end
        	

        	
            Ext.avmon.subMenuClick(null,Ext.getCmp("mainFrame_labMenu1"));
        }

        else if(item.id=="labelPollingCenter"){
            Ext.getCmp("mainFrame_labMenu1").hide();
            Ext.getCmp("mainFrame_labMenu2").hide();
            if(!mainFrame_labMenu3_hidden){ Ext.getCmp("mainFrame_labMenu3").show(); }
            Ext.getCmp("mainFrame_labMenu4").hide();
            Ext.getCmp("mainFrame_labMenu5").hide();
            Ext.getCmp("mainFrame_labMenu6").hide();
            Ext.getCmp("mainFrame_labMenu7").hide();
            Ext.getCmp("mainFrame_labMenu8").hide();
            if(!mainFrame_labMenu9_hidden){ Ext.getCmp("mainFrame_labMenu9").show(); }
            //add by mark start
            Ext.getCmp("mainFrame_labMenu10").hide();
//            Ext.getCmp("mainFrame_labMenu11").hide();
            //add by mark end
            

            
            Ext.avmon.subMenuClick(null,Ext.getCmp("mainFrame_labMenu3"));
        }
    }

});
