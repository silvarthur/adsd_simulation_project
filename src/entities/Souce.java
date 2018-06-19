package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Souce extends Sim_entity {
	private Sim_port out;
	/**
	 * Utilizando a distribuição de poisson 
	 * que expressa a probabilidade de uma série de eventos ocorrer num certo período de tempo 
	 * se estes eventos ocorrem independentemente de quando ocorreu o último evento
	 */
	private Sim_poisson_obj delay;
	
	public Souce(String name, double average) {
		super(name);
		
		this.delay = new Sim_poisson_obj("Delay", average);
		add_generator(delay);		
		
		this.out = new Sim_port("Out");
		add_port(out);
	}

	public void body() {
		for(int i = 0; i < 100; i++) {
			sim_schedule(out, 0.0, 0);
			
			sim_trace(1, "Nova solicitação ao facede\n");
			
			sim_pause(delay.sample());
		}
	}
		
}
