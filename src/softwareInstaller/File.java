package softwareInstaller;

import java.io.*;


/**
 * @describe 数据类，用于序列号的读写操作 
 * @author Zhao Zhichen
 * @time 2017.08.07 下午2:26:25
 * @version softwareInstaller.17.08.07
 * @see	
 */
public class File {


	/**   
	 * @Title: createKeyFile   
	 * @Description: 创建密钥文件   
	 * @param url 密钥文件地址
	 * @param key 密钥
	 * @throws IOException 写入异常      
	 * @return: void      
	 */  
	public static void createKeyFile(String url, String key) throws IOException {
		Writer wr = new FileWriter(url);
		wr.append(key);
		wr.close();
	}
	
	/**   
	 * @Title: createKeyFile   
	 * @Description:  创建系统信息文件
	 * @param url 系统信息文件地址
	 * @param info 系统信息字符串数组
	 * @throws IOException     写入异常 
	 * @return: void      
	 */  
	public static void createKeyFile(String url,String[] info) throws IOException{		
		url=url.substring(0, url.length()-4)+"系统信息.txt";
		Writer wr=new FileWriter(url);
		wr.append("CPUID:"+info[0]+"\n");
		wr.append("DiskID:"+info[1].replaceAll(" +", ",")+"\n");
		wr.append("软件版本:"+info[2]+"\n");
		wr.append("功能开关:"+info[3]+"\n");
		wr.close();
	}

	//加载注册码文件，返回值为字符串序列号，参数为字符串路径
	/**   
	 * @Title: loadKeyFile   
	 * @Description:  加载注册码文件
	 * @param url 密钥文件地址
	 * @throws IOException   读取异常   
	 * @return: String      
	 */   
	public static String loadKeyFile(String url) throws IOException {
		Reader isr=new FileReader(url);
		BufferedReader br=new BufferedReader(isr);
		String key = br.readLine();
		br.close();
		return key;
	}
}