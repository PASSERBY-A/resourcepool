package com.hp.avmon.notify;

import java.util.Map;

public interface SmsSender {
    public void init(String wsUrl,String wsMethod);
    public boolean send(String phoneNo,String content,Map extraParams);
}
