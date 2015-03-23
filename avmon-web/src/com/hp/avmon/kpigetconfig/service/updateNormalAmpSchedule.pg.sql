insert into TD_AVMON_AMP_POLICY

select a.kpi_code,a.kpi_group,a.schedule,b.agent_id,b.amp_inst_id,b.agent_id 
	from TD_AVMON_AMP_KPI a 
    inner join (select amp_id,agent_id,amp_inst_id from TD_AVMON_AMP_INST where agent_id='{AGENT_ID}' and amp_inst_id='{AMP_INST_ID}') b on a.amp_id=b.amp_id
    left join (select kpi_code from TD_AVMON_AMP_POLICY where agent_id='{AGENT_ID}' and amp_inst_id='{AMP_INST_ID}') c on a.kpi_code=c.kpi_code
where c.kpi_code is null

