package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Server extends Sim_entity{
	private Sim_port in, inDb, dbPort, roboPort;
	private Sim_negexp_obj delay;
	private Sim_stat stat;
	
	public Server(String name, double average) {
		super(name);

		this.in = new Sim_port("In");
		add_port(in);
		
		this.inDb = new Sim_port("InDb");
		add_port(inDb);
		
		this.dbPort = new Sim_port("DbPort");
		add_port(dbPort);
		
		this.roboPort = new Sim_port("RoboPort");
		add_port(roboPort);
		
		delay = new Sim_negexp_obj("Delay", average);
		add_generator(delay);
		
		this.stat = new Sim_stat();
		
		this.stat.add_measure(Sim_stat.ARRIVAL_RATE); //Taxa de chegada
		this.stat.add_measure(Sim_stat.QUEUE_LENGTH); //Tamanho da fila
		this.stat.add_measure(Sim_stat.WAITING_TIME); //Tempo de espera
		this.stat.add_measure(Sim_stat.UTILISATION);  //Utilização
		this.stat.add_measure(Sim_stat.RESIDENCE_TIME); //Tempo de resposta
		
		set_stat(stat);
	}

	public void body() {
		
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Requisição chegou no servidor.");
			
			try {
				String nameEvent = Sim_system.get_entity(e.get_src()).get_name();
				
				if (nameEvent.equals("Database")) {
					sim_trace(1, "Enviando reposta do servidor para o robo.");
		    		sim_schedule(roboPort, 0.0, 1);    	
				} else {
					sim_trace(1, "Banco de dados.");
		    		sim_schedule(dbPort, 0.0, 1);    	
				}
			} catch (eduni.simjava.Sim_exception ex) {
		    	 System.out.println(ex.getMessage());
			}
		}
	}
	
}
