select 
       t.os            as "os",
       t.os_version    as "osVersion",
       t.agent_file    as "agentFile",
       t.agent_version as "agentVersion"
  from td_avmon_agent_upgrade_files t