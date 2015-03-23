select (case WHEN a.str_value is null then '' ELSE a.str_value END) as DISK_NAME,
    (case WHEN b.str_value is null then '0 r+w/s' ELSE b.str_value END) as RW_RATE,
    (case WHEN c.str_value is null then '0%' ELSE c.str_value END) as BUSY_RATE,
    (case WHEN c.value is null then '0' ELSE c.value END) as BUSY_RATE_VALUE,
    (case WHEN d.str_value is null then '0 blks/s' ELSE d.str_value END) as TRAN_BYTES,
    (case WHEN a.threshold_level is null then 0 ELSE a.threshold_level END) as DS_LEVEL
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)  a 
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001014003') b on a.mo_id=b.mo_id and a.instance=b.instance
left join (select mo_id,value,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001014002') c on a.mo_id=c.mo_id and a.instance=c.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001014004') d on a.mo_id=d.mo_id and a.instance=d.instance
where a.mo_id='{MO_ID}' and a.kpi_code='1001014001' ORDER BY c.value desc