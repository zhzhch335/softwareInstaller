package softwareInstaller;

import java.io.*;
import java.util.Scanner;
public class CmdExec {
	/*
	 * 测试用，这里不加载类库，需要从getDiskIdResult()的方法中加载动态类库
	public static void main(String[] args) {	
		System.load(System.getProperty("user.dir")+"\\library\\CmdExec (2).dll");
		//CmdExec.getDiskId();
		System.out.print(CmdExec.getDiskId());
//		ByteArrayOutputStream bo=new ByteArrayOutputStream();
//		PrintStream ps=new PrintStream(bo);
//		System.setOut(ps);
//		CmdExec.getDiskId();
//		System.out.println(bo.toString());
		//System.out.println("这就是："+CmdExec.getDiskIdResult());
	}*/
	
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
//	public static String getDiskIdResult() {
//		System.load(System.getProperty("user.dir")+"\\library\\CmdExec.dll");
//		PrintStream ps=new PrintStream(System.out);
//		Scanner sc=new Scanner();
//		CmdExec.getDiskId();
//		String element=sc.next();
//		String result = null;
//		while(element != null) {
//			result=result+element;
//			element=sc.next();
//		}
//		sc.close();
//		return result;
//	}
}
