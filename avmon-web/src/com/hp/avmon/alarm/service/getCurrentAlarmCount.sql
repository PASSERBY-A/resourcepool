select 
	count(*) 
from TF_AVMON_ALARM_DATA t1  
inner join TD_AVMON_MO_INFO t8 on (t1.MO_ID = t8.MO_ID) 
left join td_avmon_mo_info t2 on (t1.MO_ID = t2.MO_ID) 
left join td_avmon_mo_info_attribute t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  
left join td_avmon_mo_info_attribute t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem')  
left join td_avmon_mo_info_attribute t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position')  
left join td_avmon_mo_info_attribute t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage')   
 %s 