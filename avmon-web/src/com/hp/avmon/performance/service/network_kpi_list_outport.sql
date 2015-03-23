select distinct a.instance as "port",
  k.str_value as "ifOutOctets",
  l.str_value as "portName" 
from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '42010010014') k on a.mo_id = k.mo_id
                                               and a.instance = k.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '42010010006') l on a.mo_id = l.mo_id
                                                and a.instance = l.instance
 where a.mo_id = '%s' order by "ifOutOctets"  desc  rownum <=5