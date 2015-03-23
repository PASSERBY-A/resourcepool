select 
	a.view_id  as "id",
	a.view_name as "text",
	a.parent_id as "pid",
	'true' as "expanded",
	a.is_dir as "isDir",
	case when a.is_dir=0 then '../../../resources/images/menus/grid.png' else null end as "icon"
from td_avmon_view a 
where 
a.parent_id='%s' 
and (a.create_by='%s' 
or a.view_type='public' 
or (a.view_type='group' and a.create_by in (select user_id from portal_users where dept_id=(select dept_id from portal_users where user_id='%s'))))
order by 
convert_to(a.view_name ,'GBK')