select 
* 
from  
(
	select 
		value as XP_RG_IOPS,
		path as PATH 
	from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k 
	where mo_id='%s' and KPI_CODE='xp_rg_iops' and AMP_INST_ID = 'wk_xprg_1005'
) a 
left join 
(
	select 
		value as XP_RG_MS,
		path as PATH 
	from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) k 
	where mo_id='%s' and KPI_CODE='xp_rg_ms' and AMP_INST_ID = 'wk_xprg_1005'
) b on a.path=b.path  
