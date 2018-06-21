package entities;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Database extends Sim_entity{
	private Sim_port in, serverPort;
	private Sim_negexp_obj delay;
	private Sim_stat stat;
	
	public Database(String name, double average) {
		super(name);
		
		this.in = new Sim_port("In");
		add_port(in);
		
		this.serverPort = new Sim_port("ServerPort");
		add_port(serverPort);
		
		this.delay = new Sim_negexp_obj("Delay", average);
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
			
			sim_trace(1, "Servidor fez uma operação no BD");
			sim_schedule(serverPort, 0.0, 1); 
		}
	}
	
}
