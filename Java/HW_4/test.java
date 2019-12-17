import java.lang.*;
import java.io.*;
import java.util.*;
public class test {
	public static void main(String[] args) {
		System.out.println(Character.getType('-'));
		System.out.println(Character.getType('a'));
		System.out.println(Character.getType('A'));
		System.out.println(Character.getType('b'));
		System.out.println(Character.getType('1'));
		System.out.println(Character.getType('\"'));
		System.out.println(Character.getType((char)0x0027));
		System.out.println(Character.getType(':'));
		System.out.println('\'' == '\'');
   	} 
}
