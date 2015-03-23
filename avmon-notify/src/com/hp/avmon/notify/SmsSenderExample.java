package com.hp.avmon.notify;

import java.util.Map;

public class SmsSenderExample implements SmsSender {

    @Override
    public void init(String wsUrl, String wsMethod) {
        // TODO Auto-generated method stub

    }


    public boolean send(String phoneNo, String content) {
        // TODO Auto-generated method stub
        System.out.println("(Sample Sender, not really send.) sending..."+content);
        return true;
    }


    @Override
    public boolean send(String phoneNo, String content, Map extraParams) {
        // TODO Auto-generated method stub
    	System.out.println("(Sample Sender, not really send.) sending..."+content);
        return true;
    }

}
