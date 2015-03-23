select distinct a.instance as "cpu",
  b.str_value as "ifIndex",
  c.str_value as "ifName",
  d.str_value as "ifAlias",
  e.str_value as "ifType",
  f.str_value as "ifSpeed",
  g.str_value as "ifPhysAddress",
  h.str_value as "ipAdEntAddr",
  i.str_value as "ipAdEntNetMask",
  j.str_value as "ifDescr",
  k.str_value as "ifOutOctets",
  l.str_value as "ifInOctets"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023580') b on a.mo_id = b.mo_id
                                                and a.instance = b.instance

 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023608') c on a.mo_id = c.mo_id
                                                and a.instance = c.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023626') d on a.mo_id = d.mo_id
                                                and a.instance = d.instance

 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023582') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023584') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023585') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200024234') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200024235') i on a.mo_id = i.mo_id
                                                and a.instance = i.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023581') j on a.mo_id = j.mo_id
                                                and a.instance = j.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023596') k on a.mo_id = k.mo_id
                                                and a.instance = k.instance
INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '9200023590') l on a.mo_id = l.mo_id
                                                and a.instance = l.instance
 where a.mo_id = '%s'