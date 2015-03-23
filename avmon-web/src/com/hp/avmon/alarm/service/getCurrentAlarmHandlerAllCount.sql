 SELECT count(*)
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