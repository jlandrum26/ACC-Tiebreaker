import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/* 
 * Tiebreaker Rules for 2 Teams
 * Rule 1: Head-to-Head Winner
 * Rule 2: Win Percentage against Common Conference Opponents
 * Rule 3: Rank Conference Opponents and use Ranking (may require use of tiebreaker)
 * Rule 4: Combined Win Percentage of Conference Opponents
 * Rule 5: Higher Team Rating Score via SportSource Analytics (Not Possible by this Calculator)
 * Rule 6: Random Selection
 * 
 * Tiebreaker Rules for 3+ Teams
 * Rule 1: Combined Head-to-Head Win Percentage if all teams tied played each other
 * Rule 1.1: if Rule 1's condition is false, Head-to-Head Winner against tied teams. If a team lost to the other tied teams, they're out.
 * Rules 2-6 same as Tiebreaker for 2 Teams
 * 
 * version 1.0
*/

public class ACC_Tiebreaker {
    public static void main(String[] args) {
        ArrayList<String> teams = new ArrayList<>();
        teams.add("Clemson");
        teams.add("Florida State");
        teams.add("North Carolina");
        teams.add("Georgia Tech");
        teams.add("Southern Methodist");
        teams.add("Louisville");
        teams.add("Pittsburgh");
        teams.add("Boston College");
        teams.add("Virginia");
        teams.add("Virginia Tech");
        teams.add("Stanford");
        teams.add("California");
        teams.add("Miami");
        teams.add("Duke");
        teams.add("North Carolina State");
        teams.add("Wake Forest");
        teams.add("Syracuse");
        Scanner input = new Scanner(System.in);
        System.out.println("This program is designed to break ties between two teams in the ACC.");
        // System.out.print("Enter the number of teams tied: ");
        // int number = input.nextInt();
        ArrayList<String> teams_tied = new ArrayList<>();
        int idx = 0;
        while (idx < 2) {
            System.out.printf("Enter team %d: ", idx + 1);
            String team = input.nextLine();
            if (teams.contains(team)) {
                teams_tied.add(team);
                idx++;
            } else {
                System.out.println(" Invalid team name. Try again.");
            }
        }
        System.out.println(teams_tied.toString());
        idx = 0;
        String team_1 = teams_tied.get(0);
        String team_2 = teams_tied.get(1);
        System.out.printf("Did %s play %s? ", team_1, team_2);
        String response_1 = input.nextLine();
        if (response_1.equalsIgnoreCase("yes")){
            System.out.print("Which team won? ");
            String winner = input.nextLine();
            if (winner.equals(team_1)) {
                System.out.printf("%s wins the tiebreaker.", team_1);
            } else if (winner.equals(team_2)) {
                System.out.printf("%s wins the tiebreaker.", team_2);
            } else {
                System.out.println("That was not one of the tied teams. Pick again.");
            }
        } else {
            System.out.print("Did the tied teams play any common conference opponents? ");
            String response_2 = input.nextLine();
            if (response_2.equalsIgnoreCase("yes")) {
                System.out.print("How many common opponents did both teams play this season? ");
                int com_opp = input.nextInt();
                System.out.printf("How many did %s beat? ", team_1);
                int win_opp_1 = input.nextInt();
                double win_per_1 = (double) win_opp_1 / (double) com_opp;
                // System.out.println(win_per_1);
                System.out.printf("How many did %s beat? ", team_2);
                int win_opp_2 = input.nextInt();
                double win_per_2 = (double) win_opp_2 / (double) com_opp;
                // System.out.println(win_per_2);
                if (win_per_1 > win_per_2) {
                    System.out.printf("%s wins the tiebreaker.", team_1);
                } else if (win_per_1 < win_per_2) {
                    System.out.printf("%s wins the tiebreaker.", team_2);
                } else {
                    System.out.println("All common opponents must be entered for the next phase of the tiebreaker.");
                    ArrayList<String> common_opponents = new ArrayList<>();
                    ArrayList<Double> records = new ArrayList<>();
                    while (idx < com_opp){
                        System.out.printf("Enter common opponent %d: ", idx + 1);
                        String team = input.nextLine();
                        if (teams.contains(team) && !teams_tied.contains(team)) {
                            common_opponents.add(team);
                            System.out.printf("How many wins did %s have? ", team);
                            int wins = input.nextInt();
                            System.out.printf("How many losses did %s have? ", team);
                            int losses = input.nextInt();
                            double winloss = (double) wins / (double) (wins + losses);
                            records.add(winloss);
                            idx++;
                        } else {
                            System.out.println(" Invalid team name. Try again.");
                        }
                    }
                    System.out.println(common_opponents.toString());
                    System.out.println(records.toString());
                    int i = 0;
                    int common_teams = common_opponents.size();
                    int counter = 0;
                    double high_win = -1.0;
                    boolean found = false;
                    double record_1 = 0.0;
                    double record_2 = 0.0;
                    while (i < common_opponents.size()) {
                        record_1 += records.get(i);
                        record_2 += records.get(i);
                        if (counter >= common_teams){
                            System.out.println("ran out of common opponents");
                            break;
                        } else if (records.get(i) > high_win) {
                            high_win = records.get(i);
                            System.out.printf("Did %s beat %s? ", team_1, common_opponents.get(i));
                            String response_a = input.next();
                            System.out.printf("Did %s beat %s? ", team_2, common_opponents.get(i));
                            String response_b = input.next();
                            if (!(response_a.equalsIgnoreCase(response_b))) {
                                if (response_a.equalsIgnoreCase("yes")) {
                                    System.out.printf("%s wins the tiebreaker.", team_1);
                                } else {
                                    System.out.printf("%s wins the tiebreaker.", team_2);
                                }
                                found = true;
                                break;
                            } else {
                                System.out.println("Both teams had the same result. Use the next one.");
                                i++;
                                counter++;
                                continue;
                            }
                        } else if (records.get(i) == high_win) {
                            System.out.println("This team is tied with another team. The program cannot deal with that currently. Sorry.");
                            found = true;
                            break;
                        }
                        i++;
                        counter++;
                    }
                    if (!found) {
                        System.out.println("Entering last usable tiebreaker.");
                        System.out.printf("How many conference teams has %s played excluding common opponents? ", team_1);
                        int opp_1 = input.nextInt();
                        System.out.printf("List all of %s's remaining conference opponents when prompted.\n", team_1);
                        ArrayList<String> opps = new ArrayList<>();
                        int j = 0;
                        while (j < opp_1) {
                            System.out.printf("%s opponent %d: ", team_1, j + 1);
                            String team = input.nextLine();
                            if (teams.contains(team) && !(teams_tied.contains(team)) && !(common_opponents.contains(team))
                                && !(opps.contains(team))) {
                                System.out.printf("\nWhat is %s's win percentage?", team);
                                double percent = input.nextDouble();
                                record_1 += percent;
                                opps.add(team);
                            } else {
                                System.out.println("Invalid Team. Try again.");
                                continue;
                            }
                            j++;
                        }
                        System.out.printf("How many conference teams has %s played excluding common opponents? ", team_2);
                        int opp_2 = input.nextInt();
                        System.out.printf("List all of %s's remaining conference opponents when prompted.\n", team_2);
                        int k = 0;
                        while (k < opp_2) {
                            System.out.printf("%s opponent %d: ", team_2, k + 1);
                            String team = input.nextLine();
                            if (teams.contains(team) && !(teams_tied.contains(team)) && !(common_opponents.contains(team))
                                && !(opps.contains(team))) {
                                System.out.printf("\nWhat is %s's win percentage? ", team);
                                double percent = input.nextDouble();
                                record_2 += percent;
                                opps.add(team);
                            } else {
                                System.out.println("Invalid Team. Try again.");
                                continue;
                            }
                            k++;
                        }
                        if (record_1 > record_2){
                            System.out.printf("%s wins the tiebreaker.", team_1);
                        } else if (record_1 < record_2) {
                            System.out.printf("%s wins the tiebreaker.", team_2);
                        } else {
                            System.out.println("The teams are still tied. Wait for SportsSource Analytics.");
                            System.out.println("This program cannot account for those analytics, so it will perform a random draw.");
                            Random rand = new Random();
                            int selected = rand.nextInt(2);
                            System.out.printf("%d\n%s wins the random draw, and thus wins the tiebreaker.", selected, teams_tied.get(selected));
                        }
                    }
                }
            }
        }
        input.close();
        System.out.println("\nend program");
    }
    
}
