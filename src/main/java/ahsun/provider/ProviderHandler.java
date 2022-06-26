package ahsun.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ahsun.loadbalancer.LoadBalancer;
/**
 * Provider Handler can add or remove a provider, which simulates the server being switched or off in the real life
 */
public class ProviderHandler {

	private static Logger log = Logger.getLogger(ProviderHandler.class.getName());
    private List<Provider> providers;
	
	public ProviderHandler() {
		this.providers = new ArrayList<Provider>();
	}
	
	public void addProviderToLoadBalancer(LoadBalancer loadBalancer) {
		Provider provider = new Provider(loadBalancer);
		this.providers.add(provider);
		provider.register();
		log.info("new Provider "+provider.getUuid()+" added");
	}
	
	public void removeProviderFromLoadBalancer(LoadBalancer loadBalancer, int providerIndex) {
		Provider provider = this.providers.get(providerIndex);
		loadBalancer.excludeProvider(provider);
		log.info("Provider "+provider.getUuid()+" is removed");
	}
    
}
