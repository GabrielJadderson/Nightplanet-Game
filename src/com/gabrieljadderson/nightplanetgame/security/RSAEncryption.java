package com.gabrieljadderson.nightplanetgame.security;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

/**
 * @author Gabriel Jadderson
 * Blog www.goldenpackagebyanuj.blogspot.com
 * RSA - Encrypt Data using Public Key
 * RSA - Descypt Data using Private Key
 */
public class RSAEncryption
{
	
	private static final String PUBLIC_KEY_FILE = "Public.key";
	private static boolean ERROR = false;
	
	/**
	 * Encrypt Data
	 *
	 * @param data
	 * @throws IOException
	 */
	public static byte[] encryptData(String data) throws IOException
	{
		System.out.println("[RSA] Encryption Started..");
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = null;
		try
		{
			PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			encryptedData = cipher.doFinal(dataToEncrypt);
		} catch (Exception e)
		{
			e.printStackTrace();
			ERROR = true;
			System.out.println("[RSA] Exception Thrown. ERROR CODE 1");
		} finally
		{
			if (!ERROR)
			{
				System.out.println("[RSA] Encryption Finished Sucessfully");
			}
		}
		return encryptedData;
	}
	
	/**
	 * read Public Key From File
	 *
	 * @param fileName
	 * @return PublicKey
	 * @throws IOException
	 */
	private static PublicKey readPublicKeyFromFile(String fileName) throws IOException
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = new FileInputStream(new File(fileName));
			ois = new ObjectInputStream(fis);
			
			BigInteger modulus = (BigInteger) ois.readObject();
			BigInteger exponent = (BigInteger) ois.readObject();
			
			//Get Public Key
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);
			
			return publicKey;
			
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("[RSA] Exception Thrown. ERROR CODE 2");
		} finally
		{
			if (ois != null)
			{
				ois.close();
				if (fis != null)
				{
					fis.close();
				}
			}
		}
		return null;
	}
}