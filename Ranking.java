import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ranking{
    
    public static void init(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rankings.txt"))) {

                        for(int i = 0; i < 10; i++){
                            bw.write("abc 0000\n");
                        }


		} catch (IOException e) {

                    System.out.println(e.getMessage());

		}
    }

    
    
    public static void append(String name, String time){ 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rankings.txt"))) {


			bw.write(name + " " + time);


		} catch (IOException e) {

                    System.out.println(e.getMessage());

		}
        
    }
    
    private static int max = 10;
    private static int[] lineIndexes = new int[max];
    private static int[] times = new int[max];
    private static String[] names = new String[max];
    private static int lineIndex;
    
    public static void update(){
         Ranking.init();
        
         try (BufferedReader br = new BufferedReader(new FileReader("rankings.txt"))) {

            String currentLine, currentTime;
            lineIndex = 0;
            
            while ((currentLine = br.readLine()) != null) {
                
                currentTime = currentLine.substring(currentLine.lastIndexOf(' ') + 1);
                //System.out.println(currentTime);
                lineIndex += 1;
                //System.out.println(lineIndex);
                for(int i = 0; i < times.length ;i++){
                   if(times[i] == 0){
                       if(!currentTime.equals("")){
                           times[i] = Integer.parseInt(currentTime);
                       }
                       
                       //System.out.println(times);
                       lineIndexes[i] = lineIndex;
                       break;
                   }else{
                       if(times[i] < Integer.parseInt(currentTime)){
                           int a = i + 1;
                           int b = i;
                           while(a - b == 1 && a <= times.length){
                                times[i + a] = times[i + b];
                           }
                           if(!currentTime.equals("")){
                                times[i] = Integer.parseInt(currentTime);
                            }
                           lineIndexes[i] = lineIndex;
                           break;
                       }              
                    }
                   //System.out.println(times);
                }
                
            }
            
            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static int getMax(){
        return max;
    }
    
    public static String getGuy(int rank){
        return names[rank];
    }
}