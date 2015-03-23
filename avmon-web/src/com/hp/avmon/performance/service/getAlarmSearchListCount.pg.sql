select 
   count(*)
from TF_AVMON_ALARM_DATA t1 
inner join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID) 
left join portal_users t8 on (t1.CONFIRM_USER = t8.USER_ID or t1.CONFIRM_USER = t8.REAL_NAME)
where 1=1 
 %s 
