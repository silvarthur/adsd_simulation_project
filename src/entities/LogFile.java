package entities;
import eduni.simjava.*;

public class LogFile extends Sim_entity{
	private Sim_port in;
	private double delay;
	
	public LogFile(String name, double delay) {
		super(name);
		this.delay = delay;
		
		in = new Sim_port("In"); // Port for receiving events from the source entity.
		add_port(in);
	}
	
	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e); // Get the next event.
			sim_process(this.delay); // Process the event.
			sim_completed(e); // The event has completed service.
		}
	}

}
