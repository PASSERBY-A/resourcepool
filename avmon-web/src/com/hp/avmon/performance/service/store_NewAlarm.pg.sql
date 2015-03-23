select 
	current_grade,
	to_char(first_occur_time,'HH24:MI MM-DD') as first_occur_time,
	title 
from TF_AVMON_ALARM_DATA 
where mo_id='%s' 
ORDER BY FIRST_OCCUR_TIME DESC 
limit 9