package ahsun;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import org.junit.Test;


import ahsun.client.ClientRequestHandler;
import ahsun.loadbalancer.LoadBalancer;
import ahsun.loadbalancer.RandomLoadBalancer;
import ahsun.loadbalancer.RoundRobinLoadBalancer;
import ahsun.provider.ProviderHandler;
import ahsun.simulation.Simulation;
import ahsun.simulation.SimulationExecution;

public class SimulationTest {
    private static Logger log = Logger.getLogger(SimulationTest.class.getName());
    Queue<Simulation> simulationQueue = new LinkedList<Simulation>();
    LoadBalancer randomLoadBalancer = new RandomLoadBalancer();
    LoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();
    ProviderHandler providerHandler = new ProviderHandler();
    ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
    SimulationExecution exc = new SimulationExecution(randomLoadBalancer, providerHandler, clientRequestHandler, simulationQueue);
    SimulationExecution exc2 = new SimulationExecution(roundRobinLoadBalancer, providerHandler, clientRequestHandler, simulationQueue);

    /**
     * Test Scenario 1 Random Load Balancer All the providers are running idle
     */
    @Test
    public void testScenario1() {
        log.info("Test Scenario 1 -----------Random Load Balancer 1--------------------------");
        // at time 1: add 3 providers, 2 client requests
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 1, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 1, 1));
        // at time 2: request 0 and 1 call loadbalancer, add 2 client requests
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 2, 0));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 2, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        // at time 3: add 1 client request and call load balancer
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 3, 1));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 2, 2));
        exc.execute();
    }

    /**
     * Test Scenario 2 Random Load Balancer All the Providers are running at maximum capacity. Some requests will be rejected. After adding new providers, new requests can be accepted again.
     */
    @Test
    public void testScenario2() {
        log.info("Test Scenario 2 -----------Random Load Balancer 2--------------------------");
        // at time 1: add 5 providers
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        // at time 2: add 50 requests
        for (int i = 0; i < 50; i++)
            simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        // at time 3 : 50 requests call loadbalancer
        for (int i = 0; i < 50; i++)
            simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 2, i));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 3, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 3, 1));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 50));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 51));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 4, null));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 5, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 5, 1));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 5, 52));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 5, 53));
        exc.execute();
    }

    /**
     * process longer requests
     */
    @Test
    public void testScenario3() {
        // at time 1: add 2 providers
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        // at time 2: add 2 long requests
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 10));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 2, 0));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 20));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 2, 1));
        exc.execute();

    }

    /**
     * test the check method with round robin load balancer
     */
    @Test
    public void testScenario4() {
        // at time 1: add 2 provider
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        simulationQueue.add(new Simulation(Simulation.ADD_PROVIDER, 1, null));
        // at time 2: add 5 requests
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        simulationQueue.add(new Simulation(Simulation.ADD_CLIENT_REQUEST, 2, 1));
        // at time 3 : send 5 requests
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 0));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 1));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 2));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 3));
        simulationQueue.add(new Simulation(Simulation.CLIENT_SEND_REQUEST, 3, 4));
        // at time 4: set one provider to be inactive and check activity of all
        // providers
        simulationQueue.add(new Simulation(Simulation.REMOVE_PROVIDER, 4, 0));
        simulationQueue.add(new Simulation(Simulation.CHECK_PROVIDERS, 4, null));
        // at time 5: check activity of all providers
        simulationQueue.add(new Simulation(Simulation.CHECK_PROVIDERS, 5, null));
        exc2.execute();
    }    
}
