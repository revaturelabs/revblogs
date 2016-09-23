package com.revature.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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

public class CryptImpl implements Crypt{

	private String[][] keys = new String[][]{
			
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
	
	private BusinessDelegate businessDelegate;
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	/**
	 * 
	 *  This method salts && encrypts the target.
	 *  
	 * @param target : The string you want encrypted.
	 * @return Returns the encrypted target.
	 * 
	 */
	public String encrypt(String target){
		
		String temp = saltTarget(target);
		String[] e_keys = bindKeys(temp);
	
		return encrypt(temp, e_keys[0], e_keys[1]);
	}
	private String encrypt(String target, String key1, String key2){
		
		String temp = target;
		String holder = "";
		char[] tempArray = temp.toCharArray();
		
		//------------------
		// 1st Cipher
		for(int i = 0; i < key2.length(); i++){
			
			temp = encrypt(temp, key1);
		}
		
		//------------------
		// Masking
		String st = "" + tempArray[0] + tempArray[1];
		
		for(char c : tempArray){
			
			holder += maskRegions(c);
		}
		
		//------------------
		// 2nd Cipher
		for(int j = 0; j < key1.length(); j++){
			
			holder = encrypt(holder, key2);
		}
		
		holder = st + holder;
	
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
		
			case 0:  return 55;
			case 1:  return 52;
			case 2:  return 51;
			case 3:  return 56;
			case 4:  return 49;
			case 5:  return 48;
			case 6:  return 57;
			case 7:  return 53;
			case 8:  return 50;
			case 9:  return 54;
			default: return 0; // NULL
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
	private String saltTarget(String target){
		
		Calendar cal = Calendar.getInstance();
		String salt = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			
		if(Integer.parseInt(salt) < 10){
			
			salt = "0" + salt;
		}
	
		switch(Integer.parseInt(salt)){

			case  1: return salt + "4OY#nR" + target + "690";
			case  2: return salt + "kt4oRA" + target + "848";
			case  3: return salt + "tqDHZe" + target + "290";
			case  4: return salt + "VOYhUy" + target + "575";
			case  5: return salt + "9kA?3e" + target + "692";
			case  6: return salt + "7mEY2Y" + target + "447";
			case  7: return salt + "Nlt$Yl" + target + "040";
			case  8: return salt + "J7U9wl" + target + "408";
			case  9: return salt + "lrJy?z" + target + "199";
			case 10: return salt + "aG9SGv" + target + "571";
			case 11: return salt + "la9Fmw" + target + "352";
			case 12: return salt + "5Fbdtn" + target + "394";
			case 13: return salt + "b#?LZK" + target + "922";
			case 14: return salt + "@b8rqK" + target + "328";
			case 15: return salt + "E@ZwEU" + target + "432";
			case 16: return salt + "NssU1i" + target + "107";
			case 17: return salt + "dSa#bb" + target + "706";
			case 18: return salt + "GavTJP" + target + "231";
			case 19: return salt + "prp5sj" + target + "189";
			case 20: return salt + "5KNmg2" + target + "825";
			case 21: return salt + "erq17Q" + target + "161";
			case 22: return salt + "xc21Ha" + target + "330";
			case 23: return salt + "wW6!6X" + target + "212";
			case 24: return salt + "pbk3Ob" + target + "716";
			case 25: return salt + "zHLL7J" + target + "486";
			case 26: return salt + "gMcyh0" + target + "095";
			case 27: return salt + "UaFhTf" + target + "242";
			case 28: return salt + "XJh?QJ" + target + "764";
			case 29: return salt + "gsqrzD" + target + "182";
			case 30: return salt + "AFOB@H" + target + "943";
			case 31: return salt + "YxVKTz" + target + "452";
			default: return null;
		}
	}
	private String saltTarget(String target, String day){
		
		switch(Integer.parseInt(day)){

			case  1: return day + "4OY#nR" + target + "690";
			case  2: return day + "kt4oRA" + target + "848";
			case  3: return day + "tqDHZe" + target + "290";
			case  4: return day + "VOYhUy" + target + "575";
			case  5: return day + "9kA?3e" + target + "692";
			case  6: return day + "7mEY2Y" + target + "447";
			case  7: return day + "Nlt$Yl" + target + "040";
			case  8: return day + "J7U9wl" + target + "408";
			case  9: return day + "lrJy?z" + target + "199";
			case 10: return day + "aG9SGv" + target + "571";
			case 11: return day + "la9Fmw" + target + "352";
			case 12: return day + "5Fbdtn" + target + "394";
			case 13: return day + "b#?LZK" + target + "922";
			case 14: return day + "@b8rqK" + target + "328";
			case 15: return day + "E@ZwEU" + target + "432";
			case 16: return day + "NssU1i" + target + "107";
			case 17: return day + "dSa#bb" + target + "706";
			case 18: return day + "GavTJP" + target + "231";
			case 19: return day + "prp5sj" + target + "189";
			case 20: return day + "5KNmg2" + target + "825";
			case 21: return day + "erq17Q" + target + "161";
			case 22: return day + "xc21Ha" + target + "330";
			case 23: return day + "wW6!6X" + target + "212";
			case 24: return day + "pbk3Ob" + target + "716";
			case 25: return day + "zHLL7J" + target + "486";
			case 26: return day + "gMcyh0" + target + "095";
			case 27: return day + "UaFhTf" + target + "242";
			case 28: return day + "XJh?QJ" + target + "764";
			case 29: return day + "gsqrzD" + target + "182";
			case 30: return day + "AFOB@H" + target + "943";
			case 31: return day + "YxVKTz" + target + "452";
			default: return null;
		}
	}
	private String[] bindKeys(String target){
		
		String[] tKeys = new String[2];
		
		int temp = Integer.parseInt(target.substring(0, 2));
		
		switch(temp){
			
			case  1:
				tKeys[0] = this.keys[0][0];
				tKeys[1] = this.keys[0][1];
				break;
			case  2: 
				tKeys[0] = this.keys[1][0];
				tKeys[1] = this.keys[1][1];
				break;
			case  3: 
				tKeys[0] = this.keys[2][0];
				tKeys[1] = this.keys[2][1];
				break;
			case  4: 
				tKeys[0] = this.keys[3][0];
				tKeys[1] = this.keys[3][1];
				break;
			case  5: 
				tKeys[0] = this.keys[4][0];
				tKeys[1] = this.keys[4][1];
				break;
			case  6: 
				tKeys[0] = this.keys[5][0];
				tKeys[1] = this.keys[5][1];
				break;
			case  7: 
				tKeys[0] = this.keys[6][0];
				tKeys[1] = this.keys[6][1];
				break;
			case  8: 
				tKeys[0] = this.keys[7][0];
				tKeys[1] = this.keys[7][1];
				break;
			case  9:
				tKeys[0] = this.keys[8][0];
				tKeys[1] = this.keys[8][1];
				break;
			case 10:
				tKeys[0] = this.keys[9][0];
				tKeys[1] = this.keys[9][1];
				break;
			case 11:
				tKeys[0] = this.keys[10][0];
				tKeys[1] = this.keys[10][1];
				break;
			case 12:
				tKeys[0] = this.keys[0][1];
				tKeys[1] = this.keys[0][0];
				break;
			case 13:
				tKeys[0] = this.keys[1][1];
				tKeys[1] = this.keys[1][0];
				break;
			case 14:
				tKeys[0] = this.keys[2][1];
				tKeys[1] = this.keys[2][0];
				break;
			case 15:
				tKeys[0] = this.keys[3][1];
				tKeys[1] = this.keys[3][0];
				break;
			case 16:
				tKeys[0] = this.keys[4][1];
				tKeys[1] = this.keys[4][0];
				break;
			case 17:
				tKeys[0] = this.keys[5][1];
				tKeys[1] = this.keys[5][0];
				break;
			case 18:
				tKeys[0] = this.keys[6][1];
				tKeys[1] = this.keys[6][0];
				break;
			case 19:
				tKeys[0] = this.keys[7][1];
				tKeys[1] = this.keys[7][0];
				break;
			case 20:
				tKeys[0] = this.keys[8][1];
				tKeys[1] = this.keys[8][0];
				break;
			case 21:
				tKeys[0] = this.keys[9][1];
				tKeys[1] = this.keys[9][0];
				break;
			case 22:
				tKeys[0] = this.keys[10][1];
				tKeys[1] = this.keys[10][0];
				break;
			case 23:
				tKeys[0] = this.keys[0][0];
				tKeys[1] = this.keys[1][1];
				break;
			case 24:
				tKeys[0] = this.keys[1][0];
				tKeys[1] = this.keys[2][1];
				break;
			case 25:
				tKeys[0] = this.keys[3][0];
				tKeys[1] = this.keys[4][1];
				break;
			case 26:
				tKeys[0] = this.keys[5][0];
				tKeys[1] = this.keys[6][1];
				break;
			case 27:
				tKeys[0] = this.keys[7][0];
				tKeys[1] = this.keys[8][1];
				break;
			case 28:
				tKeys[0] = this.keys[9][0];
				tKeys[1] = this.keys[10][1];
				break;
			case 29:
				tKeys[0] = this.keys[0][0];
				tKeys[1] = this.keys[5][1];
				break;
			case 30:
				tKeys[0] = this.keys[3][0];
				tKeys[1] = this.keys[7][1];
				break;
			case 31:
				tKeys[0] = this.keys[6][0];
				tKeys[1] = this.keys[9][1];
				break;
			default:
				tKeys[0] = null;
				tKeys[1] = null;
				break;
		}
		
		return tKeys;
	}
	
	/**
	 * 
	 * This method is used to compare user supplied values to encrypted fields.
	 * 
	 * @param input  : User supplied value. Such as: password from user.
	 * @param hashed : Encrypted version of that value. Such as: password from database.
	 * @return true if they match, false if they don't.
	 */
	public boolean validate(String input, String hashed){
		
		String[] vKeys = bindKeys(hashed);
		String day = hashed.substring(0, 2);
		String salted = saltTarget(input, day);
		
		boolean valid;
		
		if(encrypt(salted, vKeys[0], vKeys[1]).equals(hashed)){
			
			valid = true;
			
		} else {
			
			valid = false;
		}
		
		return valid;
	}
	
	//-------------------------------------------------------------------------------------------------
	// Decryption
	private String decrypt(String target){
		
		String dKeys[] = bindKeys(target);
		
		return decrypt(target, dKeys[0], dKeys[1]);
	}
	private String decrypt(String target, String key1, String key2){
		
		String temp = target;
		temp = temp.substring(2);
		
		//------------------
		// Reveal Cipher
		for(int i = 0; i < key1.length(); i++){
			
			temp = decrypt(temp, key2);
		}
		
		//------------------
		// Reveal Mask
		temp = clearMask(temp);
		
		return unsaltTarget(temp);
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
		
			case 0:  return 53;
			case 1:  return 52;
			case 2:  return 56;
			case 3:  return 50;
			case 4:  return 49;
			case 5:  return 55;
			case 6:  return 57;
			case 7:  return 48;
			case 8:  return 51;
			case 9:  return 54;
			default: return 0; // NULL
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

		String mini;
		
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
				default:
					break;
			}
		}
		
		return new String(trueLetters);
	}
	private String unsaltTarget(String target){
		
		char[] temp = target.toCharArray();
		
		String result = "";
		
		for(int i = 8; i < temp.length - 3; i++){
			
			result += temp[i];
		}
		
		return result;
	}
	
	//-------------------------------------------------------------------------------------------------
	// Encryption Related Tasks 
	public void setProperty(String[] props){
		
		if(props.length == 11){

			List<String> propsList = new ArrayList<>();
			
			for(int i = 0; i < props.length; i++){
					
				props[i] = encrypt(props[i]);
				propsList.add(props[i]);
			}
			
			ApplicationProperties propObj = new ApplicationProperties(propsList.get(0), propsList.get(1), propsList.get(2), propsList.get(3),
																	  propsList.get(4), propsList.get(5), propsList.get(6), propsList.get(7),
																	  propsList.get(8), propsList.get(9), propsList.get(10));
			businessDelegate.putRecord(propObj);
		}
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
				value = decrypt(value);
				return value;
				
			case APP:

				value = props.getApp();
				value = decrypt(value);
				return value;
				
			case S3:

				value = props.getS3();
				value = decrypt(value);
				return value;
				
			case SERVER:

				value = props.getServer();
				value = decrypt(value);
				return value;
				
			case JENKINS:

				value = props.getJenkins();
				value = decrypt(value);
				return value;
				
			case SONARQUBE:

				value = props.getSonarqube();
				value = decrypt(value);
				return value;
				
			case K:

				value = props.getK();
				value = decrypt(value);
				return value;
				
			case V:

				value = props.getV();
				value = decrypt(value);
				return value;
				
			case FAPP:

				value = props.getFapp();
				value = decrypt(value);
				return value;
				
			case LINKTOKEN:

				value = props.getLinkToken();
				value = decrypt(value);
				return value;
				
			case S3BUCKET:

				value = props.getBucketURL();
				value = decrypt(value);
				return value;	
				
			default: 
				
				throw new AssertionError("Impossible Case");
		}
	}

	private boolean condition(int num){
		
		if(num > 47 && num < 57){
			return true;
		}
		else if(num > 65 && num < 90){
			return true;
		}
		else if(num > 97 && num < 122){
			return true;
		}
		else {
			return false;
		}
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
				
				// Not alphanumeric, Rerun
				
				i--;
			}
		}
	
		return rand;
	}
}

