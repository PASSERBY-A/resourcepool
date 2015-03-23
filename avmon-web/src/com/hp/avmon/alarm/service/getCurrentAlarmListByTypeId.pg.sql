select  t1.MO_ID as "mo", tt.caption as "attr_ipaddr"  ,tt.type_id as "typeId" from TF_AVMON_ALARM_DATA t1 
inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) 
left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) 
left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  
left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE) 
%s
