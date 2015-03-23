(select "mo","attr_ipaddr", "typeId",count(*) as "count"
from 
(select t1.mo_id as "mo", 
(case when t3.value is null and t4.value is null then tt.caption 
     when t3.value='NA' and t4.value is not null then t4.value     
     when t3.value='NA' and t4.value is null then tt.caption       
     when t3.value is null and t4.value is not null then t4.value        
     when t3.value is not null and t4.value is null then t3.value    
else tt.caption end) as "attr_ipaddr", tt.type_id as "typeId" 
from tf_avmon_alarm_data t1 
inner join td_avmon_mo_info tt on (t1.mo_id=tt.mo_id) 
left join portal_users tu on (t1.confirm_user=tu.user_id or t1.confirm_user=tu.real_name) 
left join td_avmon_mo_info_attribute t3 on (t1.mo_id=t3.mo_id and t3.name='%s') 
left join td_avmon_mo_info_attribute t4 on (t1.mo_id=t4.mo_id and t4.name='%s') 
left join td_avmon_kpi_info t10 on (t1.kpi_code=t10.kpi_code) 
order by tt.type_id) as aa
%s 
group by "mo","attr_ipaddr","typeId" order by "typeId")

union

(

select "mo","attr_ipaddr", "typeId",count(*) as "count"
from 
(
select  a.mo_id as "mo", 
(case when t3.value is null and t4.value is null then a.caption 
     when t3.value='NA' and t4.value is not null then t4.value 
     when t3.value='NA' and t4.value is null then a.caption 
     when t3.value is null and t4.value is not null then t4.value 
     when t3.value is not null and t4.value is null then t3.value 
else t3.value end) as attr_ipaddr,
a.type_id as "typeId",
case when count(*)=1 then 0 end as "count" 
from td_avmon_mo_info a left join tf_avmon_alarm_data b on(a.mo_id=b.mo_id)
left join td_avmon_mo_info_attribute t3 on (a.mo_id=t3.mo_id and t3.name='hostName') 
left join td_avmon_mo_info_attribute t4 on (a.mo_id=t4.mo_id and t4.name='')
where alarm_id is null  
%s
group by "mo","attr_ipaddr","typeId" order by "typeId"
)as bb
%s
group by "mo","attr_ipaddr","typeId" order by "typeId"
)




