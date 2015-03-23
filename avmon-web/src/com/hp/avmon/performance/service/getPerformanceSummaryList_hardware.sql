select a.type_id as "id",a.icon_cls as "iconCls",
case when b.max_alarm_level is null then 0 else b.max_alarm_level end 
as "maxAlarmLevel",
a.caption as "text",
case when b.item_count is NULL THEN 0 ELSE b.item_count END
 as "itemCount",
case when b.alarm_count is null then 0 else b.alarm_count end as "alarmCount",
'type' as "nodeType",
a.type_id as "moType"
from TD_AVMON_MO_TYPE a 
left join 
(
	SELECT
		c.parent_id,
		COUNT (DISTINCT a.mo_id) AS item_count,
		COUNT (b.mo_id) AS alarm_count,
		MAX (current_grade) AS max_alarm_level
	FROM
		TD_AVMON_MO_INFO a
	LEFT JOIN TF_AVMON_ALARM_DATA b ON a.mo_id = b.mo_id
	LEFT JOIN TD_AVMON_MO_TYPE c ON a.TYPE_id = c.type_id
	WHERE
		c.parent_id in (SELECT TYPE_ID FROM td_avmon_mo_type WHERE parent_id = 'HARDWARE')
	GROUP BY
		c.parent_id
) b on a.type_id=b.parent_id
where a.parent_id='HARDWARE'
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
where a.type_id=c.type_id and a.type_id='HARDWARE'
