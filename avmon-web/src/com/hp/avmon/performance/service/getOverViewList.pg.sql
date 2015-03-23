select hostname as "hostName",obj_type as "hostType",
(case  WHEN tt."maxlevel" is null then 
                     0
                    ELSE 
                      tt."maxlevel" 
                  END) as  "maxlevel",
(case  WHEN cpu.str_value is null then 
                     '0' 
                    ELSE 
                     cpu.str_value 
                  END) as "cpuUsage",
(case  WHEN mem.str_value is null then 
                     '0' 
                    ELSE 
                     mem.str_value 
                  END) as "memUsage"
from td_avmon_amp_vm_host a
left join (select a.mo_id,max(current_grade) as "maxlevel"  from tf_avmon_alarm_data a group by a.mo_id) tt 
on a.mo_id=tt.mo_id
left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '5101201001') cpu
on a.mo_id=cpu.mo_id
left join (select a.mo_id, a.instance, b.str_value 
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b 
              where a.value_key = b.value_key 
                and a.kpi_code = '5101202002') mem
on a.mo_id=mem.mo_id
where a.enable_flag=1
and a.amp_inst_id='%s'