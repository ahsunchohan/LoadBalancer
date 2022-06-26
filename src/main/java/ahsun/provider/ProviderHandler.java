package ahsun.provider;

import java.util.ArrayList;
import java.util.List;

import ahsun.loadbalancer.LoadBalancer;

public class ProviderHandler {

    private List<Provider> providers;
	
	public ProviderHandler() {
		this.providers = new ArrayList<Provider>();
	}
	
	public void addProviderToLoadBalancer(LoadBalancer loadBalancer) {
		Provider provider = new Provider(loadBalancer);
		this.providers.add(provider);
		provider.register();
		System.out.println("new Provider "+provider.getUuid()+" added");
	}
	
	public void removeProviderFromLoadBalancer(LoadBalancer loadBalancer, int providerIndex) {
		Provider provider = this.providers.get(providerIndex);
		loadBalancer.excludeProvider(provider);
	}
    
}
