select 
	a.pid||'*'||a.id as "id", 
	a.text as "text", 
	a.id as "moId", 
	a.pid as "pid"
from STG_AVMON_ALARM_TREE a 
order by 
convert_to(a.text ,'GBK')