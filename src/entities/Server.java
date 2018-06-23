package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Server extends Sim_entity{
	private Sim_port inFromRequest, inFromDatabase, outToDatabase, outToRobot;
	
	private Sim_negexp_obj delay;
	
	private Sim_stat stat;
	
	public Server(String name, double average) {
		super(name);
		delay = new Sim_negexp_obj("Delay", average);
		
		this.stat = new Sim_stat();

		this.inFromRequest = new Sim_port("InFromRequest");
		this.inFromDatabase = new Sim_port("InFromDatabase");
		this.outToDatabase = new Sim_port("OutToDatabase");
		this.outToRobot = new Sim_port("OutToRobot");
		
		add_port(inFromRequest);
		add_port(inFromDatabase);
		add_port(outToDatabase);
		add_port(outToRobot);
		
		add_generator(delay);
		
		this.stat.add_measure(Sim_stat.ARRIVAL_RATE);
		this.stat.add_measure(Sim_stat.QUEUE_LENGTH);
		this.stat.add_measure(Sim_stat.WAITING_TIME);
		this.stat.add_measure(Sim_stat.UTILISATION);
		this.stat.add_measure(Sim_stat.RESIDENCE_TIME);
		
		set_stat(stat);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Request arrived at the server.");
			
			try {
				String nameEvent = Sim_system.get_entity(e.get_src()).get_name();
				
				if (nameEvent.equals("Database")) {
					sim_trace(1, "Sending server response to the robot.");
		    		sim_schedule(outToRobot, 0.0, 1);    	
				} else {
					sim_trace(1, "Database.");
		    		sim_schedule(outToDatabase, 0.0, 1);    	
				}
			} catch (eduni.simjava.Sim_exception ex) {
		    	 System.out.println(ex.getMessage());
			}
		}
	}
}
