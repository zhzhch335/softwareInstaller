package softwareInstaller;

import java.io.*;
import java.util.*;

public final class Main {
	
	// 软件版本默认值
	private static String softwareVersion = "1.2.364";
	
	// 功能开关默认值
	private static String funcationSwitch = "false";

	//创建注册文件
	public static void createKey(String path) throws IOException {
		String[] own_info = new String[5];
		own_info = ownKey();
		String accessKey = String.valueOf(own_info[4]);
		File.createKeyFile(path, accessKey);
		File.createKeyFile(path, own_info);
	};
	
	//检查注册文件
	public static boolean checkKey(String path) throws IOException {
		int loadKey = File.loadKeyFile(path);
		String[] own_info = ownKey();
		String own_str = own_info[0] + own_info[1] + own_info[2] + own_info[3];
		int own_key = own_str.hashCode();
		if (loadKey == own_key) {
			return true;
		} else {
			return false;
		}
	};

	//获取系统信息
	public static String[] ownKey() throws IOException {
		
		// 加载读取硬盘序列号的C++类库
		System.load(System.getProperty("user.dir") + "\\Main.dll");
		String[] info = { "", "", "", "", "" };/*用于存储系统信息*/
		String cpuId = getCpuId();
		String diskId = getDiskId();
		info[0] = cpuId;
		info[1] = diskId;
		info[2] = getSoftwareVersion();
		info[3] = getFuncationSwitch();
		int accessKey = dataEncode(info[0], info[1], info[2], info[3]);
		info[4] = String.valueOf(accessKey);
		return info;
	}

	// 从C++调用硬盘信息
	private static native String getDiskId();

	// 从命令提示符调用CPU信息
	private static String getCpuId() throws IOException {
		String cpuId;
		Process process = Runtime.getRuntime().exec("wmic cpu get processorId");
		Scanner sc = new Scanner(process.getInputStream());
		sc.next();
		cpuId = sc.next();
		sc.close();
		return cpuId;
	}

	//哈希值加密
	private static int dataEncode(String cpuId,String diskId,String softwareVersion,String functionSwitch){
		int key;
		String oriStr=cpuId+diskId+softwareVersion+functionSwitch;
		key=oriStr.hashCode();
		return key;
	}

	//查看版本号
	public static String getSoftwareVersion() {
		return softwareVersion;
	}

	//设置版本号		
	public static void setSoftwareVersion(String softwareVersion) {
		Main.softwareVersion = softwareVersion;
	}

	//获取功能开关状态
	public static String getFuncationSwitch() {
		return funcationSwitch;
	}

	//切换功能开关状态
	public static void setFuncationSwitch(String funcationSwitch) {
		Main.funcationSwitch = funcationSwitch;
	}
	
}
