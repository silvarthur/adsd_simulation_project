import eduni.simjava.Sim_system;
import entities.Database;
import entities.Facade;
import entities.LogFile;
import entities.Request;
import entities.RequestType;
import entities.Server;
import entities.Source;

public class Simulation {
	public static void main(String[] args) {
		Sim_system.initialise(); // Initializes Sim_system.
		Source source = new Source("Source", 100);
		Facade facade = new Facade("Facade", 50, 10);
		LogFile logFile = new LogFile("LogFile", 10);
		Request getRequest = new Request("GETRequest", 10, RequestType.GET);
		Request postRequest = new Request("POSTRequest", 10, RequestType.POST);
		Server server = new Server("Server", 120);
		Database databse = new Database("Database", 10);
		
		Sim_system.link_ports("Source", "Out1", "Facade", "In");
		Sim_system.link_ports("Source", "Out2", "LogFile", "In");
		
		Sim_system.link_ports("Facade", "Get", "GETRequest", "In");
		Sim_system.link_ports("Facade", "Post", "POSTRequest", "In");
		
		Sim_system.link_ports("GETRequest", "Out", "Server", "In1");
		Sim_system.link_ports("POSTRequest", "Out", "Server", "In1");
		
		Sim_system.link_ports("Server", "Out1", "Source", "In");
		Sim_system.link_ports("Server", "Out2", "Database", "In");
		
		Sim_system.link_ports("Database", "Out", "Server", "In2");

		Sim_system.set_trace_detail(false, true, false);

		Sim_system.run(); // Executes the simulation.
	}
}
