select count(*) as count , a.current_grade as grade,b.type_id as type 
from tf_avmon_alarm_data a, TD_AVMON_MO_INFO b 
where a.mo_id=b.mo_id and b.type_id like 'HOST_%' 
group by a.current_grade,b.type_id order by a.current_grade