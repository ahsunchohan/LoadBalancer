package ahsun.loadbalancer;

import java.util.List;
import java.util.ArrayList;

import ahsun.provider.Provider;

public abstract class LoadBalancer {
    private List<Provider> providers;
    public final static int maxProviders = 10;

    public LoadBalancer(){
        this.providers = new ArrayList<Provider>();
    }

    public int getMaxProviders(){
        return maxProviders;
    }

    public List<Provider> getProviders() {
		return providers;
	}

    public abstract String get();


}
