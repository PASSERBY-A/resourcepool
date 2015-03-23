select 
distinct
  a.id as mo_id,
  nvl(b.num_value, 0) as cpu,
  nvl(b.threshold_level, 0) as cpu_level,
  nvl(c.num_value, 0) as mem,
  nvl(c.threshold_level, 0) as mem_level,
  nvl(d.num_value, 0) as disk,
  nvl(d.threshold_level, 0) as disk_level,
  nvl(e.num_value, 0) as syslog,
  g.caption as mo_name,
  'good' as status,
  f.current_grade as current_grade
from STG_AVMON_AVAILABLE_SUMMARY a 
  left join (select mo_id,num_value,threshold_level from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where KPI_CODE='1001011005') b on a.id=b.mo_id
  left join (select mo_id,num_value,threshold_level from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where KPI_CODE='1001010001') c on a.id=c.mo_id
  left join (select mo_id,max(num_value) as num_value,threshold_level from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where KPI_CODE='1001013003' group by mo_id,threshold_level) d on a.id=d.mo_id
  left join (select mo_id,num_value from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where KPI_CODE='sys_log') e on a.id=e.mo_id
  left join (
    select mo_Id, max(current_grade) as current_grade from TF_AVMON_ALARM_DATA where KPI_CODE='1001011005'  
    group by mo_id
    union
    select mo_Id, max(current_grade) as current_grade from TF_AVMON_ALARM_DATA where KPI_CODE='1001010001' 
    group by mo_id
    union
    select mo_Id, max(current_grade) as current_grade from TF_AVMON_ALARM_DATA where KPI_CODE='1001013003' 
    group by mo_id
    union
    select mo_Id, max(current_grade) as current_grade from TF_AVMON_ALARM_DATA where KPI_CODE='sys_log' 
    group by mo_id
    ) f on a.id=f.mo_id
  left join TD_AVMON_MO_INFO g on a.id=g.mo_id
