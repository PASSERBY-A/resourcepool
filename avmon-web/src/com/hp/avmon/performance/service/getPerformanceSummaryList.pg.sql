select a.type_id as "id",a.icon_cls as "iconCls",
case when b.max_alarm_level is null then 0 else b.max_alarm_level end 
as "maxAlarmLevel",
a.caption as "text",
b.item_count as "itemCount",
case when b.alarm_count is null then 0 else b.alarm_count end as "alarmCount",
'type' as "nodeType",
a.type_id as "moType"
from TD_AVMON_MO_TYPE a 
left join 
(
select a.type_id,count(distinct a.mo_id) as item_count,count(b.mo_id) as alarm_count,max(current_grade) as max_alarm_level from TD_AVMON_MO_INFO a left join TF_AVMON_ALARM_DATA b on a.mo_id=b.mo_id
%s
group by a.type_id
) b on a.type_id=b.type_id
where %s
union all
select a.mo_id,
c.icon_cls,
case when b.max_alarm_level is null then 0 else b.max_alarm_level end ,
a.caption,
1,
case when b.alarm_count is null then 0 else b.alarm_count end,
'instance',
a.type_id
from TD_AVMON_MO_INFO a left join (
select mo_id,max(current_grade) as max_alarm_level,count(1) as alarm_count from TF_AVMON_ALARM_DATA 
group by mo_id
) b on a.mo_id=b.mo_id
,TD_AVMON_MO_TYPE c
where a.type_id=c.type_id and %s
