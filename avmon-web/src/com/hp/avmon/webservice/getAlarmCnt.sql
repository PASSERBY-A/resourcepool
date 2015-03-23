select 
	count(t.alarm_id) as "ALARM_CNT", 
	decode(t.CURRENT_GRADE, 0, 'LEVEL0', 1, 'LEVEL1', 2, 'LEVEL2', 3, 'LEVEL3', 4, 'LEVEL4') as "CURRENT_GRADE"
from 
tf_avmon_alarm_data t 
group by t.current_grade 