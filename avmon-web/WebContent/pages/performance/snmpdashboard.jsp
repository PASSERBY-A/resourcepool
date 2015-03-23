
<%@ page language="java" pageEncoding="UTF-8"%>
<!-- window begin -->
  <!-- Overlay—detail
      ================================================== -->
     <div style="width: 100%;margin-top: 50px;" id="mainDemoContainer">
        <div id="windowsnmp">
          <div id="windowHeadersnmp" style="display:none; height:0px !important;"> </div>
          <div style="overflow: hidden;" id="windowContentsnmp">
            <div id="tabsnmp">
              <ul style="margin-left: 30px;">
                <li>详细信息</li>
                <li>KPI视图</li>
                <li>KPI趋势查询</li>
                <li>告警列表</li>
                <div onclick="closeFun1()" class="Avmon-white-close Icon16 right"></div>
              </ul>
              <div style="margin:4px">
                <div class="left baseBox3"><!-- 第一列--> 
                  
                  <!-- 第一行第一列
    							  ================================================== -->
                  <div class="baseBox5 left">
                    <div class="BaseInfoPerMain">
                      <div class="left"><img src="${ctx}/resources/images/HPUnix_icon.png" width="32" height="32"></div>
                      <div class="left BasePerBoxSNMP" style="height:170px;">
                        <p class="fB f14px" id="snmpName"></p>
                        <p id="snmpIp"></p>
                        <ul>
                          <li><span class="title">启动时长</span><span class="content" id="hrSystemUptime"></span></li>
                          <li><span class="title">系统时间</span><span class="content" id="hrSystemDate"></span></li>
                          <li><span class="title">初始化加载设备</span><span class="content" id="hrSystemInitialLoadDevice"></span></li>
                          <li><span class="title">初始化加载参数</span><span class="content" id="hrSystemInitialLoadParameters"></span></li>
                          <li><span class="title">用户数量</span><span class="content" id="hrSystemNumUsers"></span></li>
                          <li><span class="title">进程数量</span><span class="content" id="hrSystemProcesses"></span></li>
                          <li><span class="title">最大进程数</span><span class="content" id="hrSystemMaxProcesses"></span></li>
                          <li><span class="title">内存大小</span><span class="content" id="hrMemorySize"></span></li>
                          <div class="blank0"></div>
                        </ul>
                      </div>
                    </div>
                  </div>
                  
                  <!-- 第一行第二列 -->
                  <div class="baseBox5 left">
                    <div class="BaseInfoPerMain" style="height:100%;">
                      <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_DistributeDisk Icon16 marginT8"></b><span class="fB">分区</span></div>
                      <div class="BaseInfoPerBoxE">
                        <ul id="partitiontList">
                        <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个分区</span></p>
                          <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;
width: 26px;">1</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">标识</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">分区ID</span><span class="content darkblue">64</span></li>
                          <li><span class="title">分区大小</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">文件系统索引</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                         </li>
                        <!--  <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center; width: 26px;">9999</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">标识</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">分区ID</span><span class="content darkblue">64</span></li>
                          <li><span class="title">分区大小</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">文件系统索引</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li> -->
                        </ul>
                      </div>
                    </div>
                  </div>
                  
                  <div class="blank0"></div>
                  
                   <!-- 第二行 -->
                  <div class="baseBox6">
                    <div class="BaseInfoPerMain" style="height:100%;">
                      <div class="BaseInfoPerBoxHeader"  style="width: 95%;"><b class="Pos-R Icon_Runsoftware Icon16 marginT8"></b><span class="fB">运行软件</span></div>
                      <div class="BaseInfoPerBoxE" style=" overflow-y:hidden;width: 97%;">
                      <div id="runningGrid"> </div> 
                     
                      </div>
                    </div>
                  </div>
                  
                   <!-- 第三行 -->
                  <div class="baseBox6">
                    <div class="BaseInfoPerMain" style="height:100%;">
                      <div class="BaseInfoPerBoxHeader" style="width: 95%;"><b class="Pos-R Icon_Installedsoftware Icon16 marginT8"></b><span class="fB">安装软件</span></div>
                      <div class="BaseInfoPerBoxE"  style="overflow-y:hidden;width: 97%;">
                      <div id="installedGrid"> </div>                          
                      </div>
                    </div>
                  </div>
                  
                </div>
                <div class="left baseBox4"><!-- 第三列-->
                  
                  <div class="BaseInfoPerMain"><!-- 第三-box1行-->
                    <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_File Icon16 marginT8"></b><span class="fB">文件系统</span></div>
                    <div class="BaseInfoPerBoxE">
                     <ul id="fileList">
                     <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个文件系统</span></p>
                      <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;
width: 26px;">1</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">MOUNT点</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">类型</span><span class="content darkblue">64</span></li>
                          <li><span class="title">存储所应号</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">上次全备份时间</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li>
                        <!--  <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center; width: 26px;">9999</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">MOUNT点</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">类型</span><span class="content darkblue">64</span></li>
                          <li><span class="title">存储所应号</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">上次全备份时间</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li> -->
                        </ul>
                    </div>
                  </div>
                  <div class="blank2"></div>
                  <div class="BaseInfoPerMain"><!-- 第三-box2行-->
                    <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_Storage Icon16 marginT8"></b><span class="fB">存储</span></div>
                    <div class="BaseInfoPerBoxE">
                     <ul id="storageList">
                     <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个存储</span></p>
                      <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;
width: 26px;">1</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">类型</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">描述</span><span class="content darkblue">64</span></li>
                          <li><span class="title">空间大小</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">已用空间</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li>
                         <!-- <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center; width: 26px;">9999</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">类型</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">描述</span><span class="content darkblue">64</span></li>
                          <li><span class="title">空间大小</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">已用空间</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li> -->
                        </ul>
                    </div>
                  </div>
                   <div class="blank2"></div>
                  <div class="BaseInfoPerMain"><!-- 第三-box3行-->
                    <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_PC Icon16 marginT8"></b><span class="fB">设备</span></div>
                    <div class="BaseInfoPerBoxE">
                     <ul id="deviceList">
                     <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个设备</span></p>
                     <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;
width: 26px;">1</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">类型</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">描述</span><span class="content darkblue">64</span></li>
                          <li><span class="title">状态</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">错误信息</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li>
                        <!--  <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center; width: 26px;">9999</span>
                            <div class="left BaseInfoSNMP" style="margin-left:4px;">
                        <ul>
                          <li><span class="title">索引号</span><span class="content darkblue">3.135774S</span></li>
                          <li><span class="title">类型</span><span class="content darkblue">2016</span></li>
                          <li><span class="title">描述</span><span class="content darkblue">64</span></li>
                          <li><span class="title">状态</span><span class="content darkblue">32GB</span></li>
                          <li><span class="title">错误信息</span><span class="content darkblue">V3.135774</span></li>
                        </ul>
                            </div>
                            <div class="blank0"></div>
                          </li> -->
                        </ul>
                    </div>
                  </div>
                </div>
              </div>
              <div> 
                        <!--section two-->
                           <div class="KPINav">
        	                  <div class="left marginR15" id="autoRefreshsnmp"><a href="#"  class="Avmon-button"><b class="R-icon_4 Icon16" style="position:relative;top:3px;"></b>自动刷新</a></div>
         	                  <div class="left" id="manuRefreshsnmp"><a href="#"  class="Avmon-button"><b class="R-icon_1 Icon16" style="position:relative;top:3px;"></b>手动刷新</a></div>
                          </div>
                          <div class="KPIlist">
                             <div id="KPIlistsnmp">Loading</div>
                            <!--  <H3>Group Name</H3>
                             <dl>
         						 <dt><b class="R-icon_3 Icon16" style="position:relative;top:3px;"></b>005-syslog error message(005)</dt>
         						 <dd>
         						    <div id="jqxgrid5">aaaaaaaaaaaaaaa </div>
         						 </dd>
        					 </dl>
                             <dl>
         						 <dt><b class="R-icon_3 Icon16" style="position:relative;top:3px;"></b>013-Host swap information(013)</dt>
         						 <dd>
         						    <div id="jqxgrid3"> bbbbbbbbbbbbbbb</div>
         						 </dd>
        					 </dl>          -->                
                          
                          </div> 
                          <!-- end -->
                        </div>
                        
                        <div>
                             <div class="KPINav">
                          	  <div class="left marginR15"><span class="LineH24 marginR40 left">提醒：KPI指标最多能选择5个。
                          	  </span><span class="LineH24 marginR6 left">Host:</span><span class="LineH24 marginR40 left" id="hostipsnmp">
                          	  </span><span id="jqxWidgetData11" class="left marginR40"></span> <span class=" LineH24 marginR6 left">
                          	  Time:</span><span class="LineH24 marginR6 left"><div id="startDatesnmp"></div></span>
                          	  <span id="startHoursnmp" class="left marginR6"></span>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> -->
                          	  <!-- <span id="jqxWidgetData2" class="left marginR6"></span> -->
                          	  <span class="LineH24 marginR6 left">To</span>
                          	  <span class="LineH24 marginR6 left"><div id="toDatesnmp"></div>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> --></span><span id="endHoursnmp" class="left marginR15"></span></div>
        	                  <div class="left marginR15" id="queryKpisnmp"><a href="#"  class="Avmon-button"><b class="Search_icon2 Icon16" style="position:relative;top:3px;"></b>查询</a></div>

                          </div>
                          <div>
                      		   <div class="W20 left marginL10">
                                  <p class="f16px marginB10 marginT10">指标列表</p>
                      		      <div id="kpigridsnmp" ></div>
                       		  </div>
                       		  <div class="W76 left marginL20">
                                  <p class="f16px marginB10 marginT10">KPI历史趋势</p>
                                  <div style="height:90%; width:100%;overflow-y: scroll;">
                                  <div id="chart10" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:20px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart11" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart12" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart13" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart14" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  </div>
                                  
                                  
                              </div>  
                          </div>                      
                         </div>
                        
                      <div  class="margin10">
                       <!--    <p>右键菜单
                             </p> -->
                           <div class=" paddingL4">
					     <div class="left paddingL4 NavMainBtn">
							<a href="#" class="Avmon-button" id="showWindowButtonsnmp">高级查询</a>
						</div>
						<div class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><span id="batchupdatesnmp">批量更新告警状态</span></a>
						</div> 
					<div class="blank20"></div>
						
					    </div>
                             <div id="alarmGridsnmp"> </div>

                         </div>
                    </div>
                </div>
            </div>

            
        </div>

<!-- window end -->
<script type="text/javascript">

</script>