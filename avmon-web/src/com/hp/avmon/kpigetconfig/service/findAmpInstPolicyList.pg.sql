select 
p.agent_id as "agentId",
p.amp_inst_id as "ampInstId",
p.kpi_code as "kpiCode",
p.schedule as "schedule",
p.kpi_group as "kpiGroup",
ki.caption as "kpiName",
p.node_key as "nodeKey"
from td_avmon_amp_policy p
left join td_avmon_kpi_info ki
on p.kpi_code=ki.kpi_name
where 
p.agent_id='%s' 
and p.amp_inst_id='%s' 
and p.node_key='%s'