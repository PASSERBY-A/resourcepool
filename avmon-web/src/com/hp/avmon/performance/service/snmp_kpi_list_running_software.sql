select distinct a.instance as "label",
 e.str_value as "hrSWRunIndex",
 f.str_value as "hrSWRunName",
 g.str_value as "hrSWRunID",
 h.str_value as "hrSWRunPath",
 i.str_value as "hrSWRunParameters",
 j.str_value as "hrSWRunType",
 k.str_value as "hrSWRunStatus",
 l.str_value as "hrSWRunPerfCPU",
 m.str_value as "hrSWRunPerfMem"
  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021833') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021834') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021835') g on a.mo_id = g.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021836') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance

INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021837') i on a.mo_id = i.mo_id
                                                and a.instance = i.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021838') j on a.mo_id = j.mo_id
                                                and a.instance = j.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021839') k on a.mo_id = k.mo_id
                                                and a.instance = k.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021842') l on a.mo_id = l.mo_id
                                                and a.instance = l.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021843') m on a.mo_id = m.mo_id
                                                and a.instance = m.instance


 where a.mo_id = '%s'
 order by a.instance