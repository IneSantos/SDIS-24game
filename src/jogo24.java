import java.util.*;

/**
 * Created by inesa on 12/05/2016.
 */
public class jogo24 {

    public int currentState = 0;
    public String equation;
    public Character prevNumb;
    public Character nextNumb;


    public jogo24() {
        this.equation = "";
    }

    public static void main(String args[]) {
        jogo24 j = new jogo24();
        Scanner reader = new Scanner(System.in);
        String in = reader.nextLine();

        while (!in.equals(";")) {
            j.equation += in;
            j.stateMachine(in);
            in = reader.nextLine();
        }
        System.out.println(j.check24(j.equation));
    }

    public void stateMachine(String input) {
        switch (this.currentState) {
            case 0:
                if (input.matches("[0-9]+"))
                    this.currentState = 1;
                else if (input.matches("#")){
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            case 1:
                if (input.matches("[*+/-]"))
                    this.currentState = 2;
                else if (input.matches("[0-9]+")) {

                    this.currentState = 1;

                    this.prevNumb = this.equation.charAt(this.equation.length() - 2);
                    this.nextNumb = this.equation.charAt(this.equation.length() - 1);

                    String str = this.equation.substring(0, this.equation.length() - input.length());
                    String newStr = this.equation.substring(0, this.equation.length() - str.length() - input.length());
                    this.equation = "";
                    this.equation += newStr + input;
                } else if (input.matches("#")){
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            case 2:
                if (input.matches("[0-9]+"))
                    this.currentState = 3;
                else if (input.matches("#")){
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            case 3:
                if (input.matches("[*+/-]"))
                    this.currentState = 2;
                else if (input.matches("[0-9]+")) {
                    this.currentState = 3;
                    this.prevNumb = this.equation.charAt(this.equation.length() - 2);
                    this.nextNumb = this.equation.charAt(this.equation.length() - 1);

                    String str = this.equation.substring(0, this.equation.length() - input.length());
                    String newStr = this.equation.substring(0, this.equation.length() - str.length() - input.length());
                    this.equation = "";
                    this.equation += newStr + input;
                } else if (input.matches("#")){
                    this.equation = "";
                    this.currentState = 0;
                }
                break;
            default:
                break;
        }
        System.out.println(this.currentState);
        System.out.println(this.equation);
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

    //SPLIT WAS RETURNING "" SO I NEEDED TO CREATE THIS
    public String[] removeempty(String[] a) {
        List<String> list = new ArrayList<String>(Arrays.asList(a));
        list.removeAll(Collections.singleton(""));
        a = list.toArray(new String[0]);
        return a;
    }
}
