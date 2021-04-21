import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;


public class PuzzleSolver {

    private HashMap<Character,Integer> letters;
    public int [] used;
    private static final  int  initial = 11;

    public boolean solution(int system,int chars, HashMap<Character,Integer> letters,String [] strings){
        int [] values = new int[3];
        int counter = 1;
        char tmp = 0;

        for(int i=0; i<3 ; i++) {

            for (int j= strings[i].length()-1; j >=0 ; j--) {
                for(Character c : letters.keySet()){
                    if(strings[i].charAt(j) == c){
                        tmp=c;
                        break;
                    }

                }
                values[i] += counter*letters.get(tmp);
                counter *=10;
            }
            counter = 1;
        }
        if(values[2] == (values[1]+values[0]))
            return true;
        return false;

    }

    public boolean backtracking(char [] puzzlechars,int system,int chars,HashMap<Character,Integer> letters,int levels,String [] strings){
        if(levels == chars - 1){
            for(int i =0 ; i< system; i++){
                if(used[i] == 0){
                    letters.put(puzzlechars[levels],i);
                    if(solution(system,chars,letters,strings))
                        return true;
                }
            }
            return false;
        }
        for(int i =0 ; i< system; i++){
            if(used[i] == 0){
                letters.put(puzzlechars[levels],i);
                used[i]++;
                if(backtracking(puzzlechars,system,chars,letters,levels+1,strings))
                    return true;
                used[i] = 0;
            }
        }
        return false;
    }

    public boolean solvePuzzle(int system,String [] strings){
        int chars=0;
        char[] puzzlechars = new char[system];
        int counter =0;
        int [] times= new int[26];
        for(int i=0; i<3 ; i++){

            for(int j=0; j<strings[i].length(); j++){
                times[strings[i].charAt(j) -65]++;
            }
        }
        for(int i=0; i<26; i++){
            if(times[i] > 0)
                chars++;
        }
        if(chars>system)
            System.out.println("We need max " + system +"characters");
        for(int i=0; i< 26; i++){
            if(times[i] > 0)
                letters.put((char) (i +'A'),initial);
        }
        for(Character c : letters.keySet()){
            puzzlechars[counter] = c;
            counter++;
        }
        return backtracking(puzzlechars,system,chars,letters,0,strings);
    }


    public static void main(String[] args) {

        PuzzleSolver sl= new PuzzleSolver();
        String strings[] = new String[3];
        sl.letters= new HashMap<Character,Integer>();
        System.out.println("Choose the arithmetic system in which you wish to solve the puzzle: ");
        Scanner sc = new Scanner(System.in);
        int system=sc.nextInt();
        sl.used = new int [system];
        System.out.println("Give the 3 strings of the puzzle : \n");
        for(int i=0; i<3;i++){

            System.out.println("String " + (i+1));
            strings[i]=sc.next();

        }
        if(sl.solvePuzzle(system,strings)){
            System.out.println("Puzzle solved , here is the value of each letter");
            for(Map.Entry<Character,Integer> entry : sl.letters.entrySet()){
                System.out.println("Value for "+entry.getKey() + " is : " + entry.getValue());
            }
        }
        else{
            System.out.println("No solution found");
        }
    }

}
