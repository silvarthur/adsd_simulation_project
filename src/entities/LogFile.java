package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class LogFile extends Sim_entity{
	private Sim_port inFromRobot;
	
	private Sim_negexp_obj delay;
	
	public LogFile(String name, double average) {
		super(name);
		
		delay = new Sim_negexp_obj("Delay", average);

		inFromRobot = new Sim_port("InFromRobot");
		
		add_port(inFromRobot);

		add_generator(delay);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Saving request in the log file.");
		}
	}
	
}
