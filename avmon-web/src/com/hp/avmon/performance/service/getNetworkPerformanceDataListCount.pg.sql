select 
count(*)
from (select caption, mo_id ,type_id
           from td_avmon_mo_info where type_id like %s
          ) a
  inner join        
           (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem' 
           and 
            %s
           ) t on a.mo_id = t.mo_id
  left join (select a.mo_id,count(*) as "count"  from tf_avmon_alarm_data a group by a.mo_id) tt 
  on a.mo_id =tt.mo_id 
    left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt 
              where a.value_key = tt.value_key 
                and a.kpi_code = '1001022001') b 
    on a.mo_id = b.mo_id 
    left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt
              where a.value_key = tt.value_key 
                and a.kpi_code = '42010010003') c 
    on a.mo_id = c.mo_id 
          inner join (select  mo_id,protocal_method from td_avmon_mo_info ) e
          on  a.mo_id = e.mo_id
     %s