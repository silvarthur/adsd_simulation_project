package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Source extends Sim_entity {
	private Sim_port in, out1, out2;
	/*
	 * Utilizando a distribuição de Poisson 
	 * que expressa a probabilidade de uma série de eventos ocorrer num certo período de tempo 
	 * se estes eventos ocorrem independentemente de quando ocorreu o último evento.
	 */
	private Sim_poisson_obj delay;
	
	public Source(String name, double average) {
		super(name);
		this.delay = new Sim_poisson_obj("Delay", average);
		
		add_generator(delay);		
		
		this.in = new Sim_port("In");
		this.out1 = new Sim_port("Out1");
		this.out2 = new Sim_port("Out2");
		
		add_port(in);
		add_port(out1);
		add_port(out2);
	}

	public void body() {
		for(int i = 0; i < 100; i++) {
			sim_schedule(out1, 0.0, 0); // A porta de saída 1 leva a entidade que representa a request.
			sim_schedule(out2, 0.0, 0); // A porta de saída 2 leva ao arquivo de log. 
			
			/*
			Como enviar o evento para a porta certa?
			Neste caso o que é recebido pela porta de entrada deve sempre ser levado para a porta
			de saída 2.
			*/
			
			sim_pause(delay.sample());
		}
	}
		
}
