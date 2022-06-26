package ahsun.loadbalancer;

import ahsun.provider.Provider;

public class RoundRobinLoadBalancer extends  LoadBalancer{

    private int currentProvider;

    public RoundRobinLoadBalancer() {
        super();
        this.currentProvider = 0;
    }

    @Override
    public String get() {
        int maxSize = getActiveProviders().size();
        Provider currentProvider = getActiveProviders().get(this.currentProvider);
        this.currentProvider++;

        if (this.currentProvider == maxSize) {
            this.currentProvider = 0;
        }

        return currentProvider.get();

    }
    
}
