select distinct a.instance as "name",
                (case
                  WHEN e.str_value = '1' then
                   'ok'
                  ELSE
                   'no'
                END) as "alarm",
                (case
                  WHEN f.str_value is null then
                   'NA'
                  ELSE
                   f.str_value
                END) as "caseTemp",
                 (case
                  WHEN g.str_value is null then
                   'NA'
                  ELSE
                   g.str_value
                END) as "vrmStatus",
                 (case
                  WHEN h.str_value is null then
                   'NA'
                  ELSE
                   h.str_value
                END) as "vrmTtlStatus",
                 (case
                  WHEN i.str_value is null then
                   'NA'
                  ELSE
                   i.str_value
                END) as "fanTtlStatus", 
                (case
                  WHEN j.str_value is null then
                   'NA'
                  ELSE
                   j.str_value
                END) as "fanTtlRedundency",
                 (case
                  WHEN k.str_value is null then
                   'NA'
                  ELSE
                   k.str_value
                END) as "tempTtlStatus"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001011') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001010') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001051') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001052') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001053') i on a.mo_id = i.mo_id
                                                and a.instance = i.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001054') j on a.mo_id = j.mo_id
                                                and a.instance = j.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001055') k on a.mo_id = k.mo_id
                                                and a.instance = k.instance   

 where a.mo_id = '%s'
 order by a.instance