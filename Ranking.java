//Esse aqui vai se a clase responsável por escrever e ler as coisas no aquivo em que são registrados os players que coseguiram vencer
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class Ranking{
	private static String stuff = "";
	//Esse método vai inicializar o arquivo ra que ele n fique vazio, n sei pq mas se ele fcar vazio da erro
    public static void init(){
        if(times.isEmpty()){
            times.add(0, 9999999);
            names.add(0, "pos");
        }
    }
    
	//Esse método vai escrever no arquivo o nome e o tempo que forem dados nos argumentos
    public static void append(String name, String time){

	 try (BufferedWriter out = new BufferedWriter(new FileWriter("rankings.txt"))) {
    		out.write(name + "-" + time);
		stuff += name + "-" + time + "\n";
	 } catch (IOException e) {
	        System.out.println(e.getMessage());
	 }

       
        
    }

    private static ArrayList<Integer> times = new ArrayList<Integer>();
    private static ArrayList<String> names = new ArrayList<String>();
    private static String[] winnerData;

	//Esse método vai ler o arquvo, e tntão colocar no array os tempos na ordem do menor para o maior.
    public static void update(){
         Ranking.init();

	 try (BufferedReader in = new BufferedReader(new FileReader("rankings.txt"))) {
    		String currentLine;
                        
            while ((currentLine = in.readLine()) != null) {
                System.out.println(currentLine);
                winnerData = currentLine.split("-");
		System.out.println(winnerData[0]);
		System.out.println(winnerData[1]);


                for(int i = 0; i < times.size(); i++){
                   if(times.get(i) > Integer.parseInt(winnerData[1])){

                        times.add(i, Integer.parseInt(winnerData[1]));
                        names.add(i, winnerData[0]);

                        break;
                    }
                }
            }

	 } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	 }
		
	
    }
    
    //esse metodo vai pegar o vencedor de ranking correnpndente aos argumentos, tomando cuidad para que esse vencedor n seja o carinha inicial que a gente botou só pr aquivo n ficar vazio
    public static String getWinner(int rank){
        if (rank < times.size()){
             if(times.get(rank) < 99999){
                 return (names.get(rank)  + " -" + times.get(rank));
             }else{
                 return(" - ");
             }
        }
        else
        {
            return(" - ");
        }
    }
	//E esse vai formatar o tempo direitinho, pra ficar minutos:segundos, pq o tempo inicialmente é só um número avulso que representa os minutos e segunos sem ser fomatdo.
    public static String formatWinner(String text, int rank){
		String txt;
		String[] data = new String[3];
		data[0] = text.substring(0, text.indexOf("-"));
		data[1] = text.substring(text.indexOf("-"), text.indexOf("-") + 1);
		data[2] = text.substring(text.indexOf("-") + 1, text.length());

		rank += 1;

		if(data[2].length() == 1){
			txt = rank  + " - " + data[0]+data[1] + " 00:0" + data[2];
		}
		else if(data[2].length() == 2){
			txt = rank  + " - " + data[0]+data[1] + " 00:" + data[2];
		}
		else if(data[2].length() == 3){
			txt = rank  + " - " + data[0]+data[1] + " 0" + data[2].substring(0) + ":" + data[2].substring(1, 2);
		}
		else{
		    txt = data[0]+data[1] + data[2].substring(0, data[2].length() - 3) + ":" + data[2].substring(data[2].length() - 2, data[2].length() -1);
		}

		if(text.equals(" - ")){
			txt = rank + "                    ";
		}
		return txt;
    }
	//No final de tudo, pra garantir que os resultados sejam escritos no arquivo para que na proxima vez qu o player for jogar o raning estar lá intacto. 
	public static void writeFile(){
		try (BufferedWriter out = new BufferedWriter(new FileWriter("rankings.txt"))) 			 {
    			out.write(stuff);

		 } catch (IOException e) {
			System.out.println(e.getMessage());
		 }
		
	}
}
