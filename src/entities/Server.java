package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Server extends Sim_entity{
	private Sim_port in1, in2, out1, out2;
	private double delay; // Porque Sim_negexp_obj?
	
	public Server(String name, double average) {
		super(name);
		this.delay = delay;
		
		in1 = new Sim_port("In1"); // Port for receiving events from the request? entity.
		in2 = new Sim_port("In2"); // Port for receiving events from the database entity.
		out1 = new Sim_port("Out1"); // Port for sending events to the source entity.
		out2 = new Sim_port("Out2"); // Port for sending events to the database entity.
		
		add_port(in1);
		add_port(in2);
		add_port(out1);
		add_port(out2);
	}

	public void body() {
		while (Sim_system.running()) {
			// Já que existe duas portas de entrada, é necessário criar dois objetos Sim_event?
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay);
			sim_completed(e);
			
			sim_trace(1, "Requisição chegou no servidor");
			
	        sim_schedule(out1, 0.0, 0);
	        sim_pause(this.delay);
		}
	}
	
}
