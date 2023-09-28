import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
    System.out.println("Hello world! \n");
        // 1)
        // a) eliminates all the numbers which are not multiple of 4;
        // b) transform the result into a list
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15,16);
//        List<Integer> result = numbers.stream().filter(nr -> nr%4 ==0).collect(Collectors.toList());
//        System.out.println(result);

        // 2)
        // a)eliminates all the numbers which are not multiple of  4;
        // b)transform each remaining number into its succesor (eg. 4 is transformed into 5);
        // c)compute the sum modulo 2 of the remaining numbers (eg. (9 +5) mod 2=0)
        // d)transform the result into a list
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15,16);
//        List<Integer> result = numbers.stream().filter(nr -> nr%4 == 0).map(nr -> nr+1).reduce(Integer::sum).stream().map(res -> res%2).collect(Collectors.toList());
//        System.out.println(result);

        // 3)
        // a)eliminates all the numbers which are not multiple of  3 or  multiple of 7;
        // b)transform each remaining number into its predecessor (eg. 3 is transformed into 2);
        // c)compute the sum modulo 5 of the remaining numbers (eg. (2 +4) mod 5=1)
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//        List<Integer> result = numbers.stream().filter(nr -> nr%3 == 0 || nr%7 == 0).map(nr -> nr-1).reduce(Integer::sum).stream().map(res -> res%5).collect(Collectors.toList());
//        System.out.println(result);

        // 4)
        //a)keep only the numbers which are multiple of  5 or  multiple of 2;
        //b)transform each remaining number into a string, that consists of a prefix "N", the number and the suffix "R" (eg. 5 is transformed into "N5R")
        //c)concatenate all the strings
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//        List<String> result = numbers.stream().filter(nr -> nr%5==0 || nr%2==0).map(nr -> "N"+nr.toString()+"R").reduce(String::concat).stream().collect(Collectors.toList());
//        System.out.println(result);

        // 5)
        //a) keep only the numbers which are multiple of 3 or multiple of 2;
        //b) transform each remaining number into a string, that consists of a prefix "A", the successor of the number and the suffix "B"
        //c) concatenate all the strings

//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//        List<String> result = numbers.stream().filter(nr -> nr%3 == 0 || nr%2 == 0).map(nr -> nr+1).map(nr -> "A" + nr.toString() + "B").reduce(String::concat).stream().collect(Collectors.toList());
//        System.out.println(result);
    }
}
