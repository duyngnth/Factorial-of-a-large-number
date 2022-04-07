
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duyng
 */
public class FactorialCaculator {
    
    /*
    OVERVIEW:
    The data type long's max value is 9,223,372,036,854,775,807 (2^63-1)
    so it is unable for us to calculate anything that exceed this limit.
    The idea is put all the digits of number into an array and do calculations
    with it.
    */
    
    public static int[] toDigitArray(int n) {
        /*
        This method accepts an non-negative integer n as a parameter
        and return an array of integer that contain all digit of n.
        Ex: 6789 => [6][7][8][9]
        */
        // Check if n is equal to 0
        // return array that contains only 1 element is [0]
        int[] zero = {0};
        if (n == 0)
            return zero;
        // DECLARE VARIABLES
        int[] ret; 
        ArrayList<Integer> temp = new ArrayList<>(); 
        int digit; 
        // While n still greater than 0, get the last digit and add it into temp list
        while (n > 0) {
           digit = n % 10;
           temp.add(digit);
           n /= 10;
        }
        // Ex: n = 6789 => temp = (9)(8)(7)(6)
        // Now construct the ret array with the size of temp
        // Then add all the elements in reversed order
        ret = new int[temp.size()];
        for (int i = temp.size()-1; i >= 0; i--)
            ret[temp.size()-i-1] = temp.get(i);
        return ret;
    }
    
    public static int[] multiply(int[] a, int[] b) {
        /*
        This method accepts 2 arrays of digit as parameters 
        that represent 2 numbers and return an array of digit
        that represents the product of 2 number.
        Ex: a = [2][4][5][3], b = [3][4] => ret = [8][3][4][0][2]
        */
        // Creating new array a2 with a first digit 0
        // ex: [2][4][5][3] -> [0][2][4][5][3]
        int[] a2 = new int[a.length+1];
        a2[0] = 0;
        for (int i = 1; i < a2.length; i++)
            a2[i] = a[i-1];
        // DECLARE VARIABLES
        int ret[]; 
        int preret[] = new int[a.length + b.length];
        int[][] base = new int[b.length][a.length + b.length];
        int[] temp = new int[a.length + 1];
        int c, carry = 0, e;
        int[] digitmul;
        /*
        EXPLAIN:
        1. We will multiplying a to each digit of b, the result will be hold in temp array
        2. After that, put all elements in temp into a new row in the base array
        3. With a = [0][2][4][5][3], b = [3][4], our attemp is to create a base array like this:
           [0][7][3][5][9][0] (2453 * 30 = 73590)
           [0][0][9][8][1][2] (2453 * 4 = 9812)
        4. We will add all the elements of base array by column to get the result
           [0][7][3][5][9][0]
          +[0][0][9][8][1][2]
           ------------------
          =[0][8][3][4][0][2] -> This will be hold in preret array
        5. Copy all elements after redundant 0s into ret array
           -> ret = [8][3][4][0][2]
        => RETURN ret
        */
        for (int i = 0; i < b.length; i++) {
            c = b[i]; // c is the current digit of b to be multiplied with a2
            for (int j = a2.length - 1; j >= 0; j--) {
                e = c * a2[j] + carry; // e is the total of the product of c and a digit of a2 and the amount that is carried from the previous multiplication
                // Check if e >= 10, add the last digit of e into temp
                // and carry the remaining amount into the next multiplication
                if (e >= 10) {
                    carry = e/10;
                    e %= 10;
                } else
                    carry = 0;
                temp[j] = e;
            }
            // Add temp array into a new row of base array
            for (int j = 0; j < temp.length; j++)
                base[i][j+i] = temp[j];
        }
        carry = 0;
        // Calculate the addition in base by column to find the final result
        for (int j = base[0].length - 1; j >= 0; j--) {
            for (int i = 0; i < base.length; i++) 
                carry += base[i][j];
            // If the sum of each column >= 10, the last digit will be addedd into preret array
            // and the remaining amount will be carried into the next addition
            e = carry % 10;
            carry /= 10;
            preret[j] = e;
        }
        // Delete redundant 0s in new return array
        int count = 0;
        for (int i = 0; i < preret.length; i++) {
            if (preret[i] == 0)
                count++;
            else
                break;
        }
        // Construct return array
        ret = new int[preret.length - count];
        for (int i = 0; i < ret.length; i++)
            ret[i] = preret[i + count];
        return ret;
    }
    
    public static int[] factorial(int n) {
        int[] ret = {1};
        int[] num;
        for (int i = 2; i <= n; i++) {
            num = toDigitArray(i);
            ret = multiply(ret, num);
        }
        return ret;
    }
    
    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to exit...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num;
        int[] result;
        
        while (true) {
            System.out.print("Enter number: ");
            try {
                num = Integer.parseInt(sc.nextLine().trim());
                if (num < 0)
                    System.err.println("Number can not smaller than 0!");
                else
                    break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, try again!");
            }
        }
        result = factorial(num);
        System.out.print(num + "! = ");
        for (int i = 0; i < result.length; i++)
            System.out.print(result[i]);
        System.out.println("");
        pressEnterToContinue();
    }
}