SELECT *
FROM 
	(SELECT 
		VALUE AS XP_DEV_IOPS, 
		PATH AS PATH 
	    FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
	    WHERE mo_id = '%s' AND KPI_CODE = 'xp_dev_iops' AND AMP_INST_ID = 'wk_xpdev_1003'
	) a
	LEFT JOIN
	(SELECT 
		VALUE AS XP_DEV_MS, 
		PATH
		FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
        WHERE mo_id = '%s' AND KPI_CODE = 'xp_dev_ms' AND AMP_INST_ID = 'wk_xpdev_1003') b ON a.PATH = b.PATH
