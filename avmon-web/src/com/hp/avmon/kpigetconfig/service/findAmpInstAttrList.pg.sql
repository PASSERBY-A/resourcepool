select 
t.name as "name", 
t.value as "value" 
from td_avmon_amp_inst_attr t
where 
t.amp_inst_id='%s'
and t.agent_id='%s'