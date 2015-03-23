select count(*) as count
  from td_avmon_agent agent
  left join tf_avmon_kpi_value_list list on agent.mo_id = list.mo_id
  left join tf_avmon_kpi_value_current curr on list.value_key =
                                               curr.value_key
 where list.kpi_code = '1001022001'