import eduni.simjava.*;

public class Robot extends Sim_entity{
	private Sim_port out;
	private double delay;
	
	public Robot(String name, double delay) {
		super(name);
		this.delay = delay;
		
		out = new Sim_port("Out");
		add_port(out);
	}

	public void body() {
		for(int i = 0; i < 30; i++) {
			sim_schedule(out, 0.0, 0);
			sim_pause(delay);
		}
	}
		
}
