select b.str_value as "selType",
c.str_value as "selDesc",
d.str_value as "selDate"
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)  a 
inner join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}' and kpi_code='8201004001') b on a.mo_id=b.mo_id and a.instance=b.instance
inner join (select mo_id,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}'  and kpi_code='8201004002') c on a.mo_id=c.mo_id and a.instance=c.instance
inner join (select mo_id,value,str_value,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k where mo_id='{MO_ID}'  and kpi_code='8201004003') d on a.mo_id=d.mo_id and a.instance=d.instance
where a.mo_id='{MO_ID}' 
--and a.kpi_code='1001018001' 
ORDER BY d.value desc