select 
a.mo_id as "MO_ID", 
a.caption as "hostName",
m.value as "businessSystem" ,
(case  WHEN "maxlevel" is null then 
                     0
                    ELSE 
                      "maxlevel" 
                  END) as  "maxlevel",
(case  WHEN b.str_value is null then 
                     '0' 
                    ELSE 
                     b.str_value 
                  END) as "monitor", 
(case  WHEN c.str_value is null then 
                     '0' 
                    ELSE 
                     c.str_value 
                  END) as "dbstatus", 
(case  WHEN d.str_value is null then 
                     '0' 
                    ELSE 
                     d.str_value 
                  END)  as "Alertlog", 
(case  WHEN e.str_value is null then 
                     'NO'
                    ELSE 
                     e.str_value 
                  END)  as "get", 
(case  WHEN f.str_value is null then 
                     '0' 
                    ELSE 
                     f.str_value 
                  END) as "pin" ,
                  (case  WHEN g.str_value is null then 
                     '0' 
                    ELSE 
                     f.str_value 
                  END) as "mem" ,
                  (case  WHEN h.str_value is null then 
                     '0' 
                    ELSE 
                     f.str_value 
                  END) as "global" 
  from (select caption, mo_id 
           from td_avmon_mo_info where type_id like %s
          ) a
  inner join        
           (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem' 
           and 
           %s
           ) m on a.mo_id = m.mo_id        
           ------------
           
   left join (select a.mo_id,max(current_grade) as "maxlevel"  from tf_avmon_alarm_data a group by a.mo_id) tt 
  
  on a.mo_id =tt.mo_id 
    left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2102023001') b 
    on a.mo_id = b.mo_id 
    left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2101020001') c 
    on a.mo_id = c.mo_id 
    
        left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2102024002') d 
    on a.mo_id = d.mo_id 
          left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2101001002') e 
    on a.mo_id = e.mo_id 
              left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2101001003')  f 
    on a.mo_id = f.mo_id     
                 left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2101001001')  g
    on a.mo_id = g.mo_id  
           left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '2101001005')  h 
    on a.mo_id = h.mo_id  
    %s
--where a.mo_id = '03a860ab-8f80-4037-9e0d-50129e3136d8'