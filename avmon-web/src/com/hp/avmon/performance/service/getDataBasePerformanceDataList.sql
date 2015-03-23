select 
a.mo_id, 
a.value as "hostName", 
"count",
(case  WHEN b.str_value is null then 
                     '0%' 
                    ELSE 
                     b.str_value 
                  END) as "cpuUsg", 
(case  WHEN c.str_value is null then 
                     '0%' 
                    ELSE 
                     c.str_value 
                  END) as "memUsg", 
(case  WHEN d.str_value is null then 
                     '0%' 
                    ELSE 
                     d.str_value 
                  END)  as "syslog", 
(case  WHEN e.str_value is null then 
                     'NO' 
                    ELSE 
                     e.str_value 
                  END)  as "swap", 
(case  WHEN f.str_value is null then 
                     '0' 
                    ELSE 
                     f.str_value 
                  END) as "runtime" 
  from ((select value, mo_id 
           from td_avmon_mo_info_attribute 
          where name = 'hostName')) a 
          ------------
  left join (select a.mo_id,count(*) as "count"  from tf_avmon_alarm_data a group by a.mo_id) tt 
  on a.mo_id =tt.mo_id 
                ------------
    left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '1001011005') b 
    on a.mo_id = b.mo_id 
    left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '1001010001') c 
    on a.mo_id = c.mo_id 
    
        left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '1001013003') d 
    on a.mo_id = d.mo_id 
          left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '1001005001') e 
    on a.mo_id = e.mo_id 
              left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '1002001025')  f 
    on a.mo_id = f.mo_id     
--where a.mo_id = '03a860ab-8f80-4037-9e0d-50129e3136d8'