package softwareInstaller;

import java.io.IOException;
import java.lang.*;
import java.util.Scanner;
public class CmdExec {

	public static String getCpuId() throws IOException{
		String cpuId;
		Process process=Runtime.getRuntime().exec("wmic cpu get processorId");
		Scanner sc=new Scanner(process.getInputStream());
		sc.next();
		cpuId=sc.next();
		sc.close();
		return cpuId;
	}
	
	public static String getDiskId() {
		return "";
	}
	
}
