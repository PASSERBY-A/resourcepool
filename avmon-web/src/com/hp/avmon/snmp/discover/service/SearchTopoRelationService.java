package com.hp.avmon.snmp.discover.service;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class SearchTopoRelationService
{
//  DeviceInfoManager deviceManager;
//  List<LinkInfo> allLinkList = new Vector();
//
//  public List<LinkInfo> createAllLinks(List<DeviceInfo> paramList){
//    System.out.println(new Timestamp(System.currentTimeMillis()));
//    this.deviceManager = new DeviceInfoManager(paramList);
//    List localList1 = this.deviceManager.switchList;
//    Vector localVector = new Vector();
//    Iterator localIterator1 = localList1.iterator();
//    Object localObject;
//    while (localIterator1.hasNext())
//    {
//      localObject = (DeviceInfo)localIterator1.next();
//      localVector.add(localObject);
//      if (DiscoverConfig.isDebug)
//      {
//        System.out.println("\n=============================");
//        System.out.println(localObject);
//      }
//      List localList2 = ((DeviceInfo)localObject).getPortInfoList();
//      Iterator localIterator2 = localList2.iterator();
//      while (localIterator2.hasNext())
//      {
//        PortInfo localPortInfo1 = (PortInfo)localIterator2.next();
//        checkPortLeafDevice(localPortInfo1);
//        List localList3 = localPortInfo1.subDeviceList;
//        Iterator localIterator3 = localList3.iterator();
//        while (localIterator3.hasNext())
//        {
//          DeviceInfo localDeviceInfo = (DeviceInfo)localIterator3.next();
//          if (localVector.contains(localDeviceInfo))
//            continue;
//          List localList4 = localDeviceInfo.getPortInfoList();
//          Iterator localIterator4 = localList4.iterator();
//          while (localIterator4.hasNext())
//          {
//            PortInfo localPortInfo2 = (PortInfo)localIterator4.next();
//            checkTwoPortLink(localPortInfo1, localPortInfo2);
//          }
//        }
//      }
//    }
//    System.out.println("=============================");
//    System.out.println(new Timestamp(System.currentTimeMillis())  + this.allLinkList.size());
//    localIterator1 = this.allLinkList.iterator();
//    while (localIterator1.hasNext())
//    {
//      localObject = (LinkInfo)localIterator1.next();
//      System.out.println(localObject);
//    }
//    return (List<LinkInfo>)this.allLinkList;
//  }
//
//  private void addLink(LinkInfo paramLinkInfo)
//  {
//    if (this.allLinkList.contains(paramLinkInfo))
//      return;
//    ListIterator localListIterator = this.allLinkList.listIterator();
//    while (localListIterator.hasNext())
//    {
//      LinkInfo localLinkInfo = (LinkInfo)localListIterator.next();
//      int i = localLinkInfo.checkFuzzyOrAccurate(paramLinkInfo);
//      if (i == 1)
//      {
//        localListIterator.remove();
//        if (!DiscoverConfig.isDebug)
//          continue;
//        System.out.println(+ localLinkInfo + paramLinkInfo);
//      }
//      else
//      {
//        if (i != 0)
//          continue;
//        if (DiscoverConfig.isDebug)
//          System.out.println(paramLinkInfo);
//        return;
//      }
//    }
//    if (DiscoverConfig.isDebug)
//      System.out.println( paramLinkInfo);
//    this.allLinkList.add(paramLinkInfo);
//  }
//
//  private void checkPortLeafDevice(PortInfo paramPortInfo)
//  {
//    Object localObject1;
//    Object localObject2;
//    Object localObject3;
//    if (paramPortInfo.subDeviceList.size() == 1)
//    {
//      localObject1 = (DeviceInfo)paramPortInfo.subDeviceList.get(0);
//      localObject2 = new PortInfo();
//      ((PortInfo)localObject2).device = ((DeviceInfo)localObject1);
//      ((PortInfo)localObject2).port = -1;
//      localObject3 = creatLink(paramPortInfo, (PortInfo)localObject2, true);
//      if (DiscoverConfig.isDebug)
//        System.out.println(localObject3);
//      addLink((LinkInfo)localObject3);
//      return;
//    }
//    if (paramPortInfo.portMacList.size() == 1)
//    {
//      localObject1 = (String)paramPortInfo.portMacList.get(0);
//      if (this.deviceManager.getDeviceInfoByMAC((String)localObject1) == null)
//      {
//        localObject2 = this.deviceManager.getIPOfMac((String)localObject1);
//        if (localObject2 != null)
//        {
//          localObject3 = new DeviceInfo();
//          ((DeviceInfo)localObject3).setDeviceIP((String)localObject2);
//          ((DeviceInfo)localObject3).setDeviceMAC((String)localObject1);
//          PortInfo localPortInfo = new PortInfo();
//          localPortInfo.device = ((DeviceInfo)localObject3);
//          localPortInfo.port = -1;
//          LinkInfo localLinkInfo = creatLink(paramPortInfo, localPortInfo, false);
//          if (DiscoverConfig.isDebug)
//            System.out.println(localLinkInfo);
//          addLink(localLinkInfo);
//        }
//      }
//    }
//  }
//
//  private void checkTwoPortLink(PortInfo paramPortInfo1, PortInfo paramPortInfo2)
//  {
//    DeviceInfo localDeviceInfo1 = paramPortInfo1.device;
//    List localList1 = paramPortInfo1.subDeviceList;
//    DeviceInfo localDeviceInfo2 = paramPortInfo2.device;
//    List localList2 = paramPortInfo2.subDeviceList;
//    if ((localList1.contains(localDeviceInfo2)) && (localList2.contains(localDeviceInfo1)))
//    {
//      ArrayList localArrayList = new ArrayList();
//      Object localObject2 = localList1.iterator();
//      Object localObject1;
//      while (((Iterator)localObject2).hasNext())
//      {
//        localObject1 = (DeviceInfo)((Iterator)localObject2).next();
//        if (!localList2.contains(localObject1))
//          continue;
//        localArrayList.add(localObject1);
//      }
//      if (localArrayList.size() <= 0)
//      {
//        localObject1 = creatLink(paramPortInfo1, paramPortInfo2, true);
//        if (DiscoverConfig.isDebug)
//          System.out.println(localObject1);
//        addLink((LinkInfo)localObject1);
//      }
//      else if (localArrayList.size() == 1)
//      {
//        localObject1 = (DeviceInfo)localArrayList.get(0);
//        localObject2 = new PortInfo();
//        ((PortInfo)localObject2).device = ((DeviceInfo)localObject1);
//        ((PortInfo)localObject2).port = -1;
//        LinkInfo localLinkInfo1 = creatLink(paramPortInfo1, (PortInfo)localObject2, true);
//        LinkInfo localLinkInfo2 = creatLink(paramPortInfo2, (PortInfo)localObject2, true);
//        if (DiscoverConfig.isDebug)
//        {
//          System.out.println(localLinkInfo1);
//          System.out.println(localLinkInfo2);
//        }
//        addLink(localLinkInfo1);
//        addLink(localLinkInfo2);
//      }
//    }
//  }
//
//  private LinkInfo creatLink(PortInfo paramPortInfo1, PortInfo paramPortInfo2, boolean paramBoolean)
//  {
//    DeviceInfo localDeviceInfo1 = paramPortInfo1.device;
//    int i = paramPortInfo1.port;
//    DeviceInfo localDeviceInfo2 = paramPortInfo2.device;
//    int j = paramPortInfo2.port;
//    LinkInfo localLinkInfo = new LinkInfo();
//    if (!paramBoolean)
//      localLinkInfo.linkType = LinkInfo.LinkTypeOutbound;
//    if ((localDeviceInfo1.getDeviceIP().compareTo(localDeviceInfo2.getDeviceIP()) <= 0) || (!paramBoolean))
//    {
//      localLinkInfo.startDevice = localDeviceInfo1;
//      localLinkInfo.startPortInfo = paramPortInfo1;
//      localLinkInfo.endDevice = localDeviceInfo2;
//      localLinkInfo.endPortInfo = paramPortInfo2;
//      localLinkInfo.startPort = i;
//      localLinkInfo.endPort = j;
//    }
//    else
//    {
//      localLinkInfo.startDevice = localDeviceInfo2;
//      localLinkInfo.startPortInfo = paramPortInfo2;
//      localLinkInfo.endDevice = localDeviceInfo1;
//      localLinkInfo.endPortInfo = paramPortInfo1;
//      localLinkInfo.startPort = j;
//      localLinkInfo.endPort = i;
//    }
//    return localLinkInfo;
//  }
}
