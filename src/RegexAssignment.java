import java.util.*;

public class RegexAssignment {

    //ANSWER 1
    static boolean properName(String s){
        return s.matches("[A-Z][a-z]+");
    }

    //ANSWER 2
    static boolean integer(String s){
        return s.matches("([+-])?(0|([1-9]\\d*))(\\.[0-9]+)?");
    }

    //ANSWER 3
    static boolean ancestor(String s){
        return s.matches("(great-)*(mother|father)");
    }

    //ANSWER 4
    static boolean palindrome(String s){
        return s.matches("([A-z])([A-z])([A-z])([A-z])([A-z])\\5\\4\\3\\2\\1");
    }

    //ANSWER 5
    //NOTE: IN words.java, I HAVE THE ARRAY STRING WITH ALL THE WORDLE WORDS. HOWEVER, THE TESTS WOULD NOT RUN BECAUSE THAT
    //STRING IS TOO LONG. SO, AFTER A FEW HUNDRED LINES, I COMMENTED OUT ALL THE TEST. BUT KNOW, IN PRODUCTION, THOSE WORDS
    //WOULD NEED TO BE UNCOMMENTED AGAIN.
    static class WordleResponse{
        char c;
        int index;
        LetterResponse resp;

    }

    enum LetterResponse {
        CORRECT_LOCATION, // Green
        WRONG_LOCATION,   // Yellow
        WRONG_LETTER      // Gray
    }

    /*
    if users guesses "TRAIN" (and target is "SHLEP") response would be 5 WordleResponses,
    first would be a WordleReponse object with the following values
    c='T'
    index = 0
    LetterResponse = LetterResponse.WRONG_LETTER
     */

    static List<String> wordleMatches(List <List <WordleResponse>> responses){

        Map<Integer, Character> green = new HashMap<>(); //needs to know the index it matches to and letter
        Map<Integer, Set<Character>> yellow = new HashMap<>(); //needs to know the index it DOESN'T match to and all potential letters this applies to
        ArrayList<Character> gray = new ArrayList<Character>(); //only needs to know which letter does not appear

        List<String> allWordleWords = new ArrayList<>(Arrays.asList(new words().allWordleWords));
        StringBuilder regexSolution = new StringBuilder();
        List<String> possibleWords = new ArrayList<String>();

        for (List<WordleResponse> response : responses) {
            for (WordleResponse currResponse : response) {
                if (currResponse.resp == LetterResponse.CORRECT_LOCATION) { //green
                    green.put(currResponse.index, currResponse.c);
                }
                else if (currResponse.resp == LetterResponse.WRONG_LOCATION) { //yellow
                    yellow.putIfAbsent(currResponse.index, new HashSet<>());
                    yellow.get(currResponse.index).add(currResponse.c);

                }
                else { //gray
                    gray.add(currResponse.c);
                }
            }
        }

        //build regex:

        //look-ahead first
        //for yellow, (?=.*X)
        for(Set<Character> ySet : yellow.values()){
            for(Character y : ySet){
                regexSolution.append("(?=.*").append(y).append(")");
            }
        }
        //for gray, (?!.*X)
        for(Character g: gray){
            regexSolution.append("(?!.*").append(g).append(")");
        }

        //specific position spots
        //yellow, [^X]
        //green, (X)
        //gray, .

        for(int i = 0; i < 5; i++){
            if(green.containsKey(i)){
                regexSolution.append("(").append(green.get(i)).append(")");
                //although the () above are unnecessary, I feel it makes the regex easier to understand. Professor, do you agree with me? This same logic follows below with (.)
            }
            else if(yellow.containsKey(i)){
                regexSolution.append("[^"); //this way we properly add all possible yellows
                for(char c : yellow.get(i)){
                    regexSolution.append(c);
                }
                regexSolution.append("]");
            }
            else{
                regexSolution.append("(.)"); //we need to do this 1) bc it's true 2) to not mess up the regex order for future groups
            }

        }

        //filter through allWordleWords
        for(String s : allWordleWords){
            if(s.matches(String.valueOf(regexSolution))){
                possibleWords.add(s);
            }
        }

        return possibleWords;
    }
}

