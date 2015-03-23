select ag.AGENT_ID "agentId",
       ag.AGENT_VERSION as "agentVersion",
       ag.os as "os",
       ag.os_version as "osVersion",
       ag.ip as "ip",
       ag.agent_Status as "status",
       NVL(ag.host_name,host.value) as "hostName",
       ag.last_update_time as "lastUpdateTime",
       ag.last_heartbeat_time as "lastHeartbeatTime",
       round((sysdate - ag.last_heartbeat_time) * 24 * 60) as "agentStatus",
       ag.agent_host_status as "agentHostStatus",
       ag.AGENT_COLLECT_FLAG as "agentCollectFlag",
       ag.gateway as "gateway",
       ag.mo_id as "moId",
       amp."ampCount",
       amp."ampCount" as "ampCount1"
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