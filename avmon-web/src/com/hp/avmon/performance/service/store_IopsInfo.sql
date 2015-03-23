select
* from
(select 
    nvl(VALUE, 0) AS XP_CLP_MS, PATH
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) 
where mo_id='%s' and KPI_CODE='xp_clp_ms' and AMP_INST_ID = 'wk_xpclp_1007') a 
left join 
(select 
    nvl(VALUE, 0) AS XP_CLP_CMUS, PATH
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) 
where mo_id='%s' and KPI_CODE='xp_clp_cmus' and AMP_INST_ID = 'wk_xpclp_1007') b on a.PATH = b.PATH
left join 
(select 
    nvl(VALUE, 0) AS XP_CLP_CMWR, PATH
from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) 
where mo_id='%s' and KPI_CODE='xp_clp_cmwr' and AMP_INST_ID = 'wk_xpclp_1007') c on a.PATH = c.PATH