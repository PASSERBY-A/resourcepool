SELECT 
	count(*)
from 
td_avmon_amp_vm_host where obj_type = 'VirtualMachine' and  agent_id = '%s' 