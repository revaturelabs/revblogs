package com.revature.service.impl;

import java.util.concurrent.ThreadLocalRandom;

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
		
		return temp;
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
        //Use a new constructor or use as is below
        //Setting to 0 then reassigning is a waste of resources
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
		
		//Use a new constructor or use as is below
		//Setting to 0 then reassigning is a waste of resources
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
				return 48;
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
			default:  return 77;
		}
	}
	
	// Decryption
	public String decrypt(String target, String key1, String key2){
		
		String temp = target;
		
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
        //Use a new constructor or use as is below
        //Setting to 0 then reassigning is a waste of resources
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
		
		int cipherLetter = 0;
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
				return 48;
		}
	}
	private char decipherOther(char other){
		
		switch(other){
		
		case 32: return 37;
		case 33: return 35;
		case 34: return 36;
		case 35: return 61;
		case 36: return 44;
		case 37: return 64;
		case 38: return 93;
		case 39: return 94;
		case 40: return 62;
		case 41: return 34;
		case 42: return 33; 
		case 43: return 91;
		case 44: return 123;
		case 45: return 32;
		case 46: return 58;
		case 47: return 46;
		case 58: return 39;
		case 59: return 45;
		case 60: return 43;
		case 61: return 47;
		case 62: return 63;
		case 63: return 96;
		case 64: return 42; 
		case 91: return 40;
		case 92: return 38;
		case 93: return 59;
		case 94: return 125;
		case 95: return 124;
		case 96: return 126;
		case 123: return 41;
		case 124: return 92;
		case 125: return 60;
		case 126: return 95;
		default: return 77;
		}
	}
	
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
	
	public void setProperty(String[] props, PropertyType... type){
		
		String[] propertyObj = new String[type.length];
		String[] passedProps = new String[type.length];
		
		for(int i = 0; i < passedProps.length; i++){
			
			passedProps[i] = props[i];
		}
		
		int counter = 0;
		
		for(PropertyType p : type){
			
			switch(p){
			
				case COMPANY:
					
					props[counter] = encrypt(passedProps[counter], keys[0][0], keys[0][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case APP:
	
					props[counter] = encrypt(passedProps[counter], keys[1][0], keys[1][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case S3:
	
					props[counter] = encrypt(passedProps[counter], keys[2][0], keys[2][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case SERVER:
	
					props[counter] = encrypt(passedProps[counter], keys[3][0], keys[3][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case JENKINS:
	
					props[counter] = encrypt(passedProps[counter], keys[4][0], keys[4][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case SONARQUBE:
	
					props[counter] = encrypt(passedProps[counter], keys[5][0], keys[5][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case K:
	
					props[counter] = encrypt(passedProps[counter], keys[6][0], keys[6][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case V:
	
					props[counter] = encrypt(passedProps[counter], keys[7][0], keys[7][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case FAPP:
	
					props[counter] = encrypt(passedProps[counter], keys[8][0], keys[8][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case LINKTOKEN:
	
					props[counter] = encrypt(passedProps[counter], keys[9][0], keys[9][1]);
					propertyObj[counter] = props[counter];
					counter++;
					
				case S3BUCKET:
	
					props[counter] = encrypt(passedProps[counter], keys[10][0], keys[10][1]);
					propertyObj[counter] = props[counter];
					counter++;
			}
		}
		
	
		ApplicationProperties propObj = new ApplicationProperties(propertyObj[0], propertyObj[1], propertyObj[2], propertyObj[3],
															  	  propertyObj[4], propertyObj[5], propertyObj[6], propertyObj[7],
															      propertyObj[8], propertyObj[9], propertyObj[10]);
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

				return null;
		}
	}

	
	public String getRandom(int length) {
		
		String rand = "";
		
		for(int i = 0; i < length; i++){
			
			if(i == 0){
				
				rand += (char)ThreadLocalRandom.current().nextInt(97, 122 + 1);
				
				if((i % 2) == 0){
					
					rand += (char)ThreadLocalRandom.current().nextInt(48, 57 + 1);
					
				} else {
					
					rand += (char)ThreadLocalRandom.current().nextInt(65, 90 + 1);
				}
			}
		}
	
		return rand;
	}
}

