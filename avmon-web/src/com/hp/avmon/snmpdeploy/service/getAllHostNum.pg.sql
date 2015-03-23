select count(*)
  from 
    td_avmon_snmp_info a,
    td_avmon_snmp_type b,
    td_avmon_snmp_info_ATTRIBUTE c     
  where 
    a.type_id = b.type_id and
    a.mo_id = c.mo_id and
    c.name = 'businessSystem' and 
    b.display_flag=1 and
    c.value = '%s'	 and
    a.type_id IN ('HOST_WINDOWS','HOST_HP-UX','HOST_AIX','HOST_SUNOS','HOST_LINUX')