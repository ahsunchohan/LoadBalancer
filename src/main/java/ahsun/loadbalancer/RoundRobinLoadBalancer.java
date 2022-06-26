package ahsun.loadbalancer;

import java.util.List;
import java.util.Random;

import ahsun.provider.Provider;


public class RoundRobinLoadBalancer extends  LoadBalancer{

    private int currProvider;

    public RoundRobinLoadBalancer() {
        super();
        this.currProvider = 0;
    }

    @Override
    public String get() {
        int maxSize = getProviders().size();
        Provider currProvider = getProviders().get(this.currProvider);
        this.currProvider++;

        if (this.currProvider == maxSize) {
            this.currProvider = 0;
        }
        
        return currProvider.get();

    }
    
}
