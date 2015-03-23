select trap_content as "trap_content",alarm_title as "alarm_title",alarm_content as "alarm_content",
alarm_time as "alarm_time",alarm_grade as "alarm_grade",alarm_type as "alarm_type"
from TD_AVMON_SNMP_TRAP
where 1=1 and trap_key='%s'