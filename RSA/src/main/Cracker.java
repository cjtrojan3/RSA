package main;
import java.math.*;
import java.util.ArrayList;

public class Cracker {

	public static void main(String[] args) {
		
		//I am given (PQ, E) - I can encrypt anything with this
		BigInteger PQ = new BigInteger("608485549753");
		BigInteger E = new BigInteger("7");
		
		//Find P and Q - Both prime numbers
		BigInteger P = new BigInteger("2");
		BigInteger Q = null;
		boolean foundP = false;
		while(!foundP) {
			if(PQ.mod(P) == BigInteger.ZERO) {
				Q = (PQ.divide(P));
				foundP = true;
			}
			else {
				P = P.add(BigInteger.ONE);
			}
		}
		//At this point, we have found P and Q
		
		//Find PhiPQ
		BigInteger PhiPQ = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
		
		//Find D
		BigInteger D = E.modInverse(PhiPQ);
		
		//Bring in the encrypted message
		ArrayList<BigInteger> hiddenMessage = new ArrayList<BigInteger>();
		String decodedMessage = "";
		hiddenMessage.add(new BigInteger("576322461849"));
		hiddenMessage.add(new BigInteger("122442824098"));
		hiddenMessage.add(new BigInteger("34359738368"));
		hiddenMessage.add(new BigInteger("29647771149"));
		hiddenMessage.add(new BigInteger("140835578744"));
		hiddenMessage.add(new BigInteger("546448062804"));
		hiddenMessage.add(new BigInteger("120078454173"));
		hiddenMessage.add(new BigInteger("42618442977"));
		
		//Decrypt the message now
		for(BigInteger i : hiddenMessage) {
			BigInteger m = i.modPow(D, PQ);
			decodedMessage += (char)(m.intValue());
		}
		System.out.println(decodedMessage);
	}

}
