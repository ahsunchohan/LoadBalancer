package ahsun.loadbalancer;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

import ahsun.provider.Provider;
import ahsun.client.ClientRequest;

public abstract class LoadBalancer {

	private static Logger log = Logger.getLogger(LoadBalancer.class.getName());  

    private List<Provider> registeredProviders;
    private List<Provider> activeProviders;
    public final static int maxProviders = 10;

    public LoadBalancer(){
        this.registeredProviders = new ArrayList<Provider>();
        this.activeProviders = new ArrayList<Provider>();
    }

    public abstract String get(ClientRequest request);

    public int getMaxProviders(){
        return maxProviders;
    }

    public List<Provider> getActiveProviders() {
		return activeProviders;
	}

    public void setActiveProviders(List<Provider> activeProviders) {
		this.activeProviders = activeProviders;
	}

    public List<Provider> getRegisteredProviders() {
		return registeredProviders;
	}

    public void registerProvider(Provider provider) {
		if (!this.registeredProviders.contains(provider) && this.registeredProviders.size() < LoadBalancer.maxProviders) {
			this.registeredProviders.add(provider);
			this.activeProviders.add(provider);
		}
	}

    public void excludeProvider(Provider provider) {
		log.info("remove provider " + provider.getUuid()+" from the active provider list");
		this.activeProviders.remove(provider);
	}

	public void includeProvider(Provider p) {
		this.activeProviders.add(p);
	}
	/**
	 * All the providers will be checked. If any active provider does not respond or respond 'MaxRequestReached', then it will be set to inactive. If any inactive provider responds for 2 times in a row, then it will be set to active.
	 */
    public void heartBeatCheck() {
		for (Provider provider : this.registeredProviders) {
			// check all active providers
			if (this.activeProviders.contains(provider)) {
				ClientRequest request = new ClientRequest(1);
				String response = provider.get(request);
				if (response == null || response.equals("MaxRequestReached")) {
					excludeProvider(provider);
					provider.setHealth(0);
				}
			}
			// check all inactive providers
			if (!this.activeProviders.contains(provider)) {
				ClientRequest request = new ClientRequest(1);
				String response = provider.get(request);
				if (response != null && !response.equals("MaxRequestReached")) {
					if (provider.getHealth() == 0){
						provider.setHealth(1);
						log.info("received response from inactive provider " + provider.getUuid());
						break;
					}

					if (provider.getHealth() == 1) {
						provider.setHealth(2);
						includeProvider(provider);
						log.info("received response from inactive provider " + provider.getUuid()+" again. This provider is active now.");
					}
				}
				// if the load balancer receives null or MaxRequestReached from that provider then set the health of the provider to 0
				else if (provider.getHealth() > 0){
					provider.setHealth(0);
				}
			}
		}
	}

	public String distributeLoad(ClientRequest request){
		for (Provider provider: this.activeProviders) {
			if (provider.getNumRequests() < Provider.maxRequests) {
				return get(request);
			}			
		}
		return "Max Capacity reached.";
	}

}
