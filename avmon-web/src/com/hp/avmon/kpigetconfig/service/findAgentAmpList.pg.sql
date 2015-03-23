select
ag.AMP_ID as "ampId",
ag.agent_id as "agentId",
ag.amp_inst_id as "ampInstId",
ag.amp_version as "ampVersion",
ag.caption as "caption",
ag.enable_flag as "enableFlg",
ag.last_active_time as "lastActiveTime",
ag.schedule as "schedule",
ag.status as "status",
upper(amp.type) as "ampType"
from TD_AVMON_AMP_INST ag
left join TD_AVMON_AMP amp
on ag.amp_id=amp.amp_id