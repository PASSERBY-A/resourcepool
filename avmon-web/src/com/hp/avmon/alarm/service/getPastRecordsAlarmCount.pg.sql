select 
	count(*) 
from TF_AVMON_ALARM_HISTORY T1
	--inner join TF_AVMON_ALARM_DATA T2 on (T1.MO_ID=t2.MO_ID and t1.KPI_CODE=t2.KPI_CODE)
 %s