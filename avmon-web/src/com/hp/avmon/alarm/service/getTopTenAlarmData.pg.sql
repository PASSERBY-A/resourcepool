select * from (select total as "total",caption as "hostname",
(case when c.value is null  then caption 
else c.value
end) as "value" 
from TD_AVMON_MO_INFO a inner join (select count(*) total, mo_id 
from tf_avmon_alarm_data t1
%s
group by mo_id order by total desc) b on a.mo_id = b.mo_id 
left join (select value,mo_id from td_avmon_mo_info_attribute where name='hostName') 
c on a.mo_id = c.mo_id
%s 
%s

