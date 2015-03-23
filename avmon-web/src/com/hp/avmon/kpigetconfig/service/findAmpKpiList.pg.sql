select 
i.agent_id as "agentId",
i.amp_inst_id as "ampInstId",
k.kpi_code as "kpiCode",
k.schedule as "schedule",
k.kpi_group as "kpiGroup",
ki.caption as "kpiName"
from td_avmon_amp_kpi k
left join td_avmon_kpi_info ki
on k.kpi_code=ki.kpi_name
left join td_avmon_amp_inst i 
on k.amp_id=i.amp_id
where i.agent_id='%s' 
and i.amp_inst_id='%s'