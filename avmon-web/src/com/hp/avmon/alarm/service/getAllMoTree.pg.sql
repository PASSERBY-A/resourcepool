select a.pid||'*'||a.id as "id", 
         a.text as "text", 
         a.id as "moId", 
         a.alarm_count as "alarm_count", 
         case when a.agent_status=2 then 'icon-pause' when b.status=1 then iconCls else iconCls||'-b' end as "iconCls", 
         a.leaf as "leaf", 
         a.expanded as "expanded", 
         b.last_update_time as "agentLastUpdateTime", 
         case when a.agent_status=2 then '暂停' when b.status=1 then '正在运行' else '未知' end as "agentStatus", 
         a.qtip as "qtip"  
         from STG_AVMON_ALARM_TREE a  
         left join (select id,to_char(last_update_time,'YYYY-MM-DD HH24:MI:SS') as last_update_time, case when last_update_time>(localtimestamp - interval '1 hour') then 1 else 0 end as status from STG_AVMON_AVAILABLE_SUMMARY) b  
         on a.id=b.id  
         order by convert_to(a.text ,'GBK')