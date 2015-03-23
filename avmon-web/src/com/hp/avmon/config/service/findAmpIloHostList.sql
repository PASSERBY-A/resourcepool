select 
h.agent_id as "agentId",
h.amp_inst_id as "ampInstId",
h.ip as "ip",
h.hostname as "hostName",
h.username as "userName",
h.password as "password",
h.ext_param1 as "extParam1",
h.ext_param2 as "extParam2",
h.ext_param3 as "extParam3",
h.mo_id as "moId"
from td_avmon_amp_ilo_host h
where h.agent_id='%s' 
and h.amp_inst_id='%s'