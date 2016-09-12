package com.revature.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.beans.ApplicationProperties;
import com.revature.data.impl.PropertyType;
import com.revature.service.BusinessDelegate;
import com.revature.service.Crypt;

public class CryptImpl implements Crypt {

	private BusinessDelegate businessDelegate;
	String[][] keys = new String[][]{
		
		/*
		 *  These keys are used to encode the corresponding properties
		 */
			
		{"CZmTgoznKnJocTkGuFFURvZjUDuVvBhoETorfnzPOfqymleBbOOHfqPCSSty", "pneumonoultramicroscopicsilicovolcanoconiosis"},
		{"GSXWzGGiiDBvlYxTNddabeUOsSPLHoYnibqBEAtRrSDnZPrACvUjBMGxcoBZ", "Pseudopseudohypoparathyroidism"},
		{"cCpQZBETFySMWXeMTQDQomszbDhIgTCWNfjzrBQjwyzcMIrNeFGZggWpzSdQ", "Floccinaucinihilipilification"},
		{"UjVheJqfrHXEuciEaIEibjRYjaxGEJFPrLcZNuugxZQmpHdeoBJRVLFeEDfc", "Antidisestablishmentarianism"},
		{"BhXCFkEevSCHlJMCJyvqhyOiNnKDaoxwcdWrNGxUZySIJspidexHSROVXDAh", "supercalifragilisticexpialidocious"},
		{"RnhHIlwovrapdVzySrOIfmMZPOPOEACAsVScsBIflnsIphgireiIRKkmINdr", "Incomprehensibilities"},
		{"momGKfMimvxYGNKmZCzdXNSBGpvQngTbtvxETwjePoZWyirhkyAWMhkFzxQI", "honorificabilitudinitatibus"},
		{"TuJgzrAAFblqmFUfDvRyNHOtKQjVpxESLwrXecnGMSrSEJyhfkgPGvTccbPJ", "sesquipedalianism"},
		{"JplYSkoJXvxUEIaEZtLMzYugcPINpzArbIoGHjwHwFzdoUtfNfMOetPvvsHn", "METHIONYLTHREONYLTHREONYGLUTAMINYLARGINY"},
		{"iqGJkjoSepUYggqxsZCdxXzCSyjxADhQtsiMPhyNRMxJbGowMrGmlIQETFzC", "Aequeosalinocalcalinoceraceoaluminosocupreovitriolic"},
		{"boosNkoVgLkjnWJUMEeHAGbUmwWhVlBOPZKZjUduUXunxwbsZmnNxKdAWePg", "peobuefdvxjbtoajefspkfuccfngbf"}
	};
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	/**
	 * 
	 *  This method encrypts the target with the keys.
	 *  
	 * @param target The string you want encrypted.
	 * @param key1   The keyword you want the target to cipher against. (Don't pass in the target again!)
	 * @param key2   The keyword used to vary the output when similiar targets and keywords are utilized.
	 * @return       Returns the encryped target.
	 */
	public String encrypt(String target, String key1, String key2){
		
		String temp = target;
		
		for(int i = 0; i < key2.length(); i++){
			
			temp = encrypt(temp, key1);
		}
		
		String holder = "";
		
		char[] tempArray = temp.toCharArray();
		
		for(char c : tempArray){
			
			holder += maskRegions(c);
		}
		
		return holder;
	}
	private String encrypt(String target, String keyword){
		
		char[] tempPassword = target.toCharArray();
		char[] tempUsername = keyword.toCharArray();
		char[] tempResult = new char[tempPassword.length];
		
		 //Alphabets
        char[] alphabet1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] alphabet2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
        int keyBase = tempUsername.length;
        int keyCeil = tempUsername.length;
        int keyIndex;
        
		for(int i = 0; i < tempPassword.length; i++){
			
			if(Character.isAlphabetic(tempPassword[i])){
				
				keyIndex = keyBase % keyCeil;
				
				if(Character.isUpperCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex]))
				{
					tempResult[i] = cipherLetter(alphabet1, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isUpperCase(tempPassword[i]) && Character.isLowerCase(tempUsername[keyIndex])){
					
					tempResult[i] = cipherLetter(alphabet1, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isLowerCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex])){
					
					tempResult[i] = cipherLetter(alphabet2, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else {
					
					tempResult[i] = cipherLetter(alphabet2, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				keyBase++;
			}
			
			else if(Character.isDigit(tempPassword[i])){
				
				
				tempResult[i] = cipherNumber(tempPassword[i]);
			}
			
			else {
				
				tempResult[i] = cipherOther(tempPassword[i]);
			}
		}
		
		return new String(tempResult);
	}
	private char cipherLetter(char[] alpha1, char[] alpha2, char letter, int keyIndex, char[] keyword){
		
		int cipherLetter;
		int indexLetter = 0;
		int indexKey = 0;
		
		for(int i = 0; i < 26; i++){
			
			if(letter == alpha1[i]){
				indexLetter = i;
				break;
			}
		}
		
		for(int j = 0; j < 26; j++){
			
			if(keyword[keyIndex] == alpha2[j]){
				
				indexKey = j;
				break;
			}
		}
		
		cipherLetter = indexLetter + indexKey;
		
		while(cipherLetter > 25){
			
			cipherLetter -= 26;
		}
		
		return alpha1[cipherLetter];
	}
	private char cipherNumber(char number){
		
		int trueNumber = Character.getNumericValue(number);
		
		switch(trueNumber){
		
			case 0:
				return 55;
			case 1:
				return 52;
			case 2:
				return 51;
			case 3:
				return 56;
			case 4:
				return 49;
			case 5:
				return 48;
			case 6:
				return 57;
			case 7:
				return 53;
			case 8:
				return 50;
			case 9:
				return 54;
			default:
				return 0; // NULL
		}
	}
	private char cipherOther(char other){
		
		switch(other){
		
			case 32:  return 45;
			case 33:  return 42;
			case 34:  return 41;
			case 35:  return 33;
			case 36:  return 34;
			case 37:  return 32;
			case 38:  return 92;
			case 39:  return 58;
			case 40:  return 91;
			case 41:  return 123;
			case 42:  return 64;
			case 43:  return 60;
			case 44:  return 36;
			case 45:  return 59;
			case 46:  return 47;
			case 47:  return 61;
			case 58:  return 46;
			case 59:  return 93;
			case 60:  return 125;
			case 61:  return 35;
			case 62:  return 40;
			case 63:  return 62;
			case 64:  return 37;
			case 91:  return 43;
			case 92:  return 124;
			case 93:  return 38;
			case 94:  return 39;
			case 95:  return 126;
			case 96:  return 63;
			case 123: return 44;
			case 124: return 95;
			case 125: return 94;
			case 126: return 96;
			default:  return 0; // NULL
		}
	}
	private String maskRegions(char c){
		
		if("ABCDEFGHIJKLM".indexOf(c) != -1){
		
			switch(c){
			
				case 'A': return "$OhuT#";
				case 'B': return "TtFTiY";
				case 'C': return "tCdOUC";
				case 'D': return "3xwtpi";
				case 'E': return "!M0$Xe";
				case 'F': return "wvQvVO";
				case 'G': return "e#t3hA";
				case 'H': return "zZJuZE";
				case 'I': return "E#kkTA";
				case 'J': return "YRd0V$";
				case 'K': return "S5Z6CN";
				case 'L': return "ruBvQq";
				case 'M': return "DYOohJ";
				default : return null;
			}
		}
		else if("NOPQRSTUVWXYZ".indexOf(c) != -1){
			
			switch(c){
			
				case 'N': return "C!4zE8";
				case 'O': return "fOY9iN";
				case 'P': return "FYpp4u";
				case 'Q': return "0sG4r3";
				case 'R': return "KYtiUl";
				case 'S': return "q$kreA";
				case 'T': return "jv8MUZ";
				case 'U': return "wa0$Hn";
				case 'V': return "mw154U";
				case 'W': return "K0xe4k";
				case 'X': return "NngHYK";
				case 'Y': return "pJE1Nw";
				case 'Z': return "COqVzq";
				default : return null;
			}
		}
		else if("abcdefg".indexOf(c) != -1){
				
			switch(c){
			
				case 'a': return "mPcFC2";
				case 'b': return "JINKJk";
				case 'c': return "wkNBhR";
				case 'd': return "kesPkg";
				case 'e': return "nkfZn4";
				case 'f': return "$w?fY2";
				case 'g': return "Xwo?Kg";
				default : return null;
			}
		}
		else if("hijklmnop".indexOf(c) != -1){
		
			switch(c){

				case 'h': return "CPa?8Y";
				case 'i': return "o?!9w4";
				case 'j': return "UzdBgX";
				case 'k': return "gcEI3h";
				case 'l': return "F#qIp?";
				case 'm': return "3YeSkm";
				case 'n': return "gFCXbS";
				case 'o': return "dHZ6g!";
				case 'p': return "kb65mt";
				default : return null;
			}
		}
		else if("qrstuvwxyz".indexOf(c) != -1){
			
			switch(c){
			
				case 'q': return "7b7099";
				case 'r': return "oHyVZ?";
				case 's': return "XR#i3C";
				case 't': return "hp7X#v";
				case 'u': return "SfyQSZ";
				case 'v': return "ACro8E";
				case 'w': return "gh4BjV";
				case 'x': return "8cUb0S";
				case 'y': return "Iey9ay";
				case 'z': return "j5Db9V";
				default : return null;
			}
		}
		else if(Character.isDigit(c)){
			
			int trueNumber = Character.getNumericValue(c);
			
			switch(trueNumber){
			
				case 0:
					return "p90dHj";
				case 1:
					return "1i!Rc!";
				case 2:
					return "h00pFz";
				case 3:
					return "5p1Qp9";
				case 4:
					return "7tU0fU";
				case 5:
					return "Ukmety";
				case 6:
					return "Hy32BQ";
				case 7:
					return "Dgyllp";
				case 8:
					return "MGvJXc";
				case 9:
					return "MDCqbq";
				default : 
					return null;
			}
		}
		else {
		
			switch(c){
			
				case 32:  return "2b6pD8";
				case 33:  return "M54?bE"; 
				case 34:  return "Rp$wGi"; 
				case 35:  return "I0uQ9Y"; 
				case 36:  return "a9PjnL"; 
				case 37:  return "nZVNug"; 
				case 38:  return "7Jf!lN"; 
				case 39:  return "2CU6Ft"; 
				case 40:  return "OPRQfR"; 
				case 41:  return "jY3zgw"; 
				case 42:  return "DOVa2s"; 
				case 43:  return "KP6JVz"; 
				case 44:  return "wuws!m"; 
				case 45:  return "HOQraS"; 
				case 46:  return "IPNHri"; 
				case 47:  return "7XEj5R"; 
				case 58:  return "YpkzKT"; 
				case 59:  return "EkvUI0"; 
				case 60:  return "eqBO7c"; 
				case 61:  return "16RYrG"; 
				case 62:  return "gFswj6"; 
				case 63:  return "?TJQUA"; 
				case 64:  return "inP8eS";
				case 91:  return "dmFtkx";
				case 92:  return "IVD3EC";
				case 93:  return "C4tdtH";
				case 94:  return "w$LsgQ";
				case 95:  return "1xRt1X";
				case 96:  return "4bbblY";
				case 123: return "kToyO1";
				case 124: return "aP1chC"; 
				case 125: return "Cfhven";
				case 126: return "yvx?ZC";
				default : return null;
			}
		}
	}
	
	// Decryption
	public String decrypt(String target, String key1, String key2){
		
		String temp = target;
		
		temp = clearMask(temp);
		
		for(int i = 0; i < key2.length(); i++){
		
			temp = decrypt(temp, key1);
		}
		
		return temp;
	}
	private String decrypt(String target, String keyword){
		
		char[] tempPassword = target.toCharArray();
		char[] tempUsername = keyword.toCharArray();
		char[] tempResult = new char[tempPassword.length];
		
		 //Alphabets
        char[] alphabet1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] alphabet2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
        
        int keyBase = tempUsername.length;
        int keyCeil = tempUsername.length;
        int keyIndex;
        
		for(int i = 0; i < tempPassword.length; i++){
			
			if(Character.isAlphabetic(tempPassword[i])){
				
				keyIndex = keyBase % keyCeil;
				
				if(Character.isUpperCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex]))
				{
					tempResult[i] = decipherLetter(alphabet1, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isUpperCase(tempPassword[i]) && Character.isLowerCase(tempUsername[keyIndex])){
					
					tempResult[i] = decipherLetter(alphabet1, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isLowerCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex])){
					
					tempResult[i] = decipherLetter(alphabet2, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else {
					
					tempResult[i] = decipherLetter(alphabet2, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				keyBase++;
			}
			
			else if(Character.isDigit(tempPassword[i])){
				
				
				tempResult[i] = decipherNumber(tempPassword[i]);
			}
			
			else {
				
				tempResult[i] = decipherOther(tempPassword[i]);
			}
		}
		
		return new String(tempResult);
	}
	private char decipherLetter(char[] alpha1, char[] alpha2, char letter, int keyIndex, char[] keyword){
		
		int cipherLetter;
		int indexLetter = 0;
		int indexKey = 0;
		
		for(int i = 0; i < 26; i++){
			
			if(letter == alpha1[i]){
				indexLetter = i;
				break;
			}
		}
		
		for(int j = 0; j < 26; j++){
			
			if(keyword[keyIndex] == alpha2[j]){
				
				indexKey = j;
				break;
			}
		}
		
		cipherLetter = indexLetter - indexKey;
		
		while(cipherLetter < 0){
			
			cipherLetter += 26;
		}
		
		return alpha1[cipherLetter];
	}
	private char decipherNumber(char number){
		
		int trueNumber = Character.getNumericValue(number);

		switch(trueNumber){
		
			case 0:
				return 53;
			case 1:
				return 52;
			case 2:
				return 56;
			case 3:
				return 50;
			case 4:
				return 49;
			case 5:
				return 55;
			case 6:
				return 57;
			case 7:
				return 48;
			case 8:
				return 51;
			case 9:
				return 54;
			default:
				return 0; // NULL
		}
	}
	private char decipherOther(char other){
		
		switch(other){
		
			case 32:  return 37;
			case 33:  return 35;
			case 34:  return 36;
			case 35:  return 61;
			case 36:  return 44;
			case 37:  return 64;
			case 38:  return 93;
			case 39:  return 94;
			case 40:  return 62;
			case 41:  return 34;
			case 42:  return 33; 
			case 43:  return 91;
			case 44:  return 123;
			case 45:  return 32;
			case 46:  return 58;
			case 47:  return 46;
			case 58:  return 39;
			case 59:  return 45;
			case 60:  return 43;
			case 61:  return 47;
			case 62:  return 63;
			case 63:  return 96;
			case 64:  return 42; 
			case 91:  return 40;
			case 92:  return 38;
			case 93:  return 59;
			case 94:  return 125;
			case 95:  return 124;
			case 96:  return 126;
			case 123: return 41;
			case 124: return 92;
			case 125: return 60;
			case 126: return 95;
			default:  return 0; // NULL
		}
	}
	private String clearMask(String maskedField){
		
		char[] maskedArray = maskedField.toCharArray();
		char[] trueLetters = new char[maskedField.length() / 6];
		
		int startPos = 0;
		int counter = 1;
		int interval = 6;
		int limit;

		String mini = "";
		
		// i = 1 True Letter
		for(int i = 0; i < trueLetters.length; i++){
			
			limit = counter * interval;
			
			mini = "";
			
			// j = 1 piece of the masked letter
			for(int j = startPos; j < limit; j++){
				
				mini += maskedArray[j];
			}
			
			counter  += 1;
			startPos += 6;
	
			switch(mini){
				case "$OhuT#": trueLetters[i] = 'A'; 
					break;
				case "TtFTiY": trueLetters[i] = 'B'; 
					break;
				case "tCdOUC": trueLetters[i] = 'C'; 
					break;
				case "3xwtpi": trueLetters[i] = 'D'; 
					break;
				case "!M0$Xe": trueLetters[i] = 'E'; 
					break;
				case "wvQvVO": trueLetters[i] = 'F'; 
					break;
				case "e#t3hA": trueLetters[i] = 'G'; 
					break;
				case "zZJuZE": trueLetters[i] = 'H'; 
					break;
				case "E#kkTA": trueLetters[i] = 'I'; 
					break;
				case "YRd0V$": trueLetters[i] = 'J'; 
					break;
				case "S5Z6CN": trueLetters[i] = 'K'; 
					break;
				case "ruBvQq": trueLetters[i] = 'L'; 
					break;
				case "DYOohJ": trueLetters[i] = 'M'; 
					break;
				case "C!4zE8": trueLetters[i] = 'N'; 
					break;
				case "fOY9iN": trueLetters[i] = 'O'; 
					break;
				case "FYpp4u": trueLetters[i] = 'P'; 
					break;
				case "0sG4r3": trueLetters[i] = 'Q'; 
					break;
				case "KYtiUl": trueLetters[i] = 'R'; 
					break;
				case "q$kreA": trueLetters[i] = 'S'; 
					break;
				case "jv8MUZ": trueLetters[i] = 'T'; 
					break;
				case "wa0$Hn": trueLetters[i] = 'U'; 
					break;
				case "mw154U": trueLetters[i] = 'V'; 
					break;
				case "K0xe4k": trueLetters[i] = 'W'; 
					break;
				case "NngHYK": trueLetters[i] = 'X'; 
					break;
				case "pJE1Nw": trueLetters[i] = 'Y'; 
					break;
				case "COqVzq": trueLetters[i] = 'Z'; 
					break;
				case "mPcFC2": trueLetters[i] = 'a'; 
					break;
				case "JINKJk": trueLetters[i] = 'b'; 
					break;
				case "wkNBhR": trueLetters[i] = 'c'; 
					break;
				case "kesPkg": trueLetters[i] = 'd'; 
					break;
				case "nkfZn4": trueLetters[i] = 'e'; 
					break;
				case "$w?fY2": trueLetters[i] = 'f'; 
					break;
				case "Xwo?Kg": trueLetters[i] = 'g'; 
					break;
				case "CPa?8Y": trueLetters[i] = 'h'; 
					break;
				case "o?!9w4": trueLetters[i] = 'i'; 
					break;
				case "UzdBgX": trueLetters[i] = 'j'; 
					break;
				case "gcEI3h": trueLetters[i] = 'k'; 
					break;
				case "F#qIp?": trueLetters[i] = 'l'; 
					break;
				case "3YeSkm": trueLetters[i] = 'm'; 
					break;
				case "gFCXbS": trueLetters[i] = 'n'; 
					break;
				case "dHZ6g!": trueLetters[i] = 'o'; 
					break;
				case "kb65mt": trueLetters[i] = 'p'; 
					break;
				case "7b7099": trueLetters[i] = 'q'; 
					break;
				case "oHyVZ?": trueLetters[i] = 'r'; 
					break;
				case "XR#i3C": trueLetters[i] = 's'; 
					break;
				case "hp7X#v": trueLetters[i] = 't'; 
					break;
				case "SfyQSZ": trueLetters[i] = 'u'; 
					break;
				case "ACro8E": trueLetters[i] = 'v'; 
					break;
				case "gh4BjV": trueLetters[i] = 'w'; 
					break;
				case "8cUb0S": trueLetters[i] = 'x'; 
					break;
				case "Iey9ay": trueLetters[i] = 'y'; 
					break;
				case "j5Db9V": trueLetters[i] = 'z'; 
					break;
				case "2b6pD8": trueLetters[i] = 32; 
					break;
				case "M54?bE": trueLetters[i] = 33; 
					break; 
				case "Rp$wGi": trueLetters[i] = 34; 
					break; 
				case "I0uQ9Y": trueLetters[i] = 35; 
					break; 
				case "a9PjnL": trueLetters[i] = 36; 
					break; 
				case "nZVNug": trueLetters[i] = 37; 
					break; 
				case "7Jf!lN": trueLetters[i] = 38; 
					break; 
				case "2CU6Ft": trueLetters[i] = 39; 
					break; 
				case "OPRQfR": trueLetters[i] = 40; 
					break; 
				case "jY3zgw": trueLetters[i] = 41; 
					break; 
				case "DOVa2s": trueLetters[i] = 42; 
					break; 
				case "KP6JVz": trueLetters[i] = 43; 
					break; 
				case "wuws!m": trueLetters[i] = 44; 
					break; 
				case "HOQraS": trueLetters[i] = 45; 
					break; 
				case "IPNHri": trueLetters[i] = 46; 
					break; 
				case "7XEj5R": trueLetters[i] = 47; 
					break;
				case "p90dHj": trueLetters[i] = 48; 
					break;
				case "1i!Rc!": trueLetters[i] = 49; 
					break;
				case "h00pFz": trueLetters[i] = 50; 
					break;
				case "5p1Qp9": trueLetters[i] = 51; 
					break;
				case "7tU0fU": trueLetters[i] = 52; 
					break;
				case "Ukmety": trueLetters[i] = 53; 
					break;
				case "Hy32BQ": trueLetters[i] = 54; 
					break;
				case "Dgyllp": trueLetters[i] = 55; 
					break;
				case "MGvJXc": trueLetters[i] = 56; 
					break;
				case "MDCqbq": trueLetters[i] = 57; 
					break;
				case "YpkzKT": trueLetters[i] = 58; 
					break; 
				case "EkvUI0": trueLetters[i] = 59; 
					break; 
				case "eqBO7c": trueLetters[i] = 60; 
					break; 
				case "16RYrG": trueLetters[i] = 61; 
					break; 
				case "gFswj6": trueLetters[i] = 62; 
					break; 
				case "?TJQUA": trueLetters[i] = 63; 
					break; 
				case "inP8eS": trueLetters[i] = 64; 
					break;
				case "dmFtkx": trueLetters[i] = 91; 
					break;
				case "IVD3EC": trueLetters[i] = 92; 
					break;
				case "C4tdtH": trueLetters[i] = 93; 
					break;
				case "w$LsgQ": trueLetters[i] = 94; 
					break;
				case "1xRt1X": trueLetters[i] = 95; 
					break;
				case "4bbblY": trueLetters[i] = 96; 
					break;
				case "kToyO1": trueLetters[i] = 123; 
					break;
				case "aP1chC": trueLetters[i] = 124; 
					break; 
				case "Cfhven": trueLetters[i] = 125; 
					break;
				case "yvx?ZC": trueLetters[i] = 126; 
					break;
				default:break;
			}
		}
		
		return new String(trueLetters);
	}
	
	public void setProperty(String[] props){
		
		List<String> propsList = new ArrayList<>();
		
		for(int i = 0; i < props.length; i++){
				
			props[i] = encrypt(props[i], keys[i][0], keys[i][1]);
			propsList.add(props[i]);
		}
		
		ApplicationProperties propObj = new ApplicationProperties(propsList.get(0), propsList.get(1), propsList.get(2), propsList.get(3),
																  propsList.get(4), propsList.get(5), propsList.get(6), propsList.get(7),
																  propsList.get(8), propsList.get(9), propsList.get(10));
		businessDelegate.putRecord(propObj);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getProperty(PropertyType type) {
		
		Session session = businessDelegate.requestSession();
			
		Criteria criteria = session.createCriteria(ApplicationProperties.class);
		ApplicationProperties props = (ApplicationProperties) criteria.uniqueResult();

		String value;
		
		switch(type){
		
			case COMPANY:
				
				value = props.getCompany();
				value = decrypt(value, keys[0][0], keys[0][1]);
				return value;
				
			case APP:

				value = props.getApp();
				value = decrypt(value, keys[1][0], keys[1][1]);
				return value;
				
			case S3:

				value = props.getS3();
				value = decrypt(value, keys[2][0], keys[2][1]);
				return value;
				
			case SERVER:

				value = props.getServer();
				value = decrypt(value, keys[3][0], keys[3][1]);
				return value;
				
			case JENKINS:

				value = props.getJenkins();
				value = decrypt(value, keys[4][0], keys[4][1]);
				return value;
				
			case SONARQUBE:

				value = props.getSonarqube();
				value = decrypt(value, keys[5][0], keys[5][1]);
				return value;
				
			case K:

				value = props.getK();
				value = decrypt(value, keys[6][0], keys[6][1]);
				return value;
				
			case V:

				value = props.getV();
				value = decrypt(value, keys[7][0], keys[7][1]);
				return value;
				
			case FAPP:

				value = props.getFapp();
				value = decrypt(value, keys[8][0], keys[8][1]);
				return value;
				
			case LINKTOKEN:

				value = props.getLinkToken();
				value = decrypt(value, keys[9][0], keys[9][1]);
				return value;
				
			case S3BUCKET:

				value = props.getBucketURL();
				value = decrypt(value, keys[10][0], keys[10][1]);
				return value;	
				
			default: 
				
				throw new IllegalArgumentException("Invalid Property Type!");
		}
	}

	private boolean condition(int num){
		if(num > 47 && num < 57) return true;
		else if(num > 65 && num < 90) return true;
		else if(num > 97 && num < 122) return true;
		return false;
	}
	
	public String getRandom(int length) {
		
		Random randObj = new Random();
		
		String rand = "";
		
		for(int i = 0; i < length; i++){
			
			char myChar;
			
			int num = randObj.nextInt();
				
			if(condition(num)){
				
				myChar = (char)num;
			
				rand += myChar;
				
			} else {
				
				// Not Alphanumeric -- Rerun
				
				i--;
			}
		}
	
		return rand;
	}
}

