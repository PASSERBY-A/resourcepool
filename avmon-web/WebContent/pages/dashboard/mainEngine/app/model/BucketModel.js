Ext.define('MyApp.model.BucketModel', {
    extend: 'Ext.data.Model',
    fields: [
             {name: 'bucketId',type: 'string'},
             {name: 'name', type: 'string'},
             {name: 'usage', type: 'string'}
         ],
         hasMany: {  
              model: 'ImageModel',  
              name: 'images'
          } 
});