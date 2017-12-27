import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
public class Ranking{
    
    public static void init(){
        if(times.isEmpty()){
            times.add(0, 999999);
            names.add(0, "   ");
        }
    }
    
    public static void append(String name, String time){ 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rankings.txt"))) {


			bw.write(name + " " + time);


		} catch (IOException e) {

                    System.out.println(e.getMessage());

		}
        
    }

    private static ArrayList<Integer> times = new ArrayList<Integer>();
    private static ArrayList<String> names = new ArrayList<String>();
    private static String[] winnerData;
    
    public static void update(){
         Ranking.init();

         try (BufferedReader br = new BufferedReader(new FileReader("rankings.txt"))) {

            String currentLine;
                        
            while ((currentLine = br.readLine()) != null) {
                
                winnerData = currentLine.split(" ");


                for(int i = 0; i < times.size(); i++){
                   if(times.get(i) > Integer.parseInt(winnerData[1])){

                        times.add(i, Integer.parseInt(winnerData[1]));
                        names.add(i, winnerData[0]);

                        break;
                   }
                }
            }
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static String getWinner(int rank){
        if (rank < times.size()){
             if(times.get(rank) < 99999){
                 return (names.get(rank)  + ":" + times.get(rank));
             }else{
                 return("   :   ");
             }
        }
        else
        {
            return("   :   ");
        }
    }
}
