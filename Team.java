import java.util.ArrayList;
import java.util.List;

public class Team {

	public String firstname1; 
	public String lastname1;
	public String firstname2;
	public String lastname2;
	public String school;
	public String teamCode;
	
	public int fixThis;
	
	public int wins;
	public int losses;
	public int rounds;
	public int speaks1;
	public int speaks2;
	
	public int speaksTotal;
	
	public int timesPro;
	public int timesCon;
	public int byes;
	
	public int paired;
	
	public List<Team> opponents = new ArrayList<Team>();
	public List<Judge> judges = new ArrayList<Judge>();

	public int oppWins;
		
}
