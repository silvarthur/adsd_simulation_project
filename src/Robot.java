import eduni.simjava.*;

public class Robot extends Sim_entity{
	private Sim_port out;
	
	public Robot(String name) {
		super(name);
		out = new Sim_port("Out");
		add_port(out);
	}

}
