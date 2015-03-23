select  count(*)
  from 
    td_avmon_snmp_info a,
  	TD_AVMON_snmp_TYPE b
  where 
    a.type_id = b.type_id and
    b.display_flag=1 and
  not exists( select  mo_id  from   td_avmon_snmp_info_attribute  where name='businessSystem' and a.mo_id = mo_id) and 
  a.type_id = 'SNMPOID'
      and
    a.type_id IN ('MIDDLEWARE','DATABASE','STORAGE','NETWORK','HOST','huawei','HOST_WINDOWS','HOST_HP-UX','HOST_AIX','HOST_SUNOS','HOST_LINUX','s1700')