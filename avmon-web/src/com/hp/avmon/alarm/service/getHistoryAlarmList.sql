select 
	t1.ALARM_ID as "id",
	t1.MO_ID as "mo",
	t1.SOURCE as "source", 
	t1.SOURCE_TYPE as "sourceType", 
	t1.ADDITIONAL_INFO as "additionalInfo", 
	t1.CODE as "code", 
	t1.TITLE as "title", 
	t1.CONTENT as "content_t", 
	t1.CURRENT_GRADE as "level", 
	TO_CHAR(t1.FIRST_OCCUR_TIME, 'YYYY-MM-DD HH24:MI:SS') as "firstOccurTime", 
	TO_CHAR(t1.LAST_OCCUR_TIME, 'YYYY-MM-DD HH24:MI:SS') as "lastOccurTime", 
	t1.COUNT as "occurTimes", 
	t1.CONFIRM_TIME as "aknowledgeTime", 
	t1.STATUS as "status", 
	t1.KPI_CODE as "kpiId", 
	TO_CHAR(t1.CLOSE_TIME, 'YYYY-MM-DD HH24:MI:SS') as "clearing_time", 
	t1.SOLUTION as "attr_result", 
	case when t7.real_name is null then t1.CLOSE_BY else t7.real_name end as "attr_actorUser",
    t8.caption as "moCaption",
    t3.value as "attr_ipaddr",
    t4.value as "attr_businessSystem",
    t5.value as "attr_position",
    t6.value as "attr_usage"
from TF_AVMON_ALARM_HISTORY t1 
inner join TD_AVMON_MO_INFO t8 on (t1.MO_ID = t8.MO_ID) 
left join td_avmon_mo_info t2 on (t1.MO_ID = t2.MO_ID) 
left join td_avmon_mo_info_attribute t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  
left join td_avmon_mo_info_attribute t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem')  
left join td_avmon_mo_info_attribute t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position')  
left join td_avmon_mo_info_attribute t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage')  
left join portal_users t7 on (t1.CLOSE_BY = t7.USER_ID or t1.CLOSE_BY = t7.REAL_NAME) 
%s 