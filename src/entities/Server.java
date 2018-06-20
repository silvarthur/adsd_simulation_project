package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Server extends Sim_entity{
	private Sim_port out1;
	private Sim_port in;
	private Sim_negexp_obj delay;
	
	public Server(String name, double average) {
		super(name);
		in = new Sim_port("In");
		out1 = new Sim_port("Out1");
		
		add_port(in);
		add_port(out1);
		
		delay = new Sim_negexp_obj("Delay", average);
		add_generator(delay);
	}

	public void body() {
		
		while (Sim_system.running()) {
			
			Sim_event e = new Sim_event();
			
			sim_get_next(e);
			
			sim_process(delay.sample());
			
			sim_completed(e);
			
			sim_trace(1, "Requisição chegou no servidor");
		}
	}
	
}
