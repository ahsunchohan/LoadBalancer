package ahsun.loadbalancer;

import java.util.List;
import java.util.ArrayList;

import ahsun.provider.Provider;

public abstract class LoadBalancer {
    private List<Provider> registeredProviders;
    private List<Provider> activeProviders;
    private final static int maxProviders = 10;

    public LoadBalancer(){
        this.registeredProviders = new ArrayList<Provider>();
        this.activeProviders = new ArrayList<Provider>();
    }

    public abstract String get();

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

    public void excludeProvider(Provider p) {
		this.activeProviders.remove(p);
	}

	public void includeProvider(Provider p) {
		this.activeProviders.add(p);
	}

    public void heartBeatCheck() {
		for (Provider provider : this.registeredProviders) {
			if (this.activeProviders.contains(provider)) {
				String response = provider.get();
				if (response == null || response.equals("MaxRequestReached")) {
					excludeProvider(provider);
					provider.setHealth(false);
				}
			}
		}
	}

}
