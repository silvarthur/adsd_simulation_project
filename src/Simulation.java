import eduni.simjava.Sim_system;
import entities.Database;
import entities.Facade;
import entities.LogFile;
import entities.Request;
import entities.RequestType;
import entities.Robot;
import entities.Server;
import entities.Source;

public class Simulation {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Sim_system.initialise();
		
		Source source = new Source("Source", 100);
		Robot robot = new Robot("Robot", 100);
		Facade facade = new Facade("Facade", 50, 10);
		Request getRequest = new Request("GETRequest", RequestType.GET);
		Request postRequest = new Request("POSTRequest", RequestType.POST);
		Server server = new Server("Server", 120);
		LogFile logFile = new LogFile("LogFile", 120);
		Database database = new Database("Database", 120);
		
		Sim_system.link_ports("Source", "OutToRobot", "Robot", "InFromSource");
		Sim_system.link_ports("Robot", "OutToFacade", "Facade", "InFromRobot");
		Sim_system.link_ports("Robot", "OutToLogFile", "LogFile", "InFromRobot");
		
		Sim_system.link_ports("Facade", "OutToGet", "GETRequest", "InFromFacade");
		Sim_system.link_ports("Facade", "OutToPost", "POSTRequest", "InFromFacade");
		Sim_system.link_ports("GETRequest", "OutToServer", "Server", "InFromRequest");
		Sim_system.link_ports("POSTRequest", "OutToServer", "Server", "InFromRequest");
		
		Sim_system.link_ports("Server", "OutToDatabase", "Database", "InFromServer");
		Sim_system.link_ports("Database", "OutToServer", "Server", "InFromDatabase");
		Sim_system.link_ports("Server", "OutToRobot", "Robot", "InFromServer");
		
		Sim_system.set_trace_detail(false, true, false);
		Sim_system.run();
	}

}
