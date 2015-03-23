Ext.define('Ext.ux.PopupField', {
    //extend: 'Ext.form.TriggerField',
	extend:'Ext.form.field.Trigger',
    alias: 'widget.PopupField',
    xtype: 'popupfield',
    //自定义属性

    initComponent: function() {
        Ext.apply(this, {
            triggerCls : Ext.baseCSSPrefix + 'form-search-trigger',
    
		    pId : '',
		    
		    defaultAutoCreate : {tag: "input", type: "text", size: "10", autocomplete: "off", readOnly: true},
		
		    getPId : function() {
		    	return this.pId;
		    },
		    
		    setPId : function(pId) {
		    	this.pId = pId;
		    },
		    
		    getValue : function(){
		        return Ext.ux.PopupField.superclass.getValue.call(this) || "";
		    },
		
		    setValue : function(value){
		        Ext.ux.PopupField.superclass.setValue.call(this, value);
		    },
		
		    // private
		    onDestroy : function(){
		        if(this.menu) {
		            this.menu.destroy();
		        }
		        if(this.wrap){
		            this.wrap.remove();
		        }
		        Ext.ux.PopupField.superclass.onDestroy.call(this);
		    },
		
		    //onTriggerClick: Ext.emptyFn,
		
		    beforeBlur : function(){
		        var v = this.getRawValue();
		        if(v){
		            this.setValue(v);
		        }
		    }
        });
        this.callParent(arguments);
    }
});