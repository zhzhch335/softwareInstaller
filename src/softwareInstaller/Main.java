package softwareInstaller;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public final class Main {

	// 软件版本默认值
	private static String softwareVersion = "1.2.364";

	// 功能开关默认值
	private static String funcationSwitch = "false";

	/*
	 * 文件读写相关
	 * 
	 */

	// 创建注册文件,返回值void，参数String为文件路径
	public static void createKey(String path) throws IOException, NoSuchAlgorithmException {
		String[] own_info = new String[5];
		own_info = ownKey();
		String accessKey = String.valueOf(own_info[4]);
		File.createKeyFile(path, accessKey);
		File.createKeyFile(path, own_info);
	};

	// 检查注册文件，返回值boolean，参数String为文件路径
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

	// 整体获取系统信息，返回值为字符串数组，为别代表CPU信息，硬盘信息，版本信息和功能信息，无参数
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

	// 使用wmic工具调用硬盘序列号，返回值为字符串，无参数
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

	// 从命令提示符调用CPU信息，返回值为字符串，无参数
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

	// 查看版本号，直接返回成员变量，返回值为字符串，无参数
	public static String getSoftwareVersion() {
		return softwareVersion;
	}

	// 获取功能开关状态，直接返回成员变量，返回值为字符串，无参数
	public static String getFuncationSwitch() {
		return funcationSwitch;
	}

	/*
	 * 信息获取相关end
	 * 
	 */
	
	// SHA加密，使用哈希算法获取序列号，返回值为32长度的字符串，参数为四个字符串
	private static String dataEncode(String cpuId, String diskId, String softwareVersion, String functionSwitch)
			throws NoSuchAlgorithmException {
		String key = "";
		String oriStr = cpuId + diskId + softwareVersion + functionSwitch;
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] bkey = md.digest(oriStr.getBytes());
		for (byte i : bkey) {
			key = key + Integer.toHexString(i & 0x000000FF);
		}
		return key.toUpperCase();
	}

	// 设置版本号，返回值为空，参数为版本号字符串
	public static void setSoftwareVersion(String softwareVersion) {
		Main.softwareVersion = softwareVersion;
	}

	// 切换功能开关状态，返回值为空，参数为字符串“true”或是字符串“false”，分别代表功能开和功能关
	public static void setFuncationSwitch(String funcationSwitch) {
		Main.funcationSwitch = funcationSwitch;
	}

}
