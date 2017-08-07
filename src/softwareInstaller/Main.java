package softwareInstaller;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @describe 主控类，用于调用加密函数以及与数据类通信
 * @author Zhao Zhichen
 * @time 2017.08.07 下午2:26:52
 * @version softwareInstaller.17.08.07
 * @see	
 */
public final class Main {

	// 软件版本默认值
	private static String softwareVersion = "1.2.364";

	// 功能开关默认值
	private static String funcationSwitch = "false";

	/*
	 * 文件读写相关
	 * 
	 */

	/**   
	 * @Title: createKey   
	 * @Description:  调用File类的方法创建密钥文件
	 * @param path 密钥文件
	 * @throws IOException 写入异常
	 * @throws NoSuchAlgorithmException 未找到加密字典异常      
	 * @return: void      
	 */  
	public static void createKey(String path) throws IOException, NoSuchAlgorithmException {
		String[] own_info = new String[5];
		own_info = ownKey();
		String accessKey = String.valueOf(own_info[4]);
		File.createKeyFile(path, accessKey);
		File.createKeyFile(path, own_info);
	};

	// 检查注册文件，返回值boolean，参数String为文件路径
	/**   
	 * @Title: checkKey   
	 * @Description:  核对密钥是否一致
	 * @param path 密钥文件路径
	 * @throws IOException 读取异常
	 * @throws NoSuchAlgorithmException 未找到加密字典异常      
	 * @return: boolean      
	 */  
	public static boolean checkKey(String path) throws IOException, NoSuchAlgorithmException {
		String loadKey = File.loadKeyFile(path);
		String[] own_info = ownKey();
		if (loadKey.contentEquals(own_info[4])) {
			return true;
		} else {
			return false;
		}
	};

	/*
	 * 文件读写相关 end
	 * 
	 */

	/*
	 * 信息获取相关
	 * 
	 */

	/**   
	 * @Title: ownKey   
	 * @Description:  获取系统信息并调用encode()方法获取注册码
	 * @throws IOException 读取异常
	 * @throws NoSuchAlgorithmException 未找到加密字典异常      
	 * @return: String[]      系统信息+注册码数组
	 */  
	public static String[] ownKey() throws IOException, NoSuchAlgorithmException {
		String[] info = { "", "", "", "", "" };/* 用于存储系统信息 */
		String cpuId = getCpuId();
		String diskId = getDiskId();
		info[0] = cpuId;
		info[1] = diskId;
		info[2] = getSoftwareVersion();
		info[3] = getFuncationSwitch();
		String accessKey = dataEncode(info[0], info[1], info[2], info[3]);
		info[4] = accessKey;
		return info;
	}

	/**   
	 * @Title: getDiskId   
	 * @Description: 获取硬盘序列号  
	 * @throws IOException     读取异常 
	 * @return: String      
	 */  
	private static String getDiskId() throws IOException {
		String result = "";/* 用于保存结果 */
		String appent = "";/* 用于阅读每一行 */
		Process rt = Runtime.getRuntime().exec("wmic diskdrive get SerialNumber");
		InputStreamReader rd = new InputStreamReader(rt.getInputStream());
		BufferedReader br = new BufferedReader(rd);
		br.readLine();
		while ((appent = br.readLine()) != null) {
			result = result + appent;
		}
		return result;
	};

	/**   
	 * @Title: getCpuId   
	 * @Description:获取CPUID  
	 * @return
	 * @throws IOException  读取异常    
	 * @return: String      
	 */  
	private static String getCpuId() throws IOException {
		String cpuId;
		Process rt = Runtime.getRuntime().exec("wmic cpu get processorId");
		InputStreamReader rd=new InputStreamReader(rt.getInputStream());
		BufferedReader br =new BufferedReader(rd);
		br.readLine();
		br.readLine();
		cpuId=br.readLine();
		return cpuId;
	}

	/**   
	 * @Title: getSoftwareVersion   
	 * @Description:查看版本号  
	 * @return: String      
	 */  
	public static String getSoftwareVersion() {
		return softwareVersion;
	}

	/**   
	 * @Title: getFuncationSwitch   
	 * @Description:  查看功能开关状态
	 * @return: String      
	 */  
	public static String getFuncationSwitch() {
		return funcationSwitch;
	}

	/*
	 * 信息获取相关end
	 * 
	 */
	
	/**   
	 * @Title: dataEncode   
	 * @Description:  SHA加密，使用哈希算法获取序列号
	 * @param cpuId
	 * @param diskId
	 * @param softwareVersion
	 * @param functionSwitch
	 * @throws NoSuchAlgorithmException 未找到字典异常      
	 * @return: String      返回值为32长度的字符串
	 */  
	private static String dataEncode(String cpuId, String diskId, String softwareVersion, String functionSwitch)
			throws NoSuchAlgorithmException {
		String key = "";
		String oriStr = cpuId + diskId + softwareVersion + functionSwitch;
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] bkey = md.digest(oriStr.getBytes());
		for (byte i : bkey) {
			key = key + Integer.toHexString((i & 0x000000FF)|0xFFFFFF00).substring(6);
		}
		return key.toUpperCase();
	}

	/**   
	 * @Title: setSoftwareVersion   
	 * @Description:  设置版本号
	 * @param softwareVersion      
	 * @return: void      
	 */  
	public static void setSoftwareVersion(String softwareVersion) {
		Main.softwareVersion = softwareVersion;
	}

	/**   
	 * @Title: setFuncationSwitch   
	 * @Description:切换功能开关状态  
	 * @param funcationSwitch      参数为字符串“true”或是字符串“false”，分别代表功能开和功能关
	 * @return: void      
	 */  
	public static void setFuncationSwitch(String funcationSwitch) {
		Main.funcationSwitch = funcationSwitch;
	}

}
