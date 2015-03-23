select count(*)
  from 
    td_avmon_mo_info a,
    TD_AVMON_MO_TYPE b,
    td_avmon_mo_info_attribute c     
  where 
    a.type_id = b.type_id and
    a.mo_id = c.mo_id and
    c.name = 'businessSystem' and 
    b.display_flag=1 and
    c.value = '%s'	 and
    a.type_id = ANY('HOST_WINDOWS','HOST_HP-UX','HOST_AIX','HOST_SUNOS','HOST_LINUX')