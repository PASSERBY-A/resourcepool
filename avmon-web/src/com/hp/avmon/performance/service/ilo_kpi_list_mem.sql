select distinct a.instance as "label",
                (case
                  WHEN e.str_value = 'ok' then
                   'true'
                  ELSE
                   'false'
                END) as "alarm",
                (case
                  WHEN f.str_value is null then
                   'NA'
                  ELSE
                   f.str_value
                END) as "size",
                (case
                  WHEN g.str_value is null then
                   'NA'
                  ELSE
                   g.str_value
                END) as "speed"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002023') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002021') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002022') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance

 where a.mo_id = '%s'
 order by a.instance