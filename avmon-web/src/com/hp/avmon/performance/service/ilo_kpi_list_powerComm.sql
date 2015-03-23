select distinct a.instance as "power",
                (case
                  WHEN e.str_value = 'ok' then
                   'on'
                  ELSE
                   'off'
                END) as "powerStatus",
                (case
                  WHEN f.str_value = 'not_redundant' then
                   'on'
                  ELSE
                   'off'
                END) as "powerRedundancyStatus"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001050') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001049') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
 where a.mo_id = '%s'
 order by a.instance