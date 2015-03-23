select 
    t1.ALARM_ID as "ID", 
    t1.CURRENT_GRADE as "LEVEL",
    t1.STATUS as "STATUS",
    t2.caption as "MO_CAPTION",
    TRANSLATE(t1.CONTENT,'\','|') as "CONTENT",
    t1.COUNT AS "AMOUNT",
    TO_CHAR(t1.LAST_OCCUR_TIME,'YYYY-MM-DD HH24:MI:SS') as "OCCUR_TIMES",
    t1.KPI_CODE as "KPI_CODE",
    t1.MO_ID as MO_ID
from TF_AVMON_ALARM_DATA t1 
inner join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID) 
left join portal_users t8 on (t1.CONFIRM_USER = t8.USER_ID or t1.CONFIRM_USER = t8.REAL_NAME)
where 1=1 
 %s 
--order by t1.LAST_OCCUR_TIME desc