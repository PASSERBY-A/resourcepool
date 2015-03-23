Ext.define('MyApp.model.ImageModel', {
    extend: 'Ext.data.Model',
    fields: [
             {name:'imgId', type:'string' },
             {name:'bucketId', type:'string'},
             {name:'src', type:'string' },
             {name:'class', type:'string'},
             {name:'width', type:'string'},
             {name:'height',type:'string'},
             {name:'top',type:'string'}
      ],
      belongsTo: {  
           model        : 'BucketModel'      	
       }
});