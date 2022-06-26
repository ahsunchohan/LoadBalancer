package ahsun.simulation;

public class Simulation {
    public static int ADD_CLIENT_REQUEST = 0;
    public static int CLIENT_SEND_REQUEST = 1;
    // provider handler simulation
    public static int ADD_PROVIDER = 2;
    public static int REMOVE_PROVIDER = 3;
    // load balancer simulation
    public static int CHECK_PROVIDERS = 4;
    // provider simulation

    private int startTime;
    private Object param;
    private int simulation;

    public Simulation(int simulation, int startTime, Object param) {
		this.simulation = simulation;
		this.startTime = startTime;
		this.param = param;
	}

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public int getSimulation() {
        return simulation;
    }

    public void setSimulation(int simulation) {
        this.simulation = simulation;
    }
}
