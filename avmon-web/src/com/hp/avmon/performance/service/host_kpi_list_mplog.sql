select a.str_value as Alert_Level,
b.str_value as Keyword,
c.str_value as Logged_by,
d.str_value as Data,
e.str_value as Generator,
f.str_value as Sensor_Type,
g.str_value as Sensor_Number,
h.str_value as MP_status
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)  a 
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034002') b on a.mo_id=b.mo_id and a.instance=b.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034003') c on a.mo_id=c.mo_id and a.instance=c.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034004') d on a.mo_id=d.mo_id and a.instance=d.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034005') e on a.mo_id=e.mo_id and a.instance=e.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034006') f on a.mo_id=f.mo_id and a.instance=f.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034007') g on a.mo_id=g.mo_id and a.instance=g.instance
left join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='{MO_ID}' and kpi_code='1001034008') h on a.mo_id=h.mo_id and a.instance=h.instance
where a.mo_id='{MO_ID}' and a.kpi_code='1001034001' ORDER BY a.str_value desc