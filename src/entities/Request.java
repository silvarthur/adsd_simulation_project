package entities;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_uniform_obj;

public class Request extends Sim_entity{
	private Sim_port inFromFacade, outToServer;
	
	private Sim_uniform_obj delay;
	
	private Sim_stat stat;
	
	private RequestType type;
	
	public Request(String name, RequestType type) {
		super(name);
		this.type = type;
		
		this.delay = new Sim_uniform_obj("Delay", 0, 1);

		this.stat = new Sim_stat();
		
		this.inFromFacade = new Sim_port("InFromFacade");
		this.outToServer = new Sim_port("OutToServer");
		
		add_port(inFromFacade);
		add_port(outToServer);
		
		add_generator(delay);
		
		this.stat.add_measure(Sim_stat.ARRIVAL_RATE);
		this.stat.add_measure(Sim_stat.QUEUE_LENGTH);
		this.stat.add_measure(Sim_stat.WAITING_TIME);
		this.stat.add_measure(Sim_stat.UTILISATION);
		this.stat.add_measure(Sim_stat.RESIDENCE_TIME);
		
		set_stat(stat);
		
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Request of type " + this.getType().toString() + "going to the server.");
			sim_schedule(outToServer, 0.0, 1);
		}
	}
	
	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
}
