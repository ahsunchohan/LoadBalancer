package ahsun.loadbalancer;

import java.util.List;
import java.util.Random;

import ahsun.provider.Provider;

public class RandomLoadBalancer extends LoadBalancer {

    public RandomLoadBalancer(){
        super();
    }

    @Override
    public String get(){
        List<Provider> providerList = getProviders();
        int randNum = new Random().nextInt(providerList.size());
        Provider randProvider = providerList.get(randNum);
        return randProvider.get();
    }
    
}
