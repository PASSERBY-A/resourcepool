select 
	max(os) as os, 
	max(osVersion) as osVersion, 
	max(businessSystem) as businessSystem, 
	max(position) as position, 
	max(usage) as usage, 
	max(owner) as owner, 
	max(cpuModel) as cpuModel 
from 
( 
	select 
		case when name='factory' then value else '' end as os, 
		case when name='osVersion' then value else '' end as osVersion, 
		case when name='deviceName' then value else '' end as businessSystem, 
		case when name='position' then value else '' end as position, 
		case when name='usage' then value else '' end as usage, 
		case when name='owner' then value else '' end as owner, 
		case when name='cpuModel' then value else '' end as cpuModel 
	from TD_AVMON_MO_INFO_ATTRIBUTE 
	where mo_id='%s' 
) a