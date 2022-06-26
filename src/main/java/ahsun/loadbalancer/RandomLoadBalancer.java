package ahsun.loadbalancer;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ahsun.client.ClientRequest;

import ahsun.provider.Provider;

public class RandomLoadBalancer extends LoadBalancer {

    private static Logger log = Logger.getLogger(RandomLoadBalancer.class.getName()); 
    
    public RandomLoadBalancer(){
        super();
    }
    // Providers will be chosen here randomly
    @Override
    public String get(ClientRequest request){
        List<Provider> providerList = getActiveProviders();
        int randNum = new Random().nextInt(providerList.size());
        Provider randProvider = providerList.get(randNum);
        log.info("Random Load Balancer will pass client request "+request.getId()+" to provider "+randProvider.getUuid());
        return randProvider.get(request);
    }
    
}
