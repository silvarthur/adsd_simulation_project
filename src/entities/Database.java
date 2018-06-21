package entities;

import eduni.simjava.*;

public class Database extends Sim_entity{
	private Sim_port in, out;
	private double delay;
	
	public Database(String name, double delay) {
		super(name);
		this.delay = delay;
		
		in = new Sim_port("In"); // Port for receiving events from the server entity.
		out = new Sim_port("Out"); // Port for sending events to the server.
		
		add_port(in);
		add_port(out);
	}
	
	public void body() {
		while (Sim_system.running()) {
	        Sim_event e = new Sim_event();
	        sim_get_next(e); // Get the next event
	        sim_process(this.delay); // Process the event
	        sim_completed(e); // The event has completed service
	        
	        sim_schedule(out, 0.0, 0);
	        sim_pause(this.delay);
		}
	}
}
