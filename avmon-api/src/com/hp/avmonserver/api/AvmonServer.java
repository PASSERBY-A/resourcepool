package com.hp.avmonserver.api;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.hp.avmon.utils.AvmonUtils;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.AlarmComment;
import com.hp.avmonserver.entity.AvmonServerConfig;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.MO;
import com.hp.avmonserver.entity.MOStatus;

public class AvmonServer {

	IRemoteService remoteService = null;

	private String avmonServerUrl;

	public String getAvmonServerUrl() {
		return avmonServerUrl;
	}

	public void setAvmonServerUrl(String avmonServerUrl) {
		this.avmonServerUrl = avmonServerUrl;
	}

	public void init(String avmonServerUrl) {
		this.avmonServerUrl = avmonServerUrl;
		try {
			//AvmonUtils.systemOut("AvmonServerInitAvmonServerUrl:"+ avmonServerUrl);
			remoteService = (IRemoteService) Naming.lookup(this.avmonServerUrl);
		} catch (Exception e) {
			AvmonUtils.systemOut("avmonServerUrl=" + avmonServerUrl);
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean checkRemoteService() throws Exception {
		if (remoteService == null) {
			init(avmonServerUrl);
		} else {
			try {
				// AvmonUtils.systemOut("Test avmonServerUrl="+avmonServerUrl);
				remoteService.heartBeat();
				// AvmonUtils.systemOut("Test Avmon Server OK.");

			} catch (Exception e) {
				// AvmonUtils.systemOut("Test Avmon Server Fault.");
				// AvmonUtils.systemOut("AvmonServer exception: " +
				// e.getMessage());
				remoteService = null;
				init(avmonServerUrl);

			}
		}
		// init(avmonServerUrl);
		if (remoteService == null) {
			// throw new
			// Exception("Error:Init AvmonServer fault with "+avmonServerUrl);
			AvmonUtils.systemOut("Error:Init AvmonServer fault with "
					+ avmonServerUrl);
			return false;
		}
		return true;
	}

	public MO getMoById(String moId) {
		try {
			if (checkRemoteService()) {
				return remoteService.getMoById(moId);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}

	/**
	 * 涓嬪彂AMP瀹夎鏂囦欢
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String deployAmpPackage(String agentId, String ampInstId) {
		try {
			if (checkRemoteService()) {
				return remoteService.deployAmpPackage(agentId, ampInstId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 涓嬪彂AMP 瀹炰緥鐨勮皟搴﹂厤缃�
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @param nodeKey
	 *            , moId
	 * @param kpiCode
	 * @param instance
	 *            ,棰勭暀锛岀洰鍓嶅浐瀹氭坊"ALL"
	 * @param schedule
	 *            锛屾牸寮忎负"0,1 * * * * *"
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String deployAmpSchedule(String agentId, String ampInstId,
			String nodeKey, String kpiCode, String instance, String schedule) {
		try {
			if (checkRemoteService()) {
				return remoteService.deployAmpSchedule(agentId, ampInstId,
						nodeKey, kpiCode, instance, schedule);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 涓嬪彂AMP瀹炰緥鐨勫叏閮ㄨ皟搴�
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String deployAmpSchedule(String agentId, String ampInstId) {
		try {
			if (checkRemoteService()) {
				return remoteService.deployAmpSchedule(agentId, ampInstId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 涓嬪彂AMP瀹炰緥鐨勯厤缃枃浠�
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String deployAmpConfig(String agentId, String ampInstId) {
		try {
			if (checkRemoteService()) {
				return remoteService.deployAmpConfig(agentId, ampInstId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 鍚姩Agent
	 * 
	 * @param agentId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String startAgent(String agentId) {
		try {
			if (checkRemoteService()) {
				return remoteService.startAgent(agentId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}
	
	/**
     * 鍗囩骇Agent
     * 
     * @param agentId
     * @return <Code>:<Message>
     * @throws RemoteException
     */
    public String upgradeAgent(String agentId) {
        try {
            if (checkRemoteService()) {
                return remoteService.upgradeAgent(agentId);
            } else {
                return "44:Avmon-Server Connection Error";
            }
        } catch (Exception e) {
            AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
            e.printStackTrace();
            remoteService = null;
        }
        return "99:Unknown Error";
    }

	/**
	 * 鍋滄Agent
	 * 
	 * @param agentId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String stopAgent(String agentId) {
		try {
			if (checkRemoteService()) {
				return remoteService.stopAgent(agentId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 鍚姩Amp 瀹炰緥
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String startAmp(String agentId, String ampInstId) {
		try {
			if (checkRemoteService()) {
				return remoteService.startAmp(agentId, ampInstId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 鍋滄Amp 瀹炰緥
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String stopAmp(String agentId, String ampInstId) {
		try {
			if (checkRemoteService()) {
				return remoteService.stopAmp(agentId, ampInstId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}
	/**
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @param status
	 * @return
	 */
	public String operAgentByStatus(String agentId, String ampInstId,String status) {
		try {
			if (checkRemoteService()) {
				return remoteService.operationAgentByStatus(agentId, ampInstId, status);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 鏇存柊Avmon-Server鐨勭紦瀛�
	 * 
	 * @param cacheType
	 *            锛屾瘮濡傦細MoCache| AmpCache锛屽彲鐢ㄧ殑鏍囪瘑瀛楃涓蹭负锛�? MoCache ? AmpCache ?
	 *            MergeHandler ? TranslateHandler ? FilterHandler ?
	 *            AutoCloseAlarmTask ? CheckThresholdHandler ?
	 *            RouteInspectionParser ? AgentCache ? NotifyHandler ?
	 *            UpgradeHandler ? AgentCache
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String updateCache(String cacheType) {
		try {
			if (checkRemoteService()) {
				return remoteService.updateCache(cacheType);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}
	
	/**
	 * 鐢ㄤ簬server涔嬮棿鐩镐簰璋冪敤
	 * @param cacheType
	 * @return
	 */
	   public String serverUpdateCache(String cacheType) {
		   try {
	            if (checkRemoteService()) {
	                return remoteService.serverUpdateCache(cacheType);
	            } else {
	                return "44:Avmon-Server Connection Error";
	            }
	        } catch (Exception e) {
	            AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	            e.printStackTrace();
	            remoteService = null;
	        }
	        return "99:Unknown Error";
	    }

	/**
	 * 鍒涘缓鏂扮殑MO瀵硅薄锛屽苟鏇存柊鍒皊erver鐨勭紦瀛樹腑
	 * 
	 * @param mo
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String createMo(MO mo) {
		try {
			if (checkRemoteService()) {
				return remoteService.createMo(mo);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 绉婚櫎MO瀵硅薄锛屽苟鏇存柊鍒皊erver鐨勭紦瀛樹腑
	 * 
	 * @param moId
	 * @return <Code>:<Message>
	 * @throws RemoteException
	 */
	public String removeMo(String moId) {
		try {
			if (checkRemoteService()) {
				return remoteService.removeMo(moId);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 鑾峰彇鎸囧畾AMP瀹炰緥(VM绫诲瀷)鐨勮櫄鏈哄垪琛�
	 * 
	 * @param agentId
	 * @param ampInstId
	 * @return 杩斿洖json鏍煎紡鐨勫瓧绗︿覆
	 * @throws RemoteException
	 */
	public String queryVMHostList(String agentId, String ampInstId) {
		try {
			if (checkRemoteService()) {
				return remoteService.queryVMHostList(agentId, ampInstId);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}
	
	/**
	 * 鍙戦�鍛戒护缁檃gent
	 * @param agentId
	 * @param ampInstId
	 * @param cmdCode
	 * @return
	 */
	public String sendCmdToAgent(String agentId, String ampInstId,String cmdCode){
		try {
			if (checkRemoteService()) {
				return remoteService.sendCmdToAgent(agentId, ampInstId,cmdCode);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}

//	/**
//	 * 娣诲姞鏂板憡璀�
//	 * 
//	 * @param alarm
//	 * @return <Code>:<Message>
//	 * @throws RemoteException
//	 */
//	public String newAlarm(Alarm alarm) {
//		try {
//			if (checkRemoteService()) {
//				return remoteService.newAlarm(alarm);
//			} else {
//				return "44:Avmon-Server Connection Error";
//			}
//		} catch (Exception e) {
//			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
//			e.printStackTrace();
//			remoteService = null;
//		}
//		return "99:Unknown Error";
//	}

	/**
	 * 鏇存柊鍛婅
	 * 
	 * @param alarm
	 * @return
	 * @throws RemoteException
	 */
	public String updateAlarm(Alarm alarm) {
		try {
			if (checkRemoteService()) {
				return remoteService.updateAlarm(alarm);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 鍏抽棴鍛婅锛屽皢娲诲姩鍛婅绉昏嚦鍘嗗彶鍛婅涓�
	 * 
	 * @param alarm
	 * @return
	 * @throws RemoteException
	 */
	public String closeAlarm(Alarm alarm) {
		try {
			if (checkRemoteService()) {
				return remoteService.closeAlarm(alarm);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	/**
	 * 娣诲姞鍛婅娉ㄩ噴
	 * 
	 * @param alarmId
	 * @param userId
	 * @param content
	 * @param contentType
	 * @throws RemoteException
	 */
	public String addAlarmComments(String alarmId, String userId,
			String content, String contentType) {
		try {
			if (checkRemoteService()) {
				return remoteService.addAlarmComments(alarmId, userId, content,
						contentType);
			} else {
				return "44:Avmon-Server Connection Error";
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return "99:Unknown Error";
	}

	public String firstDeploySchedule(String agentId) {
		try {
			if (checkRemoteService()) {
				return remoteService.deployScheduleTask(agentId);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}

	public List<byte[]> sendInstallFileContent(String opetId) {
		try {
			if (checkRemoteService()) {
				return remoteService.sendInstallFileContent(opetId);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}
	
	public String testFileSend() {
		try {
			if (checkRemoteService()) {
				return remoteService.testFileSend();
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}

   public Map execute(String operation,Map params) {
        try {
            if (checkRemoteService()) {
                return remoteService.execute(operation,params);
            } else {
                return null;
            }
        } catch (Exception e) {
            AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
            e.printStackTrace();
            remoteService = null;
        }
        return null;
    }
   
   /**
    * use for get PM server list for gateway
    * @return
    */
   public List<AvmonServerConfig> getAvmonServerList(){
       try {
           if (checkRemoteService()) {
               return remoteService.getAvmonServerList();
           } else {
               return null;
           }
       } catch (Exception e) {
           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
           e.printStackTrace();
           remoteService = null;
       }
       return null;
   }
   
   
//   public boolean registerServer(AvmonServerInstance serverInstance) {
//       try {
//           if (checkRemoteService()) {
//               return remoteService.registerServer(serverInstance);
//           } else {
//               return false;
//           }
//       } catch (Exception e) {
//           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
//           e.printStackTrace();
//           remoteService = null;
//       }
//       return false;
//   }
//   
//   public AvmonServerInstance queryAlarmServer() {
//       try {
//           if (checkRemoteService()) {
//               return remoteService.queryAlarmServer();
//           } else {
//               return null;
//           }
//       } catch (Exception e) {
//           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
//           e.printStackTrace();
//           remoteService = null;
//       }
//       return null;
//   }
   
   public boolean newAlarm(Alarm alarm) {
       try {
           if (checkRemoteService()) {
               return remoteService.newAlarm(alarm);
           } else {
               return false;
           }
       } catch (Exception e) {
           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
           e.printStackTrace();
           remoteService = null;
       }
       return false;
   }
   
   public Alarm getAlarm(String alarmId) {
       try {
           if (checkRemoteService()) {
               return remoteService.getAlarmById(alarmId);
           } else {
               return null;
           }
       } catch (Exception e) {
           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
           e.printStackTrace();
           remoteService = null;
       }
       return null;
   }

	public List<String> queryNewAlarmIdList(Date d) {
	    try {
	        if (checkRemoteService()) {
	            return remoteService.queryNewAlarmIdList(d);
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	        e.printStackTrace();
	        remoteService = null;
	    }
	    return null;
	}
	
	   public KpiEvent getKpiEvent(String moId,String kpiCode,String instance) {
	       try {
	           if (checkRemoteService()) {
	               return remoteService.getKpiEvent(moId,kpiCode,instance);
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return null;
	   }
	   
	   public List<KpiEvent> getKpiEventList(String moId,String kpiCode) {
	       try {
	           if (checkRemoteService()) {
	               return remoteService.getKpiEventList(moId,kpiCode);
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return null;
	   }
	   
	   public List<KpiEvent> getKpiEventList(String moId) {
	       try {
	           if (checkRemoteService()) {
	               return remoteService.getKpiEventList(moId);
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return null;
	   }

	public boolean applyMoOwner(String avmonServerId,String moId) {
	       try {
	           if (checkRemoteService()) {
	               return remoteService.applyMoOwner(avmonServerId,moId);
	           } else {
	               return true;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return true;
	}

	public void exchangeKpiEvents(String avmonServerId,List<KpiEvent> kpiList) {
		 try {
	           if (checkRemoteService()) {
	               remoteService.exchangeKpiEvents(avmonServerId,kpiList);
	           } 
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	}

	public void exchangeAlarms(String avmonServerId,List<Alarm> alarmList) {
		 try {
	           if (checkRemoteService()) {
	               remoteService.exchangeAlarms(avmonServerId,alarmList);
	           } 
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	}

	public List<KpiEvent> fetchKpiEvents(String avmonServerId) {
		 try {
	           if (checkRemoteService()) {
	               return remoteService.fetchKpiEvents(avmonServerId);
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return null;
	}

	public List<Alarm> fetchAlarms(String avmonServerId) {
		 try {
	           if (checkRemoteService()) {
	               return remoteService.fetchAlarms(avmonServerId);
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return null;
	}
	
	public void stopServer() {
		 try {
	           if (checkRemoteService()) {
	               remoteService.stopServer();
	           } 
	       } catch (Exception e) {

	       }
	}

	public Map<String, AvmonServerConfig> getMoServerMap() {
		 try {
	           if (checkRemoteService()) {
	               return remoteService.getMoServerMap();
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
	           e.printStackTrace();
	           remoteService = null;
	       }
	       return null;
	}
}
