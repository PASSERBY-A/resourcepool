select 
	a.pid||'*'||a.id as "id", 
	a.text as "text", 
	a.id as "moId", 
	c.alarm_count as "alarm_count", 
	case when a.agent_status=2 then 'icon-pause' when b.status=1 then iconCls else iconCls||'-b' end as "iconCls", 
	a.leaf as "leaf", 
	a.expanded as "expanded", 
	b.last_update_time as "agentLastUpdateTime", 
	case when a.agent_status=2 then '暂停' when b.status=1 then '正在运行' else '未知' end as "agentStatus", 
	a.qtip as "qtip" 
from STG_AVMON_ALARM_TREE a 
left join (
	select 
		id,
		to_char(last_update_time,'YYYY-MM-DD HH24:MI:SS') as last_update_time, 
		case when last_update_time>(localtimestamp - interval '1 hour') then 1 else 0 end as status 
	from STG_AVMON_AVAILABLE_SUMMARY
	) b on a.id=b.id 
left join (
	select 
		 t1.view_id as mo_id,
		count(1) as alarm_count 
	from STG_AVMON_ALARM_VIEW_MO_MAP t1,TF_AVMON_ALARM_DATA t2 
	where t1.mo_id = t2.mo_id 
	group by t1.view_id 
	union  
	select 
		t3.mo_id,
		count(1) 
	from TF_AVMON_ALARM_DATA t3 
	group by t3.mo_id
	) c on c.mo_id = a.id 