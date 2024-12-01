package com.epay.transaction.util;

//import com.sbi.epay.transaction.applicationConstant.AggTransactionConstant;
//import com.sbi.epay.transaction.logs.LogWritter;

import com.epay.transaction.util.AggTransactionConstant;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class AggBase64CoderAndStandardHashing {

	private static final String CLASS_NAME = AggBase64CoderAndStandardHashing.class.getName();
	
	public AggBase64CoderAndStandardHashing()
	{
	}
	
	// Business Methods starts
		// Mapping table from 6-bit nibbles to Base64 characters.
		private static char[]    map1 = new char[64];

		static
		{
			int i=0;
			for (char c='A'; c<='Z'; c++) map1[i++] = c;
			for (char c='a'; c<='z'; c++) map1[i++] = c;
			for (char c='0'; c<='9'; c++) map1[i++] = c;
			map1[i++] = '+'; map1[i++] = '/';
		}

		// Mapping table from Base64 characters to 6-bit nibbles.
		private static byte[]    map2 = new byte[128];

		static
		{
			for (int i=0; i<map2.length; i++) map2[i] = -1;
			for (int i=0; i<64; i++) map2[map1[i]] = (byte)i;
		}

		/**
		 * Encodes a string into Base64 format.
		 * No blanks or line breaks are inserted.
		 * @param s  a String to be encoded.
		 * @return   A String with the Base64 encoded data.
		 */
		public String encodeString (String s)
		{
			return new String(encode(s.getBytes()));
		}

		/**
		 * Encodes a byte array into Base64 format.
		 * No blanks or line breaks are inserted.
		 * @param in  an array containing the data bytes to be encoded.
		 * @return    A character array with the Base64 encoded data.
		 */
		public static char[] encode (byte[] in) 
		{
			return encode(in,in.length);
		}

		/**
		 * Encodes a byte array into Base64 format.
		 * No blanks or line breaks are inserted.
		 * @param in   an array containing the data bytes to be encoded.
		 * @param iLen number of bytes to process in <code>in</code>.
		 * @return     A character array with the Base64 encoded data.
		 */
		public static char[] encode (byte[] in, int iLen)
		{
			int oDataLen = (iLen*4+2)/3;       // output length without padding
			int oLen = ((iLen+2)/3)*4;         // output length including padding
			char[] out = new char[oLen];
			int ip = 0;
			int op = 0;

			while (ip < iLen)
			{
				int i0 = in[ip++] & 0xff;
				int i1 = ip < iLen ? in[ip++] & 0xff : 0;
				int i2 = ip < iLen ? in[ip++] & 0xff : 0;
				int o0 = i0 >>> 2;
				int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
				int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
				int o3 = i2 & 0x3F;
				out[op++] = map1[o0];
				out[op++] = map1[o1];
				out[op] = op < oDataLen ? map1[o2] : '='; op++;
				out[op] = op < oDataLen ? map1[o3] : '='; op++;
			}

			return out;
		}

		/**
		 * Decodes a string from Base64 format.
		 * @param s  a Base64 String to be decoded.
		 * @return   A String containing the decoded data.
		 * @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
		 */
		public String decodeString (String s)
		{
			return new String(decode(s));
		}

		/**
		 * Decodes a byte array from Base64 format.
		 * @param s  a Base64 String to be decoded.
		 * @return   An array containing the decoded data bytes.
		 * @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
		 */
		public static byte[] decode (String s)
		{
			return decode(s.toCharArray()); 
		}

		/**
		 * Decodes a byte array from Base64 format.
		 * No blanks or line breaks are allowed within the Base64 encoded data.
		 * @param in  a character array containing the Base64 encoded data.
		 * @return    An array containing the decoded data bytes.
		 * @throws    IllegalArgumentException if the input is not valid Base64 encoded data.
		 */
		public static byte[] decode (char[] in)
		{
			int iLen = in.length;
			if (iLen%4 != 0) throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
			while (iLen > 0 && in[iLen-1] == '=') iLen--;
			int oLen = (iLen*3) / 4;
			byte[] out = new byte[oLen];
			int ip = 0;
			int op = 0;

			while (ip < iLen) 
			{
				int i0 = in[ip++];
				int i1 = in[ip++];
				int i2 = ip < iLen ? in[ip++] : 'A';
				int i3 = ip < iLen ? in[ip++] : 'A';
				if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
					throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
				int b0 = map2[i0];
				int b1 = map2[i1];
				int b2 = map2[i2];
				int b3 = map2[i3];
				if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
					throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
				int o0 = ( b0       <<2) | (b1>>>4);
				int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
				int o2 = ((b2 &   3)<<6) |  b3;
				out[op++] = (byte)o0;
				if (op<oLen) out[op++] = (byte)o1;
				if (op<oLen) out[op++] = (byte)o2;
			}

			return out;
		}

		
		public static String password_hash_SHA256(String password)
		{
			if(null==password || ("").equals(password))
			{
				password = "";
				return password;
			}

			byte[] pwd_bytes = password.getBytes();

			byte[] hash_value = null;

			try
			{
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.update(pwd_bytes);
				hash_value = digest.digest();
			} catch (NullPointerException e) {
				//LogWritter.InfoLOGGER.info(CLASS_NAME,e);
			} catch (ClassCastException e) {
				//LogWritter.InfoLOGGER.info(CLASS_NAME,e);
			} catch (Exception e) {
				//LogWritter.InfoLOGGER.info(CLASS_NAME,e);
			}

			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer(AggTransactionConstant.querySize_10);
			for (int i = 0; i < hash_value.length; i++) 
			{	
				sb.append(Integer.toString((hash_value[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString().toUpperCase();
		}


		// Business Methods ends
		public static String password_hash_SHA512(String password)
		{
			StringBuffer sb = new StringBuffer(AggTransactionConstant.querySize_10);
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
				messageDigest.update(password.getBytes("UTF-8"));
				byte[] digestBytes = messageDigest.digest();

				String hex = null;
				for (int i = 0; i < digestBytes.length; i++)
				{
					hex = Integer.toHexString(0xFF & digestBytes[i]);
					if (hex.length() < 2)
						sb.append("0");
					sb.append(hex);
				}
				String psw = sb.toString();
			} catch (NullPointerException ex) {
			//	LogWritter.InfoLOGGER.info(CLASS_NAME,ex);

			} catch (ClassCastException ex) {
			//	LogWritter.InfoLOGGER.info(CLASS_NAME,ex);

			} catch (Exception ex) {
			//	LogWritter.InfoLOGGER.info(CLASS_NAME,ex);

			}
			return new String(sb);
		}




		// Vesion1 and Version4 for Random generation of secrerId and SecretKey
		private static long get64LeastSignificantBitsForVersion1() {
			Random random = new Random();
			long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
			long variant3BitFlag = 0x8000000000000000L;
			return random63BitLong | variant3BitFlag;
		}

		private static long get64MostSignificantBitsForVersion1() {
			final long currentTimeMillis = System.currentTimeMillis();
			final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32; 
			final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
			final long version = 1 << 12; 
			final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
			return time_low | time_mid | version | time_hi;
		}



		public static UUID generateType1UUID() {
			long most64SigBits = get64MostSignificantBitsForVersion1();
			long least64SigBits = get64LeastSignificantBitsForVersion1();
			return new UUID(most64SigBits, least64SigBits);
		}




		private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
		public static String bytesToHex(byte[] bytes) {
			char[] hexChars = new char[bytes.length * 2];
			for (int j = 0; j < bytes.length; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 2] = HEX_ARRAY[v >>> 4];
				hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
			}
			return new String(hexChars);
		}



		public static String generateRandomAPPSecretKey() throws NoSuchAlgorithmException
		{
			UUID randomKeySecret=UUID.randomUUID() ;

			MessageDigest salt=  MessageDigest.getInstance("SHA-256");
			salt.update(randomKeySecret.toString().getBytes(StandardCharsets.UTF_8));

			String hexValue=bytesToHex(salt.digest());

			return hexValue;


		}


		public static String generateRandomAppKeyId()
		{

			UUID randomKeyId=  generateType1UUID();
			return randomKeyId.toString();


		}


	public static String byteToStringHash(byte[] hash_SHA512)
	{
		BigInteger number = new BigInteger(1, hash_SHA512);
		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));
		// Pad with leading zeros
		while (hexString.length() < 64)
		{
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}

}
