package game;

import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by inesa on 12/05/2016.
 */
public class Game24 {

    private int currentState = 0;
    private String equation;
    private ArrayList<String> options = new ArrayList<>();
    private Character prevNumb;
    private Character nextNumb;
    public ArrayList<ArrayList<Integer>> challenges = new ArrayList<>();


    public Game24() {
        //todo: tirar isto daqui
        readFile();
        this.equation = "";
        //todo: isto é para ser passado para o sitio certo!
        for (int i = 0; i < 4; i++) {
            this.options.add(challenges.get(0).get(i).toString());
        }
        System.out.println(options);
    }


    public static void main(String args[]) {
        Game24 j = new Game24();

        Scanner reader = new Scanner(System.in);
        String in = reader.nextLine();


        while (!j.check4for3()) {
            j.equation += in;
            j.stateMachine(in);
            in = reader.nextLine();
        }

        System.out.println(j.check24(j.equation));

    }

    public void stateMachine(String input) {
        switch (this.currentState) {
            case 0:
                if (input.matches("[0-9]+")) {
                    if (validatePlay(input)) {
                        options.remove(input);
                        this.currentState = 1;
                    } else {
                        String newStr = this.equation.substring(0, this.equation.length() - 2);
                        this.equation = "";
                        this.equation += newStr + input;
                        this.currentState = 0;
                    }
                } /*else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                } */ else this.equation = "";
                break;
            case 1:
                if (input.matches("[*+/-]")) {
                    this.currentState = 2;
                } else if (input.matches("[0-9]+")) {
                    if (validatePlay(input)) {
                        options.remove(input);
                        this.currentState = 1;

                        this.prevNumb = this.equation.charAt(this.equation.length() - 2);
                        this.nextNumb = this.equation.charAt(this.equation.length() - 1);

                        String str = this.equation.substring(0, this.equation.length() - input.length());
                        String newStr = this.equation.substring(0, this.equation.length() - 2);
                        this.equation = "";
                        this.equation += newStr + input;

                        options.add(this.prevNumb.toString());
                    } else {
                        String newStr = this.equation.substring(0, this.equation.length() - 2);
                        this.equation = "";
                        this.equation += newStr + input;
                        this.currentState = 1;
                    }
                } else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            case 2:
                if (input.matches("[0-9]+"))
                    if (validatePlay(input)) {
                        options.remove(input);
                        this.currentState = 3;
                    } else {
                        String newStr = this.equation.substring(0, this.equation.length() - 1);
                        this.equation = "";
                        this.equation += newStr;
                        this.currentState = 2;
                    }
                    /*else if (input.matches("#")) {
                        this.equation = "";
                        this.currentState = 0;
                    } else {
                    String newStr = this.equation.substring(0, this.equation.length() - 2);
                    this.equation = "";
                    this.equation += newStr + input;
                }*/
                break;
            case 3:
                if (input.matches("[*+/-]")) {
                    this.currentState = 2;

                } else if (input.matches("[0-9]+")) {
                    if (validatePlay(input)) {
                        options.remove(input);
                        this.currentState = 3;
                        this.prevNumb = this.equation.charAt(this.equation.length() - 2);
                        this.nextNumb = this.equation.charAt(this.equation.length() - 1);

                        String str = this.equation.substring(0, this.equation.length() - input.length());
                        String newStr = this.equation.substring(0, this.equation.length() - 2);
                        this.equation = "";
                        this.equation += newStr + input;
                        options.add(this.prevNumb.toString());
                    } else {
                        String newStr = this.equation.substring(0, this.equation.length() - 2);
                        this.equation = "";
                        this.equation += newStr + input;
                        this.currentState = 3;
                    }
                } /*else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                }*/
                break;
            default:
                break;
        }
        System.out.println("state " + this.currentState);
        System.out.println("eq " + this.equation);
        System.out.println(options);
    }

    public boolean validatePlay(String num) {
        return this.options.contains(num);
    }

    public boolean check4for3() {

        String[] numbers = this.equation.split("[*+/-]");
        String[] ops = removeempty(this.equation.split("[0-9]"));

        return (numbers.length == 4 && ops.length == 3);
    }

    public boolean check24(String input) {

        if (check4for3()) {

            String[] numbers = input.split("[*+/-]");
            String[] ops = removeempty(input.split("[0-9]"));

            ArrayList<String> opslist = new ArrayList<String>(Arrays.asList(ops));
            ArrayList<Integer> results = new ArrayList<Integer>();

            //operadores com maior prioridade
            for (int i = 0; i < opslist.size(); i++) {
                if (opslist.get(i).equals("*") || opslist.get(i).equals("/")) {
                    results.add(basicaCalculator(Integer.parseInt(numbers[i]), Integer.parseInt(numbers[i + 1]), opslist.get(i)));
                }
            }

            //operadores com menor prioridade
            for (int i = 0; i < opslist.size(); i++) {
                if (opslist.get(i).equals("+") || opslist.get(i).equals("-")) {
                    if (i < results.size())
                        results.add(basicaCalculator(results.get(i - 1), results.get(i), opslist.get(i)));
                    else
                        results.add(basicaCalculator(results.get(i - 1), Integer.parseInt(numbers[i + 1]), opslist.get(i)));
                }
            }

            if (results.size() > 0)
                if (results.get(results.size() - 1) == 24)
                    return true;

            return false;
        } else return false;
    }

    public int basicaCalculator(Integer num1, Integer num2, String op) {
        int res = 0;
        switch (op) {
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = num1 / num2;
                break;
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num1 / num2;
                break;
            default:
                res = 0;
                break;
        }
        return res;
    }

    public void readFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resources/challenges.txt"));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();

                if (line != null) {
                    ArrayList<Integer> numbers = new ArrayList<>();
                    String[] t = line.split(" ");
                    for (int i = 0; i < t.length; i++) {
                        numbers.add(Integer.parseInt(t[i]));
                    }
                    challenges.add(numbers);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Integer> getRandomGame() {
        Random r = new Random();
        return challenges.get(r.nextInt(challenges.size()));
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getEquation() {
        return equation;
    }

    //SPLIT WAS RETURNING "" SO I NEEDED TO CREATE THIS
    public String[] removeempty(String[] a) {
        List<String> list = new ArrayList<String>(Arrays.asList(a));
        list.removeAll(Collections.singleton(""));
        a = list.toArray(new String[0]);
        return a;
    }

    public JSONObject getJSON() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray(challenges);
        json.put(Constants.GAME, array);
        return json;
    }

    public void resetEquation() {
        equation = "";
    }
}