package digipages.dev.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Enc01 {

	public Enc01(String DownloadToken) {
		this.downloadToken=DownloadToken;
	}

	public byte[] encode(String filepath,byte[] src) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte [] ret= new byte[src.length];
		byte key[]=getRealKey(downloadToken,filepath);
		int loc = 0;
		while (loc < src.length)
		{
			encRange(src,loc,key.length,ret, loc, key);
			loc +=key.length;
		}
		return ret;
	}

	// xor =decode = encode;
	public byte[] decode(String filePath,byte[] src) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return encode(filePath,src);
	}





	private String downloadToken="";
	
	private void encRange(byte[] src, int loc, int length, byte[] ret, int loc2,byte key[]) {
		for (int i=0;i<length && i+loc<src.length ;i++)
		{
			int xloc=loc +i;
			ret[xloc] = (byte)(src[xloc] ^ key[i]);
		}

	}



	private byte[] getRealKey(String DownloadToken, String filePath)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte [] realKey = null;
		int offset = calcOffset(filePath);
		String newSeed = DownloadToken.substring(0,offset) + filePath + DownloadToken.substring(offset);
		MessageDigest md  = MessageDigest.getInstance("SHA-256");
		md.update(newSeed.getBytes("UTF-8"));
		realKey = md.digest();
		return realKey;
	}

	private int calcOffset(String filePath) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(filePath.getBytes("UTF-8"));
		byte [] x= md.digest();

		int sum = 0;
		for (int i=0; i< x.length;i+=2)
		{

			byte tmp[] = new byte[2];
			System.arraycopy(x, i, tmp, 0, 2);
			int value = byteArrayToSmallInt(tmp);
			sum+=value;
		}
		int offset = sum%64;

		return offset;
	}


	public static int byteArrayToSmallInt(byte[] b)
	{
	    return   b[1] & 0xFF |
	            (b[0] & 0xFF) << 8 ;
	}


}
