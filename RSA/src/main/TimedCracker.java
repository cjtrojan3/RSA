package main;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;

public class TimedCracker {

	private static BigInteger E, D, P, Q, PQ, PhiPQ;
	private static BigInteger[] cipherMessage;
	
	public static void main(String[] args) {
		PrintWriter writer = null;
		try {
			
			writer = new PrintWriter("aaa.txt", "UTF-8");
			
			for(int i = 14; i<=62; ++i) {
				calculateValues(i);
				encryptMessage();
				long startTime = System.currentTimeMillis();
				decryptMessage();
				long endTime = System.currentTimeMillis();
				System.out.println("Time for a bitlength of " + i + ": " + (endTime-startTime));
				writer.println(i+"\t"+(endTime-startTime));
			}
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		

	}
	
	public static void calculateValues(int bitlength) {
		//Find P and Q
		SecureRandom r = new SecureRandom();
		P = BigInteger.ZERO;
		Q = BigInteger.ZERO;
		while (P.equals(Q)) {
			P = new BigInteger(bitlength / 2, 100, r);
			Q = new BigInteger(bitlength / 2, 100, r);
		}
		
		//Find PQ and PhiPQ
		PQ = P.multiply(Q);
		PhiPQ = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
		
		// Find E
		E = new BigInteger("2");
		while (PhiPQ.gcd(E).compareTo(BigInteger.ONE) > 0) {
			E = E.add(BigInteger.ONE);
		}
		
		// Find D
		D = E.modInverse(PhiPQ);
	}
	
	public static void encryptMessage() {
		String plainTextMessage = "Hello World";
		cipherMessage = new BigInteger[plainTextMessage.length()];
			
		for (int i=0; i<plainTextMessage.length(); i++) {
			BigInteger m = new BigInteger(((int)plainTextMessage.charAt(i)) + "");
			BigInteger c = m.modPow(E, PQ);
			cipherMessage[i] = c;
		}
	}
	public static void decryptMessage() {
		//Find P and Q - Both prime numbers
		BigInteger newP = new BigInteger("2");
		BigInteger newQ = null;
		boolean foundP = false;
		while(!foundP) {
			if(PQ.mod(newP) == BigInteger.ZERO) {
				newQ = (PQ.divide(newP));
				foundP = true;
			}
			else {
				newP = newP.add(BigInteger.ONE);
			}
		}
		BigInteger newPhiPQ = (newP.subtract(BigInteger.ONE)).multiply(newQ.subtract(BigInteger.ONE));
		BigInteger newD = E.modInverse(newPhiPQ);
	}

}
