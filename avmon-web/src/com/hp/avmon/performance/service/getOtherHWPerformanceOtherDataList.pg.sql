select 
a.mo_id as "MO_ID", 
a.caption as "hostName",
a.type_id as "type",
t.value as "businessSystem" ,
(case  WHEN "count" is null then 
                     0
                    ELSE 
                      "count" 
                  END) as  "count",
(case  WHEN b.str_value is null then 
                     'NA' 
                    ELSE 
                     b.str_value 
                  END) as "status", 
(case  WHEN c.str_value is null then 
                     'NA' 
                    ELSE 
                     c.str_value 
                  END) as "version", 
(case  WHEN d.str_value is null then 
                     'NA' 
                    ELSE 
                     d.str_value 
                  END)  as "powerstatus",
(case  WHEN e.protocal_method is null then 
                     'NA' 
                    ELSE 
                     e.protocal_method 
                  END) as "protocal"
  from (select caption, mo_id ,type_id
           from td_avmon_mo_info where  parent_id = 'HARDWARE' and type_id not in ('HARDWARE_HP','HARDWARE_DELL')
          ) a
  inner join        
           (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem' 
           and 
            attr.value not in (select businessname as "name" from tf_avmon_biz_dictionary) union all 
            ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr 
where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr 
where name='businessSystem'))
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
                and a.kpi_code = '5201002053') c 
    on a.mo_id = c.mo_id 
    
        left join (select a.mo_id, a.instance, tt.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current tt 
              where a.value_key = tt.value_key 
                and a.kpi_code = '5201001050') d 
    on a.mo_id = d.mo_id 
          inner join (select  mo_id,protocal_method from td_avmon_mo_info ) e
          on  a.mo_id = e.mo_id
     %s
     order by "count" desc
