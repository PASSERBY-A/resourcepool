select 
    t1.ALARM_ID as "id", 
    t1.MO_ID as "mo", 
    t1.SOURCE as "source", 
    decode(t1.SOURCE, null, 'Avmon', 'null', 'Avmon', 0, 'Avmon', t1.SOURCE) as "monitor",
    t1.SOURCE_TYPE as "sourceType", 
    t1.ADDITIONAL_INFO as "additionalInfo", 
    t1.CODE as "code", 
    t1.TITLE as "title", 
    TRANSLATE(t1.CONTENT,'\','|') as "content_t", 
    t1.CURRENT_GRADE as "level", 
    TO_CHAR(t1.FIRST_OCCUR_TIME, 'YYYY-MM-DD HH24:MI:SS') as "firstOccurTime", 
    TO_CHAR(t1.LAST_OCCUR_TIME, 'YYYY-MM-DD HH24:MI:SS') as "lastOccurTime", 
    t1.COUNT as "occurTimes", 
    TO_CHAR(t1.CONFIRM_TIME, 'YYYY-MM-DD HH24:MI:SS')  as "aknowledgeTime", 
    case when tu.real_name is null then t1.CONFIRM_USER else tu.real_name end as "attr_actorUser", 
    t1.STATUS as "status", 
    t1.KPI_CODE as "kpiId", 
    t1.SOLUTION as "attr_result",
    tt.caption as "moCaption",
    t3.value as "attr_ipaddr",
    t4.value as "attr_businessSystem",
    t5.value as "attr_position",
    t6.value as "attr_usage",
    t7.value as "hostName",
    t8.value as "deviceSN",
    t9.value as "assetNo", 
    t10.KPI_CODE || '|' || t10.CAPTION AS "kpiInfo" 
from tf_avmon_alarm_history t1 
inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) 
left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME)
left join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID) 
left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  
left join TD_AVMON_MO_INFO_ATTRIBUTE t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem')  
left join TD_AVMON_MO_INFO_ATTRIBUTE t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position')  
left join TD_AVMON_MO_INFO_ATTRIBUTE t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage') 
left join TD_AVMON_MO_INFO_ATTRIBUTE t7 on (t1.MO_ID = t7.MO_ID and t7.NAME='hostName') 
left join TD_AVMON_MO_INFO_ATTRIBUTE t8 on (t1.MO_ID = t8.MO_ID and t8.NAME='deviceSN') 
left join TD_AVMON_MO_INFO_ATTRIBUTE t9 on (t1.MO_ID = t9.MO_ID and t9.NAME='assetNo') 
left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE) 
%s