package ahsun.loadbalancer;

import ahsun.provider.Provider;

import java.util.logging.Logger;

import ahsun.client.ClientRequest;

public class RoundRobinLoadBalancer extends  LoadBalancer{

    private static Logger log = Logger.getLogger(RoundRobinLoadBalancer.class.getName()); 
    
    private int currentProvider;

    public RoundRobinLoadBalancer() {
        super();
        this.currentProvider = 0;
    }
    //Providers will be chosen one by one in a round robin way
    @Override
    public String get(ClientRequest request) {
        int maxSize = getActiveProviders().size();
        Provider currentProvider = getActiveProviders().get(this.currentProvider);
        log.info("RoundRobin Load Balancer will pass client request "+request.getId()+" to provider "+currentProvider.getUuid());
        this.currentProvider++;

        if (this.currentProvider == maxSize) {
            this.currentProvider = 0;
        }
        
        return currentProvider.get(request);

    }
    
}
