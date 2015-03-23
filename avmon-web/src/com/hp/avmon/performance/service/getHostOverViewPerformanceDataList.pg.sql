select 
a.mo_id, 
a.caption as "hostName",
t.value as "businessSystem" ,
(case  WHEN "maxlevel" is null then 
                     0
                    ELSE 
                      "maxlevel" 
                  END) as  "maxlevel",
(case  WHEN b.str_value is null then 
                     '0' 
                    ELSE 
                     b.str_value 
                  END) as "cpuUsg", 
(case  WHEN c.str_value is null then 
                     '0' 
                    ELSE 
                     c.str_value 
                  END) as "memUsg", 
(case  WHEN d.str_value is null then 
                     '0' 
                    ELSE 
                     d.str_value 
                  END)  as "swap", 
(case  WHEN e.str_value is null then 
                     'NO'
                    ELSE 
                     e.str_value 
                  END)  as "syslog", 
(case  WHEN f.str_value is null then 
                     '0' 
                    ELSE 
                     f.str_value 
                  END) as "runtime" ,
 (case  WHEN g.protocal_method is null then 
                     'NA'
                    ELSE 
                     g.protocal_method 
                  END)  as "protocal"
      from (select caption, mo_id 
           from td_avmon_mo_info where type_id like %s
          ) a
  inner join        
           (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem' and 
            %s  ) t on a.mo_id = t.mo_id
          ------------

  --left join (select a.mo_id,max(current_grade) as "maxlevel"  from tf_avmon_alarm_data a group by a.mo_id) tt 
  left join (select a.mo_id,max(current_grade) as "maxlevel"  from tf_avmon_alarm_data a group by a.mo_id) tt 
  
  on a.mo_id =tt.mo_id 
    left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt 
              where a.value_key = tt.value_key 
                and a.kpi_code = '1001011005') b 
    on a.mo_id = b.mo_id 
    left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt
              where a.value_key = tt.value_key 
                and a.kpi_code = '1001010001') c 
    on a.mo_id = c.mo_id 
    
        left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt
              where a.value_key = tt.value_key 
                and a.kpi_code = '1001013003') d 
    on a.mo_id = d.mo_id 
          left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt
              where a.value_key = tt.value_key 
                and a.kpi_code = '1001005001') e 
    on a.mo_id = e.mo_id 
              left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt 
              where a.value_key = tt.value_key 
                and a.kpi_code = '1002001025')  f 
    on a.mo_id = f.mo_id 
    inner join (select  mo_id,protocal_method from td_avmon_mo_info ) g
          on  a.mo_id = g.mo_id
    %s
    order by "maxlevel" desc