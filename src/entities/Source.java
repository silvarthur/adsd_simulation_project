package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Source extends Sim_entity {
	private Sim_port out;
	private Sim_poisson_obj delay;
	
	public Source(String name, double average) {
		super(name);
		
		this.delay = new Sim_poisson_obj("Delay", average);
		add_generator(delay);		
		
		this.out = new Sim_port("Out");
		add_port(out);
	}

	public void body() {
		for(int i = 0; i < 100; i++) {
			sim_schedule(out, 0.0, 1);
			sim_trace(1, "Solicitação ao robo " + i);
			sim_pause(delay.sample());
		}
	}
		
}
