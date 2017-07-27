package softwareInstaller;

import java.io.*;
import java.util.*;

public final class Main {
	/**
	 * 
	 * 命令行测试用
	 */
//	public static void main(String[] args) throws IOException {
//		System.out.println("欢迎进行认证，软件版本为"+softwareVersion+"，功能开关状态为"+funcationSwitch+"。创建本地密钥请输入1，核对密钥请输入2");
//		InputStreamReader is=new InputStreamReader(System.in);
//		Scanner sc=new Scanner(is);
//		String choice=sc.next();
//		String url;
//		switch(choice) {
//		case "1":
//			System.out.println("请输入保存密钥的完整路径：");
//			url=sc.next();
//			createKey(url);
//			break;
//		case "2":
//			System.out.println("请输入读取密钥的完整路径：");
//			url=sc.next();
//			checkKey(url);
//			break;
//		default:
//			System.out.println("输入错误请重试");
//		}
//		sc.close();
//		//createKey("AccessKey.key");
//		//checkKey("AccessKey.key");
//		
//	}
	//软件版本
	public static String softwareVersion="1.2.364";
	//功能开关
	public static String funcationSwitch="true";
	
	public static String[] createKey(String path) throws IOException {
		String[] own_info=new String[5];
		own_info=ownKey();
//		for(String ele:own_info) {
//			System.out.println(ele);
//		}
		Data own_data=new Data(own_info[0], own_info[1], own_info[2], own_info[3]);
		int accessKey=own_data.dataEncode();
		File.createKeyFile(path,accessKey);
		//System.out.println(accessKey);
		//System.out.println("Success!");
		own_info[4]=String.valueOf(accessKey);
		return own_info;
	};
	
	public static boolean checkKey(String path) throws IOException {
		int loadKey=File.loadKeyFile(path);
		String[] own_info=ownKey();
		String own_str=own_info[0]+own_info[1]+own_info[2]+own_info[3];
		int own_key=own_str.hashCode();
		if(loadKey==own_key) {
			return true;
			//System.out.println("认证成功，软件可以正常使用!");
		}
		else {
			return false;
			//System.out.println("认证失败，请重新购买！");
		}
	};
	
	public static String[] ownKey() throws IOException {
		//加载读取硬盘序列号的C++类库
		System.load(System.getProperty("user.dir")+"\\CmdExec.dll");
		String[] info= {"","","","",""};
		String cpuId=CmdExec.getCpuId();
		String diskId=CmdExec.getDiskId();
		//System.out.println(diskId);
		info[0]=cpuId;
		info[1]=diskId;
		info[2]=softwareVersion;
		info[3]=funcationSwitch;
		Data own_data=new Data(info[0], info[1], info[2], info[3]);
		int accessKey=own_data.dataEncode();
		info[4]=String.valueOf(accessKey);
		return info;
	}
	
}
