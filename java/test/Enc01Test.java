package digipages.dev.utility;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Enc01Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testEncode() {
		String key="!@#!@#432234312";
		String downloadToken="hafsdohuq234r9pqwefrphafhp9oh98pvfr3 b34q08qwef98y7r13 y1h245v8 v34p9f213345v134rwtevw vere4vt3wq4v t43";
		Enc01 enc = new Enc01(key,downloadToken);
		try {
			byte result[]=enc.encode("META-INF\\container.xml", "Hello World from Eric, for Test of enc01.".getBytes("UTF-8"));
			System.out.println("encoded="+Hex.encodeHexString(result));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	@Test
	public void testDecode() {
		String key="!@#!@#432234312";
		String downloadToken="hafsdohuq234r9pqwefrphafhp9oh98pvfr3 b34q08qwef98y7r13 y1h245v8 v34p9f213345v134rwtevw vere4vt3wq4v t43";
		Enc01 enc = new Enc01(key,downloadToken);
		try {
			byte result[]=enc.encode("META-INF\\container.xml", "Hello World from Eric, for Test of enc01.".getBytes("UTF-8"));
			
			Enc01 dec = new Enc01(key,downloadToken);
			byte restored[] = dec.decode("META-INF\\container.xml", result);
			String restoreStr = new String(restored,"UTF-8");
			if (!restoreStr.contains("for Test"))
			{
				fail();
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}
