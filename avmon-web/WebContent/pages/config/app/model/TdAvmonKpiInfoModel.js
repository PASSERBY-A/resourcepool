Ext.regModel('CFG.model.TdAvmonKpiInfoModel', {
fields : [ 
{name : "kpi_code",type : "string"}
,{name : "kpi_name",type : "string"}
,{name : "caption",type : "string"}
,{name : "aggmethod",type : "string"}
,{name : "precision",type : "string"}
,{name : "unit",type : "string"}
,{name : "iscalc",type : "int"}
,{name : "calcmethod",type : "string"}
,{name : "isstore",type : "int"}
,{name : "storeperiod",type : "int"}
,{name : "datatype",type : "string"}
,{name : "id",type : "int"}
,{name : "recent_trend",type : "int"}
,{name : "type_id",type : "string"}
,{name : "comment_sql",type : "string"}
]
});