select c.realipvalue as "realip",b.ipvalue as "ip" ,info.mo_id as "agentId", a.os as "os", TO_CHAR(d.kpitime,'MM/DD/YYYY HH24:MI:SS') as "pingTime",d.status as "pingStatus" from TD_AVMON_MO_INFO info left join (select mo_id,value os from TD_AVMON_MO_INFO_ATTRIBUTE where name='os' ) a
on info.mo_id = a.mo_id 
left join (select mo_id,value as ipvalue from TD_AVMON_MO_INFO_ATTRIBUTE where name='ip') b
on info.mo_id = b.mo_id
left join (select mo_id,value as realipvalue from TD_AVMON_MO_INFO_ATTRIBUTE where name='realip') c
on info.mo_id = c.mo_id
left join
(select curr.value as status,curr.kpi_time as kpitime , list.mo_id from tf_avmon_kpi_value_list list,tf_avmon_kpi_value_current curr where list.value_key = curr.value_key and list.kpi_code = '1001022001') d
on info.mo_id = d.mo_id 
where  b.ipvalue is not null and  b.ipvalue != 'null' and b.ipvalue != 'NA'