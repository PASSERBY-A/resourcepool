select agent.agent_id as "agentId",
       agent.ip as "ip",
       agent.os as "os",
       to_char(curr.kpi_time, 'yyyy-MM-dd HH24:mi:ss') as "pingTime",
       curr.value as "pingStatus"
  from td_avmon_agent agent
  left join tf_avmon_kpi_value_list list on agent.mo_id = list.mo_id
  left join tf_avmon_kpi_value_current curr on list.value_key =
                                               curr.value_key
 where list.kpi_code = '1001022001'