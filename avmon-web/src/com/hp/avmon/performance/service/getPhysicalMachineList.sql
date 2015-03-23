SELECT hostname   AS "name",
  power.str_value  AS "powerStatus",
  vmlist.str_value   AS "vmlist",
  cpu.num_value   AS "cpu",
  memusage.num_value   AS "memPer",
  mem.num_value       AS "mem"
FROM td_avmon_amp_vm_host h
LEFT JOIN
  (SELECT l.mo_id,
    str_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102100016'
  ) power
ON h.mo_id  = power.mo_id
LEFT JOIN
  (SELECT l.mo_id,
    str_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102100020'
  ) vmlist
ON h.mo_id  = vmlist.mo_id

LEFT JOIN
  (SELECT  l.mo_id,
    num_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5101101006' and l.INSTANCE = 'this'
  ) cpu
ON h.mo_id = cpu.mo_id
LEFT JOIN 
 (SELECT l.mo_id,
    num_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5101102008'
  ) memusage
ON h.mo_id = memusage.mo_id
LEFT JOIN 
 (SELECT l.mo_id,
    num_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102100010'
  ) mem
ON h.mo_id = mem.mo_id

where 
h.obj_type   = 'HostSystem'
AND h.agent_id   ='%s'