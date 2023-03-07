package com.esprit.gdp;

import java.text.ParseException;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class ______MDP {

	public static void main(String[] args) throws java.io.IOException, ParseException
	{
		String mpCryptoPassword = "salt";

		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(mpCryptoPassword);
		
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(mpCryptoPassword);
		System.out.println(decryptor.decrypt("KQuFYD1neSMxzCiPYfuxPR2HUSyQbyQm"));
	}

}