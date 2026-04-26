import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertTrue;

public class RegexAssignmentTests {
    //problem 1
    @Test
    public void properName_returnsTrue(){
        assertTrue(RegexAssignment.properName("Bob"));
        assertTrue(RegexAssignment.properName("Sammy"));
        assertTrue(RegexAssignment.properName("Bo"));
    }

    @Test
    public void properName_returnsFalse(){
        assertFalse(RegexAssignment.properName("B"));
        assertFalse(RegexAssignment.properName("vjkwoa"));
        assertFalse(RegexAssignment.properName("ur mom"));
    }

    //problem 2
    @Test
    public void integer_returnsTrue(){
        assertTrue(RegexAssignment.integer("-23"));
        assertTrue(RegexAssignment.integer("+12.34"));
        assertTrue(RegexAssignment.integer("0"));
    }

    @Test
    public void integer_returnsFalse(){
        assertFalse(RegexAssignment.integer("--0"));
        assertFalse(RegexAssignment.integer("+0."));
        assertFalse(RegexAssignment.integer("ur mom"));
    }

    //problem 3
    @Test
    public void ancestor_returnsTrue(){
        assertTrue(RegexAssignment.ancestor("mother"));
        assertTrue(RegexAssignment.ancestor("father"));
        assertTrue(RegexAssignment.ancestor("great-great-father"));
    }

    @Test
    public void ancestor_returnsFalse(){
        assertFalse(RegexAssignment.ancestor("mothers"));
        assertFalse(RegexAssignment.ancestor("father-great"));
        assertFalse(RegexAssignment.ancestor("great-great-ancestor"));
    }

    //problem 4
    @Test
    public void palindrome_returnsTrue(){
        assertTrue(RegexAssignment.palindrome("asdfggfdsa"));
        assertTrue(RegexAssignment.palindrome("AsdfggfdsA"));
        assertTrue(RegexAssignment.palindrome("URMOMMOMRU"));
    }

    @Test
    public void palindrome_returnsFalse(){
        assertFalse(RegexAssignment.palindrome("asdfggfGsa"));
        assertFalse(RegexAssignment.palindrome("AsdfggfDsA"));
        assertFalse(RegexAssignment.palindrome("URMOMOMRU"));
    }

    //problem 5
    private RegexAssignment.WordleResponse wr(char c, int index, RegexAssignment.LetterResponse resp) {
        RegexAssignment.WordleResponse w = new RegexAssignment.WordleResponse();
        w.c = c;
        w.index = index;
        w.resp = resp;
        return w;
    }

    private List<RegexAssignment.WordleResponse> guess(RegexAssignment.WordleResponse... responses) {
        return Arrays.asList(responses);
    }

    @Test
    public void wordleMatches_noGrayInGrayPositions() {
        List<List<RegexAssignment.WordleResponse>> responses = List.of(
                guess(
                        wr('t', 0, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('r', 1, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('a', 2, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('i', 3, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('n', 4, RegexAssignment.LetterResponse.WRONG_LETTER)
                )
        );
        List<String> result = RegexAssignment.wordleMatches(responses);
        for (String word : result) {
            assertFalse(word.matches(".*[train].*"));
        }
    }

    @Test
    public void wordleMatches_oneGreenHasCorrectPosition() {
        List<List<RegexAssignment.WordleResponse>> responses = List.of(
                guess(
                        wr('s', 0, RegexAssignment.LetterResponse.CORRECT_LOCATION),
                        wr('t', 1, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('o', 2, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('r', 3, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('e', 4, RegexAssignment.LetterResponse.WRONG_LETTER)
                )
        );
        List<String> result = RegexAssignment.wordleMatches(responses);
        assertFalse(result.isEmpty());
        for (String word : result) {
            assertEquals('s', word.charAt(0));
        }
    }

    @Test
    public void wordleMatches_oneYellowDoneCorrectly() {
        List<List<RegexAssignment.WordleResponse>> responses = List.of(
                guess(
                        wr('a', 0, RegexAssignment.LetterResponse.WRONG_LOCATION),
                        wr('t', 1, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('o', 2, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('m', 3, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('s', 4, RegexAssignment.LetterResponse.WRONG_LETTER)
                )
        );
        List<String> result = RegexAssignment.wordleMatches(responses);
        assertFalse(result.isEmpty());
        for (String word : result) {
            assertTrue(word.contains("a"));
            assertNotEquals('a', word.charAt(0)); // not at yellow's index
        }
    }

    //QUESTION: is this following test necessary or is it considered going "over the top"?
    @Test
    public void wordleMatches_multipleGuessesLowersNumberOfResults() {
        List<List<RegexAssignment.WordleResponse>> oneGuess = List.of(
                guess(
                        wr('t', 0, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('r', 1, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('a', 2, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('i', 3, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('n', 4, RegexAssignment.LetterResponse.WRONG_LETTER)
                )
        );
        List<List<RegexAssignment.WordleResponse>> twoGuesses = List.of(
                guess(
                        wr('t', 0, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('r', 1, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('a', 2, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('i', 3, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('n', 4, RegexAssignment.LetterResponse.WRONG_LETTER)
                ),
                guess(
                        wr('s', 0, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('h', 1, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('o', 2, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('u', 3, RegexAssignment.LetterResponse.WRONG_LETTER),
                        wr('t', 4, RegexAssignment.LetterResponse.WRONG_LETTER)
                )
        );
        assertTrue(RegexAssignment.wordleMatches(oneGuess).size() < RegexAssignment.wordleMatches(twoGuesses).size());
    }

}
