import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MistypeDistance {
	
	private Map<Character, Coord> charMap;
	
	public MistypeDistance(){
		charMap = new HashMap<>();
		
		charMap.put('a', new Coord(0,1));
		charMap.put('s', new Coord(1,1));
		charMap.put('i', new Coord(7,0));
		charMap.put('o', new Coord(8,0));
		charMap.put('n', new Coord(5,2));
	}
	
	public int compute(String word1, String word2) {
        if (word1.isEmpty()) return word2.length();
        if (word2.isEmpty()) return word1.length();
 
        int word1Length = word1.length();
        int word2Length = word2.length();
        
        int[][] minCosts = new int[word1Length][word2Length];
        
        minCosts[word1Length - 1][word2Length - 1] = replaceCost(word1, word2, word1Length - 1, word2Length - 1);
         
        for (int j = word2Length - 2; j >= 0; j--) {
            minCosts[word1Length - 1][j] = 1 + minCosts[word1Length - 1][j + 1];
        }
 
        for (int i = word1Length - 2; i >= 0; i--) {
            minCosts[i][word2Length - 1] = 1 + minCosts[i + 1][word2Length - 1];
        }
 
        for (int i = word1Length - 2; i >= 0; i--) {
            for (int j = word2Length - 2; j >= 0; j--) {
                int replace = replaceCost(word1, word2, i, j) + minCosts[i + 1][j + 1];
                int delete = 1 + minCosts[i + 1][j];
                int insert = 1 + minCosts[i][j + 1];
                minCosts[i][j] = min(replace, delete, insert);
            }
        }
        return minCosts[0][0];
    }

	public int replaceCost(String w1, String w2, int w1Index, int w2Index) {
		int cost = 1;
		
		if(w1.charAt(w1Index) == w2.charAt(w2Index)){
			cost = 0;
		}else{
			Coord coord1 = charMap.get(w1.charAt(w1Index));
			Coord coord2 = charMap.get(w2.charAt(w2Index));
			
			if(coord1 != null && coord2 != null){
				cost = coord1.getDistance(coord2);
			}	
		}
		
		return cost;
	}

	public int min(int... numbers) {
		int result = Integer.MAX_VALUE;
		for (int each : numbers) {
			result = Math.min(result, each);
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String line = null;
		MistypeDistance distance = new MistypeDistance();
		
		try {
			FileReader reader = new FileReader("input.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String first = null;
			
			while((line=bufferedReader.readLine()) != null){
				if(first == null){
					first = line;
					System.out.println(line);
				}else if(line.equals("****")){
					System.out.println(line);
					first = null;
				}else{
					System.out.println(line + ", " + distance.compute(first, line));
				}
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class Coord{
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getDistance(Coord otherCoord){
		int otherX = otherCoord.getX();
		int otherY = otherCoord.getY();
		
		return (int)Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
	}
}