package entities;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_uniform_obj;

public class Request extends Sim_entity{
	private Sim_port in;
	private Sim_port out;
	
	private double delay;
	private RequestType type;
	
	public Request(String name, double delay, RequestType type) {
		super(name);
		this.type = type;
		this.delay = delay;
		
		in = new Sim_port("In"); // Port for receiving events from the facade entity.
		out = new Sim_port("Server"); // Port for sending events to the server entity.
		
		add_port(in);
		add_port(out);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e); // Get the next event.
			sim_process(delay); // Process the event.
			sim_completed(e); // The event has completed service.
			
			sim_schedule(out, 0.0, 1);
			sim_trace(1, "Requisição do tipo " + this.getType().toString() + " vai para o servidor\n");
		}
	}
	
	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
}
