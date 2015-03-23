select distinct a.instance as "label",
 e.str_value as "hrPartitionIndex",
 f.str_value as "hrPartitionLabel",
 g.str_value as "hrPartitionID",
 h.str_value as "hrPartitionSize",
 i.str_value as "hrPartitionFSIndex"
  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021817') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021795') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
                                                INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021818') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021819') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance

                                                INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021820') i on a.mo_id = i.mo_id
                                                and a.instance = i.instance
 where a.mo_id = '%s'
 order by a.instance