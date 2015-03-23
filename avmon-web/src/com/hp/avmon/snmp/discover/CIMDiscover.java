package com.hp.avmon.snmp.discover;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.security.auth.Subject;
import javax.wbem.client.WBEMClient;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.sblim.cimclient.discovery.AdvertisementCatalog;
import org.sblim.cimclient.discovery.Discoverer;
import org.sblim.cimclient.discovery.DiscovererFactory;
import org.sblim.cimclient.discovery.WBEMProtocol;
import org.sblim.cimclient.discovery.WBEMServiceAdvertisement;
import org.sblim.slp.Locator;
import org.sblim.slp.ServiceLocationException;
import org.sblim.slp.ServiceLocationManager;
import org.sblim.slp.ServiceType;
import org.sblim.slp.ServiceURL;

import com.hp.avmon.webservice.EventInfoServiceManager;

public class CIMDiscover {
	private static final org.apache.commons.logging.Log logger = LogFactory.getLog(EventInfoServiceManager.class);
	public static void main(String[] args) throws Exception{
		CIMDiscover cimDiscover =  new CIMDiscover();
		
		cimDiscover.findServices();
	}
	public void discover() throws Exception {

		Subject subject = new Subject();
		WBEMProtocol[] protocols = new WBEMProtocol[2];

		protocols[0] = new WBEMProtocol("https", "cim-xml");
		protocols[1] = new WBEMProtocol("http", "cim-xml");

		String[] directoryAgents = new String[] { "16.159.216.66" };
		Discoverer discoverer = DiscovererFactory
				.getDiscoverer(DiscovererFactory.SLP);
		WBEMServiceAdvertisement[] advertisements = discoverer
				.findWbemServices(directoryAgents);
		AdvertisementCatalog catalog = new AdvertisementCatalog();
		catalog.addAdvertisements(advertisements);

		String[] ids = catalog.getKnownIds();
		for (String id : ids) {
			WBEMServiceAdvertisement adv = catalog.getAdvertisement(id,
					protocols);
			WBEMClient client = adv.createClient(subject,
					Locale.getAvailableLocales());
		}
	}

	public void test1() {
		final Locator locator;
		try {
			Vector<InetAddress> daaddress = new Vector<InetAddress>();
			InetAddress inetaddress = InetAddress
					.getByName("16.159.216.66");
			daaddress.add(inetaddress);
			locator = ServiceLocationManager.getLocator(Locale.US);
			Vector<String> scopes = new Vector<String>();
			scopes.add("DEFAULT");
			Enumeration<?> enumeration = locator.findServices(new ServiceType(
					"service:wbem"), scopes, "", daaddress);

			while (enumeration.hasMoreElements()) {
				Object srvurl = enumeration.nextElement();
				if (srvurl instanceof ServiceURL) {
					System.out.println("Service URL: " + srvurl);
					Enumeration<Object> attrenum = locator.findAttributes(
							(ServiceURL) srvurl, scopes, new Vector<String>(),
							daaddress);
					while (attrenum.hasMoreElements()) {
						System.out.println("Service Attribute: "
								+ attrenum.nextElement());
					}
				}
			}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}
	}
	
	public void findServices(){
		final Locator locator;
		String name =null;
		try {
			locator = ServiceLocationManager.getLocator(Locale.US);

			Vector<String> scopes = new Vector<String>();
			scopes.add("default");
			
			String query = "(&(AgentName=" + ((name != null) ? name : "*") + "))";
			ServiceType servicetype = new ServiceType("service:wbem:https");
			Enumeration<?> enumeration = locator.findServices(servicetype, scopes, query);

			while (enumeration.hasMoreElements()) {
				System.out.println(enumeration.nextElement());
			}

		} catch (ServiceLocationException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}
	}
	
	public void findServiceTypes(){
		try {

			Locator locator = ServiceLocationManager.getLocator(Locale.US);

			Vector<String> scopes = new Vector<String>();
			scopes.add("default");

			Enumeration<?> enumeration = locator.findServiceTypes("*", scopes);

			while (enumeration.hasMoreElements()) {
				System.out.println(enumeration.nextElement());
				logger.debug(enumeration.nextElement());
			}

		} catch (ServiceLocationException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}
	}
	
	public void findAttributes(){
		try {
			Locator locator = ServiceLocationManager.getLocator(Locale.US);

			Vector<String> scopes = new Vector<String>();
			scopes.add("default");

			ServiceURL servicetype = new ServiceURL("service:wbem:https://1.1.1.1:5989", -1);

			Vector<String> attributeIds = new Vector<String>();
			Enumeration<?> enumeration = locator.findAttributes(servicetype, scopes, attributeIds);

			while (enumeration.hasMoreElements()) {
				System.out.println(enumeration.nextElement());
				logger.debug(enumeration.nextElement());
			}

		} catch (ServiceLocationException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}
	}

}
