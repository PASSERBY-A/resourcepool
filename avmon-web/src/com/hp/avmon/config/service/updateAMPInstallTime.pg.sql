UPDATE TD_AVMON_AMP_INST SET STATUS = 1,LAST_SCHEDULE_UPDATE_TIME = now() WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'