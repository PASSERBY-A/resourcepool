select count(*)
  from td_avmon_agent ag
  left join

 (select * from td_avmon_mo_info_attribute where name = 'hostName') host on ag.mo_id =
                                                                            host.mo_id
  left join (select a.agent_id,
                    case
                      when b."ampCount" is null then
                       0
                      else
                       b."ampCount"
                    end as "ampCount"
               from td_avmon_agent a
               left join (select agent_id, count(1) as "ampCount"
                           from td_avmon_amp_inst
                          group by agent_id) b on a.agent_id = b.agent_id) amp on ag.agent_id =
                                                                                  amp.agent_id
--order by amp."ampCount"