package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Server extends Sim_entity{
	private Sim_port in1;
	private Sim_port in2;
	private Sim_port out;
	private Sim_negexp_obj delay; // Porque Sim_negexp_obj?
	
	public Server(String name, double average) {
		super(name);
		delay = new Sim_negexp_obj("Delay", average);
		add_generator(delay);
		
		
		in1 = new Sim_port("In1"); // Port for receiving events from the request? entity.
		in2 = new Sim_port("In2"); // Port for receiving events from the database entity.
		out = new Sim_port("Out"); // Port for sending events to the source entity.
		
		add_port(in1);
		add_port(in2);
		add_port(out);
	}

	public void body() {
		while (Sim_system.running()) {
			// Já que existe duas portas de entrada, é necessário criar dois objetos Sim_event?
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Requisição chegou no servidor");
			
	        sim_schedule(out, 0.0, 0);
	        //sim_pause(this.delay);
		}
	}
	
}
