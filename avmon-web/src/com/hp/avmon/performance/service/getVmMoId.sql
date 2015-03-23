select t.mo_id
  from td_avmon_mo_info t
 where t.type_id = 'VMHOST'
   and t.agent_id = '%s'
   and t.caption = (select host.hostname
                      from td_avmon_amp_vm_host host
                     where host.obj_id = '%s'
                       and host.agent_id = '%s'
                       and host.amp_inst_id = '%s')