import eduni.simjava.Sim_system;
import entities.Database;
import entities.Facade;
import entities.LogFile;
import entities.Request;
import entities.RequestType;
import entities.Robo;
import entities.Server;
import entities.Source;

public class Simulation {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Sim_system.initialise();
		
		Source souce = new Source("Source", 100);
		Robo robo = new Robo("Robo", 100);
		Facade facade = new Facade("Facede", 50, 10);
		Request requestGet = new Request("RequestGET", RequestType.GET);
		Request requestPost = new Request("RequestPOST", RequestType.POST);
		Server server = new Server("Server", 120);
		LogFile logFile = new LogFile("LogFile", 100);
		Database database = new Database("Database", 100);
		
		Sim_system.link_ports("Source", "Out", "Robo", "In");
		Sim_system.link_ports("Robo", "FacadePort", "Facede", "In");
		Sim_system.link_ports("Robo", "LogFilePort", "LogFile", "In");
		
		Sim_system.link_ports("Facede", "Get", "RequestGET", "In");
		Sim_system.link_ports("Facede", "Post", "RequestPOST", "In");
		Sim_system.link_ports("RequestGET", "Server", "Server", "In");
		Sim_system.link_ports("RequestPOST", "Server", "Server", "In");
		
		Sim_system.link_ports("Server", "DbPort", "Database", "In");
		Sim_system.link_ports("Server", "RoboPort", "Robo", "ServePort");
		
		Sim_system.set_trace_detail(false, true, false);
		Sim_system.run();
	}

}
