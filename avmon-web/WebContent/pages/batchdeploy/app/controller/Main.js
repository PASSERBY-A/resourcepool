Ext.define('MyApp.controller.Main', {
    extend: 'Ext.app.Controller',

    refs: [
        {
            ref: 'discoveryGrid',
            selector: 'discoveryGrid'
        }
    ],

    onBtnDeployClick: function(button, e, options) {
        var g=this.getDiscoveryGrid();

        var store=g.getStore();

        var sm = g.getSelectionModel();

        var records=sm.getSelection();

        var moIds="";

        Ext.each(records,function(record){
            var moId=record.get("moId");

            moIds+=moId+",";

            //record.set("status","ok");
        });


        Ext.Msg.wait(avmon.deploy.deployingLaterOn);

        Ext.Ajax.request({
            url: 'deployDiscovery',
            params:{moIds:moIds},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);

                console.dir(obj);
            },
            failure: function(response, opts) {
                Ext.Msg.alert('error','server-side failure with status code ' + response.status);
            }
        });

        setTimeout(function(){

            g.getStore().load({params:{aa:1}});
            Ext.getCmp("deployResultGrid").getStore().load({params:{moId:"aa"}});
            Ext.Msg.close();

        }, 5000);


    },

    onBtnScanAgentClick: function(button, e, options) {

        button.hide();

        /*
        var p=Ext.ComponentQuery.query("#progressBar")[0];

        p.show();

        p.wait({
        interval: 500, //bar will move fast!
        duration: 1000,
        increment: 20,
        text: 'Scaning...',
        scope: this,
        fn: function(){
        p.updateText('Done!');
        //show panel2
        Ext.ComponentQuery.query("#main")[0].getLayout().setActiveItem(1);
    }
            });

            //*/

            var startIp=Ext.getCmp("startIp");

            var endIp=Ext.getCmp("endIp");

            //alert(endIp.getValue());

            var g=this.getDiscoveryGrid();

            g.getStore().load({params:{startIp:startIp.getValue(),endIp:endIp.getValue()}});

            Ext.getCmp("main").getLayout().setActiveItem(1);


    },

    init: function(application) {
        this.control({
            "#btnDeploy": {
                click: this.onBtnDeployClick
            },
            "#btnScanAgent": {
                click: this.onBtnScanAgentClick
            }
        });
    }

});
