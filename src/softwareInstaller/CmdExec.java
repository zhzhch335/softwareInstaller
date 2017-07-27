package softwareInstaller;

import java.io.*;
import java.util.Scanner;
public class CmdExec {
	
	//从C++调用硬盘信息
	public static native String getDiskId();
	//从命令提示符调用CPU信息
	public static String getCpuId() throws IOException{
		String cpuId;
		Process process=Runtime.getRuntime().exec("wmic cpu get processorId");
		Scanner sc=new Scanner(process.getInputStream());
		sc.next();
		cpuId=sc.next();
		sc.close();
		return cpuId;
	}

}
