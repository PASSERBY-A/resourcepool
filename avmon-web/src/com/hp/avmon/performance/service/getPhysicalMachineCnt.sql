select count(*) 
from td_avmon_amp_vm_host where obj_type = 'HostSystem'  and agent_id ='%s'