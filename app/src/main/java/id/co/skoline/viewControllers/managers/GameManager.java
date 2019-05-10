package id.co.skoline.viewControllers.managers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameManager {
    private Context context;

    public GameManager(Context context) {
        this.context = context;
    }

    /*Get the multiple choice option and answers*/
    public ArrayList<Integer> getMultipleChoicesSingleDigit(){
        return getMultipleChoices(1, 9, 4);
    }

    public ArrayList<Integer> getMultipleChoicesDoubleDigit(){
        return getMultipleChoices(1, 15, 4);
    }

    public ArrayList<Integer> getMultipleChoices(int min, int max, int numberOfChoice){
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> finalList = new ArrayList<>();
        for (int i=min; i<=max; i++) {
            list.add(i);
        }

        Collections.shuffle(list);
        for (int i=0; i<numberOfChoice; i++) {
            finalList.add(list.get(i));
        }

        return finalList;
    }

    public int generateCorrectAnswerForMultipleChoices(ArrayList<Integer> choices){
        Random rand = new Random();
        return choices.get(rand.nextInt(choices.size()));
    }

    /*Get parameters for subtracting two number. returns the bigger number in 1st position*/
    public ArrayList<Integer> generateSubtractionParameters(int result){
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> finalList = new ArrayList<>();
        for (int i=result; i<=20; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        finalList.add(list.get(0));
        finalList.add(list.get(0)-result);
        return finalList;
    }

    /*Get parameters for adding two number.*/
    public ArrayList<Integer> generateAdditionParameters(int result){
        ArrayList<Integer> parameters = new ArrayList<>();
        Random randomGenerator = new Random();
        int param_1 = randomGenerator.nextInt(result) + 1;
        int param_2 = result - param_1;
        parameters.add(param_1);
        parameters.add(param_2);
        return parameters;
    }
}
