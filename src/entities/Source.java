package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Source extends Sim_entity {
	private Sim_port outToRobot;
	
	private Sim_poisson_obj delay;
	
	public Source(String name, double average) {
		super(name);
		
		this.delay = new Sim_poisson_obj("Delay", average);

		this.outToRobot = new Sim_port("OutToRobot");
		
		add_port(outToRobot);
		
		add_generator(delay);
	}

	public void body() {
		for(int i = 0; i < 100; i++) {
			sim_schedule(outToRobot, 0.0, 1);
			sim_trace(1, i + " - Request to the robot entity.");
			sim_pause(delay.sample());
		}
	}
		
}
