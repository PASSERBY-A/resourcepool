SELECT
	a.obj_name 		 as 	"name",
	status.str_value as 	"status",---kpicode 查询
	a.obj_parent	 as		"host",--kpicode 查询或者vmhost子查询
	usedSpace.num_value as	"usedSpace",---demo data
	hostFre.num_value   as	"hostFre", --kpicode 查询
	hostMem.num_value	as	"hostMem", --kpicode 查询   这个图片上是内存使用量
	hostMemPer.num_value as	"clientMemUsage", --kpicode 查询   这个图片上是内存使用率
	vmMem.str_value	 as	"mem",   --kpicode 查询
	a.mo_id			 as	"uuid"   --kpicode 查询
from 
td_avmon_amp_vm_host a 
left join
	(SELECT l.mo_id,
    	str_value
	  FROM tf_avmon_kpi_value_current c1,
	    tf_avmon_kpi_value_list l
	  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102200010'
  	) status
ON a.mo_id  = status.mo_id
left join
	(SELECT l.mo_id,
    	num_value
	  FROM tf_avmon_kpi_value_current c1,
	    tf_avmon_kpi_value_list l
	  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102200027'
  	) usedSpace
ON a.mo_id  = usedSpace.mo_id

left join
	(SELECT l.mo_id,
    	num_value
	  FROM tf_avmon_kpi_value_current c1,
	    tf_avmon_kpi_value_list l
	  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102200026'
  	) hostFre
ON a.mo_id  = hostFre.mo_id

left join
	(SELECT l.mo_id,
    	num_value
	  FROM tf_avmon_kpi_value_current c1,
	    tf_avmon_kpi_value_list l
	  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102200025'
  	) hostMem
ON a.mo_id  = hostMem.mo_id

left join
	(SELECT l.mo_id,
    	num_value
	  FROM tf_avmon_kpi_value_current c1,
	    tf_avmon_kpi_value_list l
	  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5101202002'
  	) hostMemPer
ON a.mo_id  = hostMemPer.mo_id

left join
	(SELECT l.mo_id,
    	str_value
	  FROM tf_avmon_kpi_value_current c1,
	    tf_avmon_kpi_value_list l
	  WHERE c1.value_key = l.value_key and l.KPI_CODE = '5102200011'
  	) vmMem
ON a.mo_id  = vmMem.mo_id

where a.obj_type = 'VirtualMachine' and  
	 a.agent_id = '%s'