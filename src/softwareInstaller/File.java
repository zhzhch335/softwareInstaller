package softwareInstaller;

import java.io.*;

public class File {

	//创建注册码文件，返回值为空，参数为字符串路径和字符串序列号
	public static void createKeyFile(String url, String key) throws IOException {
		Writer wr = new FileWriter(url);
		wr.append(key);
		wr.close();
	}
	
	//创建系统信息文件，返回值为空，参数为字符串路径，字符数组系统信息
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
	public static String loadKeyFile(String url) throws IOException {
		Reader isr=new FileReader(url);
		BufferedReader br=new BufferedReader(isr);
		String key = br.readLine();
		br.close();
		return key;
	}
}