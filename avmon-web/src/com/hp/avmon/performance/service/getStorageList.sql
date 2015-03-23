select 
	obj_name 	as	"name",
	vmlist.str_value 	as	"vmlist",
	diskType.str_value	as	"diskType",
	diskSize.NUM_VALUE	as	"size",
	freeSpace.NUM_VALUE		as	"freeSpace",
	url.str_value		as	"type"
from 
	td_avmon_amp_vm_host h
LEFT JOIN
  (SELECT l.mo_id,
    c1.str_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102100020'
  ) vmlist
ON h.mo_id  = vmlist.mo_id
LEFT JOIN
  (SELECT l.mo_id,
    c1.str_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102400005'
  ) diskType
ON h.mo_id  = diskType.mo_id
LEFT JOIN
  (SELECT l.mo_id,
    c1.NUM_VALUE
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102400003'
  ) diskSize
ON h.mo_id  = diskSize.mo_id
LEFT JOIN
  (SELECT l.mo_id,
    c1.NUM_VALUE
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102400004'
  ) freeSpace
ON h.mo_id  = freeSpace.mo_id
LEFT JOIN
  (SELECT l.mo_id,
    c1.str_value
  FROM tf_avmon_kpi_value_current c1,
    tf_avmon_kpi_value_list l
  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102400006'
  ) url
ON h.mo_id  = url.mo_id
where 
	obj_type = 'Datastore'
	and agent_id = '%s'