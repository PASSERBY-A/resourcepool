 select count(*)
  from 
    td_avmon_snmp_info a,
    td_avmon_snmp_type b
  where 
    a.type_id = b.type_id and
    b.display_flag=1 and
    not exists( select  mo_id  from   td_avmon_snmp_info_ATTRIBUTE  where name='businessSystem' and a.mo_id = mo_id) and    
    a.type_id = 'OTHERS'