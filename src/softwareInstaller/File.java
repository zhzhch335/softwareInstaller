package softwareInstaller;

import java.io.*;
import java.util.*;

public class File {

	//创建注册码文件
	public static void createKeyFile(String url, String key) throws IOException {
		OutputStream file = new FileOutputStream(url);
		OutputStreamWriter wr = new OutputStreamWriter(file, "UTF-8");
		wr.append(key);
		wr.close();
		file.close();
	}
	
	//创建系统信息文件
	public static void creatkeyFile(String url,String[] info) throws IOException{		
		url=url.substring(0, url.length()-4)+"系统信息.txt";
		OutputStream file=new FileOutputStream(url);
		OutputStreamWriter wr=new OutputStreamWriter(file,"UTF-8");
		wr.append("CPUID:"+info[0]+"\n");
		wr.append("DiskID:"+info[1]+"\n");
		wr.append("软件版本:"+info[2]+"\n");
		wr.append("功能开关:"+info[3]+"\n");
		wr.close();
		file.close();
	}

	//加载注册码文件
	public static int loadKeyFile(String url) throws FileNotFoundException {
		InputStream file = new FileInputStream(url);
		Scanner sc = new Scanner(file, "UTF-8");
		int key = Integer.valueOf(sc.next());
		sc.close();
		return key;
	}
}