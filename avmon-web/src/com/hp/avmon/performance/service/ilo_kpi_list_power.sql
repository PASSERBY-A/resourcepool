select distinct a.instance as "power",
                (case
                  WHEN e.str_value = '0' then
                   'on'
                  ELSE
                   'off'
                END) as "status",
                (case
                  WHEN f.str_value is null then
                   ' '
                  ELSE
                   f.str_value
                END) as "temp",
                (case
                  WHEN g.str_value = '1' then
                   'on'
                  ELSE
                   'off'
                END) as "alarm"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001001') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 left join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001002') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
 left join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001003') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance
 where a.mo_id = '%s'
 order by a.instance