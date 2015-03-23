select
  a1.kpi_code as "kpiCode",
  a2.CAPTION as "caption",
  a1.SCHEDULE as "schedule",
  a1.KPI_GROUP as "kpiGroup",
  a1.AGENT_ID as "agentId",
  a1.AMP_INST_ID as "ampInstId",
  a1.NODE_KEY as "nodeKey"
from TD_AVMON_AMP_POLICY a1
inner join TD_AVMON_KPI_INFO a2 on(a1.KPI_CODE = a2.kpi_code)
where a1.agent_id = ? and a1.amp_inst_id = ?
