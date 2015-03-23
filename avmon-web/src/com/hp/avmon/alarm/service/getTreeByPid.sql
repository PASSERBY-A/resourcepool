select 
	a.pid || '*' || a.id as "id",
	a.text as "text",
	a.id as "moId",
	'' as "icon",
	c.alarm_count as "alarm_count",
	case when a.expanded='false' then 'icon-alarm-node' else iconCls end as "iconCls",
	a.leaf as "leaf",
	a.expanded as "expanded",
	b.last_update_time as "agentLastUpdateTime",
	case when a.agent_status=2 then '暂停' when b.status=1 then '正在运行' else '未知' end as "agentStatus",
	a.qtip as "qtip",
	a.path as "path" 
from STG_AVMON_ALARM_TREE a 
left join (select id,to_char(last_update_time,'YYYY-MM-DD HH24:MI:SS') as last_update_time, case when last_update_time>(sysdate-1/24) then 1 else 0 end as status from STG_AVMON_AVAILABLE_SUMMARY) b on a.id=b.id 
left join (
select a.id,count(1) as alarm_count from STG_AVMON_ALARM_TREE a,STG_AVMON_ALARM_VIEW_MO_MAP b,TF_AVMON_ALARM_DATA c where a.id=b.view_id and b.mo_id=c.mo_id
group by a.id
union all select mo_id,count(1) from TF_AVMON_ALARM_DATA group by mo_id
) c on a.id=c.id
where 
a.pid='%s'
and (a.create_by='%s' 
or a.view_type='null'
or a.view_type='public' 
or (a.view_type='group' and a.create_by in (select user_id from portal_users where dept_id=(select dept_id from portal_users where user_id='%s'))))
order by 
nlssort(a.text ,'NLS_SORT=SCHINESE_STROKE_M')