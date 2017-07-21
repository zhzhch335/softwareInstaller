package softwareInstaller;

import java.io.*;
import java.util.*;

public class File {
	/*
	 * 
	 * 
	 调试用
	public static void main(String[] args) throws IOException {
		createKeyFile("AccessKey.key",-878496320);
		System.out.print(loadKeyFile("AccessKey.key"));
	}*/
	 
	public static void createKeyFile(String url,int key) throws IOException {
		OutputStream file=new FileOutputStream(url);
		OutputStreamWriter wr=new OutputStreamWriter(file,"UTF-8");
		wr.append(String.valueOf(key));
		wr.close();
		System.out.println("密钥写入成功！");
		file.close();
	}
	
	public static int loadKeyFile(String url) throws FileNotFoundException {
		InputStream file=new FileInputStream(url);
		Scanner sc=new Scanner(file,"UTF-8");
		//System.out.println(Integer.valueOf(sc.next()));
		int key=Integer.valueOf(sc.next());
		sc.close();
		return key;
	}
}