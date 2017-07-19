package softwareInstaller;

import java.io.IOException;
import java.util.Arrays;

public final class Main {
	public static void main(String[] args) throws IOException {
		createKey("/");
	}
	
	private static String softwareVersion="1.2.364";
	private static String funcationSwitch="false";
	
	private static void createKey(String path) throws IOException {
		String[] own_info=ownKey();
//		for(String ele:own_info) {
//			System.out.println(ele);
//		}
		//Data own_data=new Data(own_info[1], own_info[2], own_info[3], own_info[4]);
		Data own_data=new Data(own_info[0], own_info[1], own_info[2], own_info[3]);
		int accessKey=own_data.dataEncode();
		//File.createKeyFile(path,accessKey);
		System.out.println(accessKey);
		System.out.println("Success!");
	};
	
	private void checkKey(String path) throws IOException {
		String[] own_info=ownKey();
		String accessKey=File.loadKeyFile(path);
		String[] info=Data.dataDecode(accessKey);
		if(Arrays.equals(own_info, info)) {
			System.out.println("Fail!");
		}
		else{
			System.out.println("Success!");
		}
	};
	
	private static String[] ownKey() throws IOException {
		//加载读取硬盘序列号的C++类库
		System.load(System.getProperty("user.dir")+"\\library\\CmdExec.dll");
		String[] info= {"","","",""};
		String cpuId=CmdExec.getCpuId();
		String diskId=CmdExec.getDiskId();
		info[0]=cpuId;
		info[1]=diskId;
		info[2]=softwareVersion;
		info[3]=funcationSwitch;
		return info;
	}
	
}
