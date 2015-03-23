select (case WHEN a.str_value is null then '' ELSE a.str_value END) as FS_NAME,
    (case WHEN b.str_value is null then '0 MB' ELSE b.str_value END) as FS_TOTAL,
    (case WHEN c.str_value is null then '' ELSE c.str_value END) as FS_PATH,
    (case WHEN d.value is null then '0' ELSE d.value END) as FS_USAGE,
    (case WHEN d.str_value is null then '0%' ELSE d.str_value END) as FS_PERCENT,
    (case WHEN e.str_value is null then '0%' ELSE e.str_value END) as FS_USED,
    (case WHEN a.threshold_level is null then 0 ELSE a.threshold_level END) as FS_LEVEL
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)  a 
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}' and kpi_code='1001018004') b on a.mo_id=b.mo_id and a.instance=b.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}' and kpi_code='1001018003') c on a.mo_id=c.mo_id and a.instance=c.instance
left join (select mo_id,value,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}' and kpi_code='1001018002') d on a.mo_id=d.mo_id and a.instance=d.instance
left join (select mo_id,value,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}' and kpi_code='1001018005') e on a.mo_id=e.mo_id and a.instance=e.instance

where a.mo_id='{MO_ID}' and a.kpi_code='1001018001' ORDER BY d.value desc