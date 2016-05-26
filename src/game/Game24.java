package game;

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
    private Character prevNumb;
    private Character nextNumb;
    public ArrayList<ArrayList<Integer>> challenges = new ArrayList<>();


    public Game24() {
        this.equation = "";
    }

    public static void main(String args[]) {
        Game24 j = new Game24();
        j.readFile();
        Scanner reader = new Scanner(System.in);
        String in = reader.nextLine();

        while (!j.check4for3()) {
            j.equation += in;
            j.stateMachine(in);
            in = reader.nextLine();
        }
        System.out.println(j.check24(j.equation));

    }

    public boolean stateMachine(String input) {
        switch (this.currentState) {
            case 0:
                if (input.matches("[0-9]+"))
                    this.currentState = 1;
                else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                } else this.equation = "";
                break;
            case 1:
                if (input.matches("[*+/-]")) {
                    this.currentState = 2;
                }
                else if (input.matches("[0-9]+")) {

                    this.currentState = 1;

                    this.prevNumb = this.equation.charAt(this.equation.length() - 2);
                    this.nextNumb = this.equation.charAt(this.equation.length() - 1);

                    String str = this.equation.substring(0, this.equation.length() - input.length());
                    String newStr = this.equation.substring(0, this.equation.length() - 2);
                    this.equation = "";
                    this.equation += newStr + input;
                } else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            case 2:
                if (input.matches("[0-9]+"))
                    this.currentState = 3;
                else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                }
                else{
                    String newStr = this.equation.substring(0, this.equation.length() - 2);
                    this.equation = "";
                    this.equation += newStr + input;
                }
                break;
            case 3:
                if (input.matches("[*+/-]")) {
                    this.currentState = 2;

                } else if (input.matches("[0-9]+")) {
                    this.currentState = 3;
                    this.prevNumb = this.equation.charAt(this.equation.length() - 2);
                    this.nextNumb = this.equation.charAt(this.equation.length() - 1);

                    String str = this.equation.substring(0, this.equation.length() - input.length());
                    String newStr = this.equation.substring(0, this.equation.length() - 2);
                    this.equation = "";
                    this.equation += newStr + input;
                } else if (input.matches("#")) {
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            default:
                break;
        }
        System.out.println("state " + this.currentState);
        System.out.println("eq " + this.equation);
        return check4for3();
    }

    public boolean check4for3() {

        String[] numbers = this.equation.split("[*+/-]");
        String[] ops = removeempty(this.equation.split("[0-9]"));

        return (numbers.length == 4 && ops.length == 3);
    }

    public boolean check24(String input) {

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

        if (results.get(results.size() - 1) == 24)
            return true;
        else return false;
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
}
