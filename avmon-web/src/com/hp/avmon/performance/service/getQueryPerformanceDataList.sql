select 
a.mo_id as "moId", 
a.type_id as "typeId",
(case when t3.value is null and t4.value is null then a.caption 
     when t3.value='NA' and t4.value is not null then t4.value 
     when t3.value='NA' and t4.value is null then a.caption 
     when t3.value is null and t4.value is not null then t4.value 
     when t3.value is not null and t4.value is null then t3.value 
else t3.value end) as "ip",
a.caption as "hostName",
t.value as "businessSystem"

  from (select caption, mo_id ,type_id
           from td_avmon_mo_info
          ) a
  inner join        
           (
              select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'
           union 
           
select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem' 
           and 
             attr.value not in (
             select businessname as "name" from tf_avmon_biz_dictionary
             ) union all 
            ( select distinct mo_id, 'others' from td_avmon_mo_info_attribute attr 
where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr 
where name='businessSystem')
            )
           ) t on a.mo_id = t.mo_id
  left join (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='ip')t3 on a.mo_id = t3.mo_id
  left join (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='hostName')t4 on a.mo_id = t4.mo_id
%s