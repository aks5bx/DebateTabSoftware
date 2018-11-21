import java.io.BufferedReader;
import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabSoftware {

	public static void main(String[] args) {
		
		// Store the teams and judges and rooms
		List<String> teamStrings = new ArrayList<String>();
		List<Matchup> Round1Pairings = new ArrayList<Matchup>();
		List<Matchup> Round2Pairings = new ArrayList<Matchup>();
		List<Matchup> Round3Pairings = new ArrayList<Matchup>();
		List<String> judgeStrings = new ArrayList<String>();
		List<String> rooms = new ArrayList<String>();
		List<Team> teams = new ArrayList<Team>();
		List<Judge> judges = new ArrayList<Judge>();

		
		// Manually enter room numbers
		rooms.add("Room1"); rooms.add("Room2"); rooms.add("Room3"); rooms.add("Room4"); rooms.add("Room5");
		rooms.add("Room6"); rooms.add("Room7"); rooms.add("Room8"); rooms.add("Room9"); rooms.add("Room10");
		
		
		// Read in the teams
		try {
			File file = new File("roster.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				teamStrings.add(line);

			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// List out teams and fill out their value
		for (String teamLine : teamStrings) {
			Team x = new Team();
			String[] values = teamLine.split(",", 0);
			String speaker1 = values[0];
			String speaker2 = values[1];
			String school = values[2];
			
			String[] speaker1FL = speaker1.split(" ", 0);
			String[] speaker2FL = speaker2.split(" ", 0);
						
			x.school = school;
			x.firstname1 = speaker1FL[0];
			x.lastname1 = speaker1FL[1];
			x.firstname2 = speaker2FL[1];
			x.lastname2 = speaker2FL[2];
						
			String one = speaker1FL[0].substring(0,1);
			String two = speaker1FL[1].substring(0,1);
			String three = speaker2FL[1].substring(0,1);
			String four = speaker2FL[2].substring(0,1);
			
			String code = school + " " + one + two + three + four;
			
			x.teamCode = code;
		
			teams.add(x);

		}
		
		// Read in the judges
			try {
					File file = new File("judges.txt");
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					StringBuffer stringBuffer = new StringBuffer();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						stringBuffer.append(line);
						stringBuffer.append("\n");
						judgeStrings.add(line);

					}
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			// List out judges and fill in values
			for (String judgeLine : judgeStrings) {
				Judge j = new Judge();
				String[] values = judgeLine.split(",", 0);
				String[] FL = values[0].split(" ", 0);
			
				j.firstname = FL[0];
				j.lastname = FL[1];
				j.schoolAff = values[1];
				j.judging = 0;
				
				judges.add(j);

			}
					
	roundOnePairings(teams, Round1Pairings, judges, rooms);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	updateMatchups(teams, Round1Pairings, judges, rooms);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	viewStandings(1, teams);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	roundTwoPairings(teams, Round2Pairings, judges, rooms);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	updateMatchups(teams, Round2Pairings, judges, rooms);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	viewStandings(2, teams);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	roundThreePairings(teams, Round3Pairings, judges, rooms);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	updateMatchups(teams, Round3Pairings, judges, rooms);
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	viewStandings(3, teams);



	} // Closing the public static void main here
	
	public static void roundOnePairings(List<Team> teams, List<Matchup> Round1, List<Judge> judges, List<String> rooms) {
		Collections.shuffle(teams);
		List<Team> Paired = new ArrayList<Team>();
		Team byeTeam = new Team();
		int byeTeamYes = 0;
		
		if (teams.size() % 2 != 0) {
			byeTeam = teams.get(0);
			byeTeam.paired = 1;
			byeTeamYes = 1;
			
			while (Paired.size() < teams.size() - 1) {
				
				for (Team team1 : teams) { if (team1.paired != 1) {
					for (Team team2 : teams) { if (team2.paired != 1) {
						if (team1.equals(team2) == false && team1.school != team2.school && team1 != byeTeam && team2 != byeTeam && 
								Paired.contains(team1) != true && Paired.contains(team2) != true) {
							Matchup rd1 = new Matchup();
							rd1.conTeam = team1; rd1.proTeam = team2;
							Round1.add(rd1);
							Paired.add(team1); Paired.add(team2);
							
						}
					}
				}
				
				}
				}	
			} // close while loop
			
			
		} // close if statement
		
		else {
			while (Paired.size() < teams.size()) {
				
				for (Team team1 : teams) { if (team1.paired != 1) {
					for (Team team2 : teams) { if (team2.paired != 1) {
						if (team1.equals(team2) == false && team1.school != team2.school && 
								Paired.contains(team1) != true && Paired.contains(team2) != true) {
							Matchup rd1 = new Matchup();
							rd1.conTeam = team1; rd1.proTeam = team2;
							Round1.add(rd1);
							Paired.add(team1); Paired.add(team2);
						}
					}
				}
				
				}
				}	
			} // close while loop
		} // close the else statement
		
		int judgeYes = 0;
		while (judgeYes < (Round1.size())) {
		for (Matchup x : Round1) {
			for (Judge j : judges) {
				if ((j.schoolAff != x.conTeam.school || j.schoolAff != x.proTeam.school) && j.judging != 1 && 
						x.set != 1) {
					j.judging = 1;
					x.judge = j;judgeYes = judgeYes + 1;
					x.set = 1;
				}
			}
		}
		}
				
		int i = 0;
		for (Matchup x : Round1) {
				x.room = rooms.get(i);
				i = i + 1;
		}

		
		for (Matchup x : Round1) {
			System.out.println("PRO " + x.proTeam.teamCode + " ----------- " + "CON " + x.conTeam.teamCode + 
					" --------- " + "ROOM " + x.room + " JUDGE: " + x.judge.firstname + " " + x.judge.lastname);
		}
		
		if (byeTeamYes > 0) {
			Matchup m2 = new Matchup();
			m2.byeTeam = byeTeam;
			byeTeam.wins += 1;
			byeTeam.fixThis = 1;
			System.out.println(byeTeam.teamCode + " has a BYE");
		}
		
		
		
	} // close the round 1 method		

	public static void updateMatchups(List<Team> teams, List<Matchup> Round1, List<Judge> judges, List<String> rooms) {
		
		for (Matchup m : Round1) {
			
			if (m.byeTeam != null) {
				m.byeTeam.paired = 0;
				m.byeTeam.rounds += 1;
				m.byeTeam.speaksTotal += (m.byeTeam.speaksTotal / m.byeTeam.rounds);
				m.byeTeam.wins += 1;
			}
			
			
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Did" + m.proTeam.teamCode + " win?");
			int n = reader.nextInt(); // Scans the next token of the input as an int
			
			System.out.println("Enter Pro1 Speaker Points");
			int n2 = reader.nextInt(); // Scans the next token of the input as an int
			
			System.out.println("Enter Pro2 Speaker Points");
			int n3 = reader.nextInt(); // Scans the next token of the input as an int
			
			System.out.println("Enter Con1 Speaker Points");
			int n4 = reader.nextInt(); // Scans the next token of the input as an int
			
			System.out.println("Enter Con2 Speaker Points");
			int n5 = reader.nextInt(); // Scans the next token of the input as an int

			
			if (n == 1) { // this means that the pro team won
				// Let's handle pro first
				m.proTeam.judges.add(m.judge); // add the judge
				m.proTeam.opponents.add(m.conTeam); // add the opponent
				m.proTeam.paired = 0;
				m.proTeam.rounds += 1;
				m.proTeam.speaks1 = n2;
				m.proTeam.speaks2 = n3;
				m.proTeam.timesPro += 1; 
				m.proTeam.wins += 1;
				m.proTeam.speaksTotal += (n2 + n3);
				
				// Now let's handle con
				m.conTeam.judges.add(m.judge); // add the judge
				m.conTeam.opponents.add(m.conTeam); // add the opponent
				m.conTeam.paired = 0;
				m.conTeam.rounds += 1;
				m.conTeam.speaks1 = n4;
				m.conTeam.speaks2 = n5;
				m.conTeam.timesCon += 1; 
				m.conTeam.losses += 1;
				m.conTeam.speaksTotal += (n4 + n5);
				
				m.judge.judging = 0;

			}
		
			
			else { // this means that the con team won
				// Let's handle pro first
				m.proTeam.judges.add(m.judge); // add the judge
				m.proTeam.opponents.add(m.conTeam); // add the opponent
				m.proTeam.paired = 0;
				m.proTeam.rounds += 1;
				m.proTeam.speaks1 = n2;
				m.proTeam.speaks2 = n3;
				m.proTeam.timesPro += 1; 
				m.proTeam.losses += 1;
				m.proTeam.speaksTotal += (n2 + n3);

				
				// Now let's handle con
				m.conTeam.judges.add(m.judge); // add the judge
				m.conTeam.opponents.add(m.conTeam); // add the opponent
				m.conTeam.paired = 0;
				m.conTeam.rounds += 1;
				m.conTeam.speaks1 = n4;
				m.conTeam.speaks2 = n5;
				m.conTeam.timesCon += 1; 
				m.conTeam.wins += 1;
				m.conTeam.speaksTotal += (n4 + n5);

			
				m.judge.judging = 0;

			}

			
		} // close for loop

	} // close updateMatchups 
	
	public static void viewStandings(int roundNumber, List<Team> teams) {
		
		List<Team> tempTeams = new ArrayList<Team>();
		while (roundNumber >= 0 ) {
		for (Team t : teams) {
			if (t.wins == roundNumber) {
				tempTeams.add(t);
			}
		}
		roundNumber = roundNumber - 1;

		}
		
		for (Team t : tempTeams) { 
			System.out.println(t.teamCode + " ----------> " + t.wins + " wins" + " " + t.speaksTotal + " points");
		}
		
		
	} // closing the viewStandings method
	
	public static void roundTwoPairings(List<Team> teams, List<Matchup> Round2, List<Judge> judges, List<String> rooms) {
		
		List<Team> oneWinTeams = new ArrayList<Team>();
		List<Team> noWinTeams = new ArrayList<Team>();
		Collections.shuffle(oneWinTeams); Collections.shuffle(noWinTeams);
		Collections.shuffle(judges); Collections.shuffle(rooms);
		
		for (Team t : teams) { // add the teams to their proper lists
			if (t.wins > 0) {
				oneWinTeams.add(t);
			}
			else {
				noWinTeams.add(t);
			}
		}
		
		int num1Win = oneWinTeams.size();
		int num0Win = noWinTeams.size();
		
		// Sort the teams by speaker points
		sort1(oneWinTeams);
		sort1(noWinTeams);
		
		if (num1Win % 2 != 0) { // for an odd number of 1 win teams, send the worst one to the 0 win team list
			Team temp = oneWinTeams.get(num1Win - 1);
			noWinTeams.add(0, temp);
			oneWinTeams.remove(num1Win - 1);
		}
		
		num1Win = oneWinTeams.size(); // update the list sizes because I forget how references work in Java
		num0Win = noWinTeams.size();
		
		int judgeSpot = 0;
		int roomSpot = 0;
		
		// match up all the one win teams
		for (int i = 0; i < num1Win; i = i + 2) {
			Matchup m = new Matchup();
			if (oneWinTeams.get(i).timesPro > 0) { // if team 1 was pro last time, make them con
				m.conTeam = oneWinTeams.get(i);
				m.proTeam = oneWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			
			else {
				m.proTeam = oneWinTeams.get(i);
				m.conTeam = oneWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			Round2.add(m);
		}
		
		// match up all the one win teams
		
		Team byeTeam = new Team();
		int byeTeamYes = 0;
		
		if (noWinTeams.size() % 2 != 0) {
			byeTeam = noWinTeams.get(noWinTeams.size() - 1);
			noWinTeams.remove(noWinTeams.size() - 1);
			byeTeamYes = 1;
			num0Win = num0Win - 1;
		}
		
		
		
		for (int i = 0; i < num0Win; i = i + 2) {
			Matchup m = new Matchup();
			if (noWinTeams.get(i).timesPro > 0) { // if team 1 was pro last time, make them con
				m.conTeam = noWinTeams.get(i);
				m.proTeam = noWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			
			else {
				m.proTeam = noWinTeams.get(i);
				m.conTeam = noWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			Round2.add(m);
		}

		for (Matchup x : Round2) {
			System.out.println("PRO " + x.proTeam.teamCode + " ----------- " + "CON " + x.conTeam.teamCode + 
					" --------- " + "ROOM " + x.room + " JUDGE: " + x.judge.firstname + " " + x.judge.lastname);
		}
		
		if (byeTeamYes > 0) {
			Matchup m2 = new Matchup();
			m2.byeTeam = byeTeam;
			byeTeam.wins += 1;
			byeTeam.fixThis = 1;
			System.out.println(byeTeam.teamCode + " has a BYE");
		}
		
	} // closing the roundTwoPairings 
		
    public static void roundThreePairings(List<Team> teams, List<Matchup> Round3, List<Judge> judges, List<String> rooms) {
    	
    	List<Team> twoWinTeams = new ArrayList<Team>();
    	List<Team> oneWinTeams = new ArrayList<Team>();
		List<Team> noWinTeams = new ArrayList<Team>();
		Collections.shuffle(oneWinTeams); Collections.shuffle(noWinTeams); Collections.shuffle(twoWinTeams);
		Collections.shuffle(judges); Collections.shuffle(rooms);
    	
		for (Team t : teams) { // add the teams to their proper lists
			if (t.fixThis == 1) {
				t.speaksTotal += (t.speaksTotal);
				t.fixThis = 0;
			}
		}
		
		
		for (Team t : teams) { // add the teams to their proper lists
			if (t.wins > 1) {
				twoWinTeams.add(t);
			}
			
			else if (t.wins > 0) {
				oneWinTeams.add(t);
			}

			else if (t.wins == 0) {
				noWinTeams.add(t);
			}
		}
		
		int num2Win = twoWinTeams.size();
		int num1Win = oneWinTeams.size();
		int num0Win = noWinTeams.size();
		
		sort1(twoWinTeams);
		sort1(oneWinTeams);
		sort1(noWinTeams);
				
		if (num2Win % 2 != 0) { // for an odd number of 2 win teams, send the worst one to the 1 win team list
			Team temp = twoWinTeams.get(num2Win - 1);
			oneWinTeams.add(0, temp);
			twoWinTeams.remove(num2Win - 1);
		}
		
		num1Win = oneWinTeams.size();
		
		if (num1Win % 2 != 0) { // for an odd number of 1 win teams, send the worst one to the 0 win team list
			Team temp = oneWinTeams.get(num1Win - 1);
			noWinTeams.add(0, temp);
			oneWinTeams.remove(num1Win - 1);

		}
		
		num2Win = twoWinTeams.size(); // update the list sizes because I forget how references work in Java
		num1Win = oneWinTeams.size(); 
		num0Win = noWinTeams.size();
		
		int judgeSpot = 0;
		int roomSpot = 0;
		
		// match up all the two win teams
		for (int i = 0; i < num2Win; i = i + 2) {
			Matchup m = new Matchup();
			if (twoWinTeams.get(i).timesPro > 1) { // if team 1 was pro last time, make them con
				m.conTeam = twoWinTeams.get(i);
				m.proTeam = twoWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			
			else {
				m.proTeam = twoWinTeams.get(i);
				m.conTeam = twoWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			Round3.add(m);
		}
		
		
		
		// match up all the one win teams
		for (int i = 0; i < num1Win; i = i + 2) {
			Matchup m = new Matchup();
			if (oneWinTeams.get(i).timesPro > 1) { // if team 1 was pro last time, make them con
				m.conTeam = oneWinTeams.get(i);
				m.proTeam = oneWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			
			else {
				m.proTeam = oneWinTeams.get(i);
				m.conTeam = oneWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			Round3.add(m);
		}
		
		
		// match up all the no win teams
		Team byeTeam = new Team();
		int byeTeamYes = 0;
		
		if (noWinTeams.size() % 2 != 0) {
			byeTeam = noWinTeams.get(noWinTeams.size() - 1);
			noWinTeams.remove(noWinTeams.size() - 1);
			byeTeamYes = 1;
		}

		
		for (int i = 0; i < noWinTeams.size(); i = i + 2) {
			Matchup m = new Matchup();
			if (noWinTeams.size() == 0) {
				break;
			}
			
			if (noWinTeams.get(i).timesPro > 1) { // if team 1 was pro last time, make them con
				m.conTeam = noWinTeams.get(i);
				m.proTeam = noWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			
			else {
				m.proTeam = noWinTeams.get(i);
				m.conTeam = noWinTeams.get(i + 1);
				m.judge = judges.get(judgeSpot); judgeSpot += 1;
				m.room = rooms.get(roomSpot); roomSpot += 1;
			}
			Round3.add(m);
		}
		
		for (Matchup x : Round3) {
			System.out.println("PRO " + x.proTeam.teamCode + " ----------- " + "CON " + x.conTeam.teamCode + 
					" --------- " + "ROOM " + x.room + " JUDGE: " + x.judge.firstname + " " + x.judge.lastname);
		}
		
		if (byeTeamYes > 0) {
			Matchup m2 = new Matchup();
			m2.byeTeam = byeTeam;
			byeTeam.wins += 1;
			byeTeam.speaksTotal += (byeTeam.speaksTotal / 2);
			System.out.println(byeTeam.teamCode + " has a BYE");
		}
    	

    }

	public static void sort1(List<Team> teams) { 
        int n = teams.size(); 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (teams.get(j).speaksTotal > teams.get(j+1).speaksTotal) { 
                    // swap temp and arr[i] 
                    Team temp = teams.get(j); 
                    teams.set(j, teams.get(j+1)); 
                    teams.set(j+1, temp); 
                } 
     
        Collections.reverse(teams);

    
    } 


} // Closing the overall class here
