SELECT  count(*)
    FROM td_avmon_amp_inst a, td_avmon_amp b, td_avmon_agent c left join td_avmon_mo_info_attribute d on (c.mo_id=d.mo_id and d.name='hostName')
   WHERE a.amp_id = b.amp_id and a.agent_id=c.agent_id
   %s 