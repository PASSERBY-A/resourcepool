select  
	count(t.mo_id) as "HOST_CNT" 
from td_avmon_mo_info t 
where 
t.type_id in (select t1.type_id from td_avmon_mo_type t1 where t1.type_id like %s)