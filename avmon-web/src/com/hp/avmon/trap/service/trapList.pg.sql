select trap_key as "trap_key", trap_content as "trap_content",alarm_count as "alarm_count", 
oid_list as "oid_list",alarm_title as "alarm_title", alarm_grade as "alarm_grade", alarm_time as "alarm_time",  alarm_type as "alarm_type",  alarm_content as "alarm_content", 
flag as "flag",b.value as "trap_source",trap_status as "trap_status"
from TD_AVMON_SNMP_TRAP a
left join td_avmon_mo_info_attribute b
on a.trap_source=b.mo_id and b.name='ip'
%s
order by %s %s