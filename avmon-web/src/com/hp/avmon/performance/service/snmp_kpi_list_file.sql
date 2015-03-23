select distinct a.instance as "label",
 e.str_value as "hrFSTypes",
 f.str_value as "hrFSIndex",
 g.str_value as "hrFSMountPoint",
 h.str_value as "hrFSType",
 i.str_value as "hrFSAccess",
 j.str_value as "hrFSBootable",
 k.str_value as "hrFSStorageIndex",
 l.str_value as "hrFSLastFullBackupDate"
  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021912') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021823') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021824') g on a.mo_id = g.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021806') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance

INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021825') i on a.mo_id = i.mo_id
                                                and a.instance = i.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021826') j on a.mo_id = j.mo_id
                                                and a.instance = j.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021827') k on a.mo_id = k.mo_id
                                                and a.instance = k.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200021828') l on a.mo_id = l.mo_id
                                                and a.instance = l.instance

 where a.mo_id = '%s'
 order by a.instance