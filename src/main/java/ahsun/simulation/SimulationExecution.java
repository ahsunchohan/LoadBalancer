package ahsun.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;
import java.util.TreeMap;

import ahsun.client.ClientRequest;
import ahsun.client.ClientRequestHandler;
import ahsun.loadbalancer.LoadBalancer;
import ahsun.provider.ProviderHandler;

public class SimulationExecution {
    private static Logger log = Logger.getLogger(SimulationExecution.class.getName());
    private LoadBalancer loadBalancer;
    private ProviderHandler providerHandler;
    private ClientRequestHandler clientRequestHandler;
    private Queue<Simulation> simulationQueue;
    private TreeMap<Integer, List<ClientRequest>> requestFinishedTime;

    public SimulationExecution(LoadBalancer loadBalancer, ProviderHandler providerHandler, ClientRequestHandler clientRequestHandler, Queue<Simulation> simulationQueue) {
		this.loadBalancer = loadBalancer;
		this.providerHandler = providerHandler;
		this.clientRequestHandler = clientRequestHandler;
		this.simulationQueue = simulationQueue;
		this.requestFinishedTime = new TreeMap<Integer, List<ClientRequest>>();
	}

    public void execute() {
        // assume time is a integer and starts with 1
        int startTime = 1;

        while (!simulationQueue.isEmpty()) {
            Simulation simulation = simulationQueue.poll();
            startTime = simulation.getStartTime();

            // check if any request is finished
            if (requestFinishedTime.containsKey(startTime)) {
                finishRequest(startTime);
            }

            switch (simulation.getSimulation()) {
                case 0: {
                    clientRequestHandler.addNewRequest((int) simulation.getParam());
                    break;
                }
                case 1: {
                    ClientRequest req = clientRequestHandler.getRequests().get((int) simulation.getParam());
                    req.sendRequest(loadBalancer);
                    int finishTime = startTime + req.getDuration();
                    if (!requestFinishedTime.containsKey(finishTime)) {
                        List<ClientRequest> idList = new ArrayList<ClientRequest>();
                        idList.add(req);
                        requestFinishedTime.put(finishTime, idList);
                    } else {
                        List<ClientRequest> idList = requestFinishedTime.get(finishTime);
                        idList.add(req);
                    }
                    break;
                }
                case 2: {
                    providerHandler.addProviderToLoadBalancer(loadBalancer);
                    break;
                }
                case 3: {
                    providerHandler.removeProviderFromLoadBalancer(loadBalancer, (int) simulation.getParam());
                    break;
                }
                case 4: {
                    loadBalancer.heartBeatCheck();
                    break;
                }
            }
        }
        // check if there is still any unfinished requests
        Set<Integer> keys = requestFinishedTime.keySet();
        for (Integer key : keys)
            if (key > startTime) {
                finishRequest(key);
            }
    }

    private void finishRequest(int time) {
        List<ClientRequest> reqList = requestFinishedTime.get(time);
        for (ClientRequest req : reqList) {
            log.info("##################  Client Request " + req.getId() + " get response from the provider "+ req.getResponse() + "##################");
        }
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public ProviderHandler getProviderHandler() {
        return providerHandler;
    }

    public void setProviderHandler(ProviderHandler providerHandler) {
        this.providerHandler = providerHandler;
    }

    public Queue<Simulation> getSimulationQueue() {
        return simulationQueue;
    }

    public void setSimulationQueue(Queue<Simulation> simulationQueue) {
        this.simulationQueue = simulationQueue;
    }
}
