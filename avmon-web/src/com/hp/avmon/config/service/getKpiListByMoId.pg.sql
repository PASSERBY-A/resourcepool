select distinct kpi.kpi_code as "kpi", k.caption as "displayKpi"
  from td_avmon_mo_info  info,
       td_avmon_amp_inst inst,
       td_avmon_amp      amp,
       td_avmon_amp_kpi  kpi,
       td_avmon_kpi_info k
where info.agent_id = inst.agent_id
   and inst.amp_id = amp.amp_id
   and amp.amp_id = kpi.amp_id
   and k.kpi_code = kpi.kpi_code
   and inst.amp_inst_id not in ('db-oracle','amp_vcenter')
   and info.mo_id = '%s'
union
select kpi_code, caption
  from td_avmon_kpi_info
where reverse(split_part(reverse(type_id),'_',1)) in ('SNMP', 'IPMI')
    AND (type_id =
                (select type_id || '_' || protocal_method
         from td_avmon_mo_info
         where mo_id = '%s')
                or type_id =
               (select parent_id || '_' || protocal_method
         from td_avmon_mo_info
         where mo_id = '%s'))


         
         

