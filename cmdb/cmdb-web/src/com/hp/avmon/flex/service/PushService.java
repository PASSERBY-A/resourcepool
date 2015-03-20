package com.hp.avmon.flex.service;

import java.util.Random;

import org.springframework.flex.messaging.MessageTemplate;

public class PushService {
	 	private static FeedThread thread;
	    private final  MessageTemplate template;

		public PushService(MessageTemplate template) {
			this.template=template;
	    }
	    public void start() {
	        if (thread == null) {
	            thread = new FeedThread(this.template);
	            thread.start();
	        }
	    }
	    public void stop() {
	        thread.running = false;
	        thread = null;
	    }

	    public static class FeedThread extends Thread {

	        public boolean running = false;

	        private final MessageTemplate template;

	        public FeedThread(MessageTemplate template) {
	            this.template = template;
	        }

	        @Override
	        public void run() {
	        	System.out.println("========================start once======================");
	            this.running = true;
	            Random random = new Random();
	            double initialValue = 35;
	            double currentValue = 35;
	            double maxChange = initialValue * 0.005;

	            while (this.running) {
	                double change = maxChange - random.nextDouble() * maxChange * 2;
	                double newValue = currentValue + change;

	                if (currentValue < initialValue + initialValue * 0.15 && currentValue > initialValue - initialValue * 0.15) {
	                    currentValue = newValue;
	                } else {
	                    currentValue -= change;
	                }

	                this.template.send("simple-feed", new Double(currentValue));

	              //  System.out.println("" + currentValue);

	                try {
	                    Thread.sleep(300);
	                } catch (InterruptedException e) {
	                }

	            }
	        }
	    }
}
