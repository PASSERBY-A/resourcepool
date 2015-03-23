
<%@ page language="java" pageEncoding="UTF-8"%>
<!-- sel window -->
<div style="width: 100%; height: 650px; margin-top: 50px;" id="windowseldemo">
      <div id="windowsel">
        <div id="windowHeader"> <span class="Icon16  R-icon_1_white"></span><span class="f14px white paddingL20">SEL(system event log)</span> </div>
        <div>
          <div class="KPINav">
            <div class="left marginR15"><span class="LineH24 marginR6 marginL10 left">类别</span><span class="LineH24 marginR20 left">
              <!-- <input type="text" placeholder="类别" style="width:140px; height:26px;"> -->
              <div id="seltype"></div>
              </span><span class=" LineH24 marginR6 left">发生时间</span><span class="LineH24 marginR6 left">
             <div id="seldate"></div>
              </span>
				<span class="LineH24 marginR6 marginL10 left">内容</span><span class="LineH24 marginR20 left">
              <input id="selcontent" type="text" placeholder="类别" style="width:230px; height:26px;">
              </span>
              </div>
            <div class="left marginR15"><a id="querysel" href="#"  class="Avmon-button"><b class="Search_icon2 Icon16" style="position:relative;top:3px;"></b>查询</a></div>
          </div>
          <div style="margin:10px;">
            <div class="blank0"></div>
            
            <div class="overlay-El-line">
                <div id="selgrid"> </div>
            </div>
          </div>
          <div class="blank5"></div>
          <div class="AvmonButtonArea">
            <!-- <div class="left"><a href="#"  class="AvmonOverlayButton">OK</a><a href="#"  class="AvmonOverlayButton">Cancel</a></div>
            --> <div class="blank0"></div>
          </div>
        </div>
      </div>
    </div>
<!-- sel window -->
<!-- window begin -->
  <!-- Overlay—detail
      ================================================== -->
      <div style="width: 100%;margin-top: 50px;" id="ipmiDemo">
        <div id="windowiloipmi">
          <div id="windowHeaderipmi" style="display:none; height:0px !important;"> </div>
          <div style="overflow: hidden;" id="windowContentipmi">
            <div id="tabipmi">
              <ul style="margin-left: 30px;">
                <li>详细信息</li>
                <li>KPI视图</li>
                <li>KPI趋势查询</li>
                <li>告警列表</li>
                <div onclick="closeFun1()" class="Avmon-white-close Icon16 right"></div>
              </ul>
              <div style="margin:4px">
                <div class="left baseBox1"><!-- 第一列--> 
                  
                  <!-- 第一列—box1
    							  ================================================== -->
                  <div class="BaseInfoPerMain">
                    <div class="left"><img src="${ctx}/resources/images/IPMI_icon.png" width="32" height="32"></div>
                    <div class="left BasePerBoxilo">
                      <p class="fB f14px" id="productName">IPMINAME</p>
                      <p id="ipmiforhostName">IP 192.168.255.255</p> 
                      <ul>
                        <li><span class="title">主板-序列号</span><span class="content" id="boardSerialNumber"></span></li>
                        <li><span class="title">主板-厂商名称</span><span class="content" id="boardManufacturer"></span></li>
                      </ul>
                      <div class="blank10"></div>
                      <ul>
                        <li><b id="selcount"  class="Icon_status_red Icon16 Pos-R left"></b>
                        <span class="left darkblue" style="width:160px; cursor:pointer">SEL(system event log)</span>
                        <span  class="content left LineH20" style="width:60px !important;; cursor:pointer" id="count"></span></li>
                      </ul>
                    </div>
                  </div>
                  
                  <!-- 第二列—服务器正面
    							  ================================================== -->
                  <div class="BaseInfoPerMain">
                    <div class="left" style="width:32px; height:32px;"></div>
                    <div class="left BasePerBoxilo">
                      <div class="left BasePerBoxilo-left">
                        <div class="BasePerBoxilo-left-box"></div>
                        <div style=" margin-top:20px; margin-left:10px;"><img src="${ctx}/resources/images/VGA.png" width="20" height="40"></div>
                      </div>
                      <div class="left BasePerBoxilo-center">
                        <div class="left margin10">
                          <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 fB">电源状态</span></div>
                          <!-- <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">开启</span></div>
                          </div> -->
                         <!--  <div class="blank0"></div>
                          <div class="marginT10 marginL25">
                            <div id="jqxSwitchButton" style="cursor:pointer"></div>
                          </div> -->
                        </div> 
                         <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 fB">电源故障</span></div>
                          <!-- <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">故障</span></div>
                          </div> -->
                        </div>
                         <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 fB">电源过载</span></div>
                         <!--  <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div> -->
                        </div>
                        <div class="left marginR15">
                          <!-- <div class="margin10"><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 fB">重启操作</span></div>
                           <div class="marginT10 marginL25">
                             <div class="left marginR15"><a href="#" class="restart-btn">重启服务器</a></div>
                          </div> -->
                        </div>                                 
                       </div>
                      </div>
                      <div class="left BasePerBoxilo-right">
                        <div class="BasePerBoxilo-right-box"></div>
                        <div style=" margin-top:10px; margin-left:10px;"> <img src="${ctx}/resources/images/USB.png" width="20" height="10">
                          <div style="margin-top:10px;"><span>UID</span><span style="margin-left:2px;"><img src="${ctx}/resources/images/status_blue.png" width="16" height="16"></span></div>
                          <div style="margin-top:10px;"><img src="${ctx}/resources/images/IPMI_Logo2.png" width="22" height="60"></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <!-- 第三列—服务器背面
    							  ================================================== -->
                  <div class="BaseInfoPerMain">
                    <div class="left" style="width:32px; height:32px;"></div>
                    <div class="left BasePerBoxilo">
                      <div class="left BasePerBoxilo-left-back">
                        <div class="BasePerBoxilo-left-box-back"></div>
                        <div class="BasePerBoxilo-left-box-back" style="bottom:4px; position:absolute"></div>
                      </div>
                      <div class="left BasePerBoxilo-center-back">
                        <div class="left marginR6">
                          <div class="margin10"><b class="Pos-R Icon_Motherboard Icon16 marginT8"></b><span class="marginL8 fB">主板温度</span></div>
                          <!-- <div id="gauge1" class="left"></div>
                          <div class="left margin10">
                            <div class="Number-Main">45</div>
                            <div class="Extra-Main">℃</div>
                            <div class="Number-title">当前温度</div>
                          </div> -->
                        </div>
                        <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 fB">VRM状态</span></div>
                         <!--  <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div> -->
                        </div>
                         <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_ErrorLog Icon16 marginT8"></b><span class="marginL8 fB">Enclosure状态</span></div>
                         <!--  <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div> -->
                        </div>
                         <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_LED Icon16 marginT8"></b><span class="marginL8 fB">LED灯状态</span></div>
                         <!--  <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div> -->
                        </div>
                      </div>
                      <div class="left BasePerBoxilo-right-back">
                        <div class="BasePerBoxilo-right-box-back"></div>
                        <div class="BasePerBoxilo-left-box-back" style="bottom:4px; position:absolute"></div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="left baseBox" style="width:27%;"><!-- 第五列-->
                  
                  <div id="organic-tabsipmi" class="BaseInfoPerMain">
                    <ul id="explore-nav-IPMI" class="left BaseInfoPerBoxT">
                      <li id="ex-titlesCpuipmi"><a rel="titlesCpuipmi" href="#" class="current"><span>CPU温度</span></a></li>
                      <li id="ex-titlePCIipmi"><a rel="titlePCIipmi" href="#"><span>插槽温度</span></a></li>
                      <li id="ex-titleMemoryipmi"><a rel="titleMemoryipmi" href="#"><span>内存</span></a></li>
                      <li id="ex-titleother"><a rel="titleother" href="#"><span>其他</span></a></li>
                    </ul>
                    <div id="all-list-wrapipmi">
                      <ul id="titlesCpuipmi">
                        <!-- <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个CPU</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_CPU Icon16"></b></div>
                          <div class="left"><span class="fB" id="">CPU</span>
                            <div>
                              <div id="gauge22" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main" id="currentTemp1"></div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>  -->
                      </ul>
                      <ul id="titlePCIipmi">
					  <!-- <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个插槽</span></p>
                       <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div>
                          <div class="left"><span class="fB">插槽</span>
                            <div>
                              <div id="gauge33" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main" id="dimm_temp"></div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>         -->      
                      </ul>
                      <ul id="titleMemoryipmi">
                      <!--  <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个Memory</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_MeM Icon16"></b></div>
                          <div class="left"> <span class="fB">PROC_12567_DRMM3</span>
                            <div>
                              <div id="gauge44" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main" id="memory_temp"></div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                                <div class="marginT10 left"><b id="memory_status" class="Icon_status_green Icon16 Pos-R left"></b><span id="memory_status_text" class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>   -->
                      </ul>
                      <ul id="titleother">
                       <!--  <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">6</span><span>个其他选项</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Fan Icon16"></b></div>
                          <div class="left"><span class="fB">Fan</span>
                          <div>
                              <div class="left marginT10">
                                <div class="Number-Main" id="VirtualFan"></div>
                                <div class="Extra-Main">/转</div>
                                <div class="Number-title">转速</div>
                              </div>
                              </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Power Icon16"></b></div>
                          <div class="left"> <span class="fB">Power</span>
                          <div>
                              <div class="left marginT10">
                                <div class="Number-Main" id="PowerMeter"></div>
                                <div class="Extra-Main">W</div>
                                <div class="Number-title">电源功率</div>
                              </div>
                              </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                        
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Temperature Icon16"></b></div>
                          <div class="left"> <span class="fB" >扣卡温度</span>
                            <div>
                              <div id="gauge55" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main" id="mezz_temp"></div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Temperature Icon16"></b></div>
                          <div class="left"> <span class="fB">Iocontroller温度</span>
                            <div>
                              <div id="gauge66" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main" id="Iocntroller_temp"></div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Temperature Icon16"></b></div>
                          <div class="left"> <span class="fB">进风口温度</span>
                            <div>
                              <div id="gauge77" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main" id="inlet_ambient_temp"></div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>                       
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Fan Icon16"></b></div>
                          <div class="left"><span class="fB">机箱</span>
                         	 <div>
                              <div class="left marginT10 marginR25">
                                <div class="Number-Main" id="max_voltage_value"></div>
                                <div class="Extra-Main">v</div>
                                <div class="Number-title">最高电压值</div>
                              </div>
                              <div class="left marginT10">
                                <div class="Number-Main" id="min_voltage_value"></div>
                                <div class="Extra-Main">v</div>
                                <div class="Number-title">最低电压值</div>
                              </div>
                              </div>
                              <div class="blank10"></div>
                              <div>
                              <div class="left marginT10 marginR25">
                                <div class="Number-Main" id="max_current_value"></div>
                                <div class="Extra-Main">A</div>
                                <div class="Number-title">最高电流值</div>
                              </div>
                              <div class="left marginT10">
                                <div class="Number-Main" id="min_current_value"></div>
                                <div class="Extra-Main">A</div>
                                <div class="Number-title">最低电流值</div>
                              </div>
                              </div>
                          </div>
                          <div class="blank0"></div>
                        </li> -->
                      </ul>
                    </div>
                    <!-- END List Wrap --> 
                    
                  </div>
                </div>
              </div>
              <div>
                        <!--section two-->
                           <div class="KPINav">
        	                  <div class="left marginR15" id="autoRefreshipmi"><a href="#"  class="Avmon-button"><b class="R-icon_4 Icon16" style="position:relative;top:3px;"></b>自动刷新</a></div>
         	                  <div class="left" id="manuRefreshipmi"><a href="#"  class="Avmon-button"><b class="R-icon_1 Icon16" style="position:relative;top:3px;"></b>手动刷新</a></div>
                          </div>
                          <div class="KPIlist">
                             <div id="iloKPIlistipmi">Loading</div>
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
                          	  </span><span class="LineH24 marginR6 left">Host:</span><span class="LineH24 marginR40 left" id="hostipipmi">
                          	  </span><span id="jqxWidgetData11" class="left marginR40"></span> <span class=" LineH24 marginR6 left">
                          	  Time:</span><span class="LineH24 marginR6 left"><div id="startDateipmi"></div></span>
                          	  <span id="startHouripmi" class="left marginR6"></span>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> -->
                          	  <!-- <span id="jqxWidgetData2" class="left marginR6"></span> -->
                          	  <span class="LineH24 marginR6 left">To</span>
                          	  <span class="LineH24 marginR6 left"><div id="toDateipmi"></div>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> --></span><span id="endHouripmi" class="left marginR15"></span></div>
        	                  <div class="left marginR15" id="queryKpiipmi"><a href="#"  class="Avmon-button"><b class="Search_icon2 Icon16" style="position:relative;top:3px;"></b>查询</a></div>

                          </div>
                          <div>
                      		   <div class="W20 left marginL10">
                                  <p class="f16px marginB10 marginT10">指标列表</p>
                      		      <div id="kpigridipmi" ></div>
                       		  </div>
                       		  <div class="W76 left marginL20">
                                  <p class="f16px marginB10 marginT10">KPI历史趋势</p>
                                  <div style="height:90%; width:100%;overflow-y: scroll;">
                                  <div id="chart10" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart11" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart12" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart13" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart14" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  </div>
                                  
                                  
                              </div>  
                          </div>                      
                         </div>
                        
                      <div  class="margin10">
                       <!--    <p>右键菜单
                             </p> -->
                           <div class=" paddingL4">
					     <div class="left paddingL4 NavMainBtn">
							<a href="#" class="Avmon-button" id="showWindowButtonipmi">高级查询</a>
						</div>
						<div class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><span id="batchupdateipmi">批量更新告警状态</span></a>
						</div> 
					<div class="blank20"></div>
						
					    </div>
                             <div id="alarmGridipmi"> </div>

                         </div>
                    </div>
                </div>
            </div>

            
        </div>

<!-- window end -->
<script type="text/javascript">

</script>