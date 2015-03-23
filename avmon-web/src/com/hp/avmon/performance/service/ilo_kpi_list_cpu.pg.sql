select distinct a.instance as "cpu",
                (case WHEN b.str_value is null then ' ' ELSE b.str_value END) as "cpuTemp",
                --b.str_value as "cpuTemp",
                (case                 
                  WHEN c.str_value = '1' then
                  	'true'
                  ELSE
                   'false'
                END) as "tempStatus",
                (case
                  WHEN d.str_value is null then
                   'NA'
                  ELSE
                   d.str_value
                END) as "procStatus",
                (case
                  WHEN e.str_value is null then
                   'NA'
                  ELSE
                   e.str_value
                END) as "procMaxThread",
                (case
                  WHEN f.str_value is null then
                   'NA'
                  ELSE
                   f.str_value
                END) as "procInstrutionSet",
                (case
                  WHEN g.str_value is null then
                   'NA'
                  ELSE
                   g.str_value
                END) as "procFrequency"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 left join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001002') b on a.mo_id = b.mo_id
                                                and a.instance = b.instance

 left join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001005') c on a.mo_id = c.mo_id
                                                and a.instance = c.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002042') d on a.mo_id = d.mo_id
                                                and a.instance = d.instance

 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002018') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002019') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201002020') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance
 where a.mo_id = '%s'