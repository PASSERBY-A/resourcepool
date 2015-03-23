select distinct a.instance as "label",
 e.str_value as "hrStorageIndex",
 f.str_value as "hrStorageType",
 g.str_value as "hrStorageDescr",
 h.str_value as "hrStorageAllocationUnits",
 i.str_value as "hrStorageSize",
 j.str_value as "hrStorageUsed"
  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021779') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021780') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021781') g on a.mo_id = g.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021782') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance

INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021783') i on a.mo_id = i.mo_id
                                                and a.instance = i.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021784') j on a.mo_id = j.mo_id
                                                and a.instance = j.instance


 where a.mo_id = '%s'
 order by a.instance