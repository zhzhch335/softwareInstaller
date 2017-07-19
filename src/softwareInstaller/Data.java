package softwareInstaller;

public class Data {

	private String cpuId;
	private String diskId;
	private String softwareVersion;
	private String functionSwitch;
	
	public static void main() {
		
	}
	
	public Data(String cpuId,String diskId,String softwareVersion,String functionSwitch){
		this.cpuId=cpuId;
		this.diskId=diskId;
		this.softwareVersion=softwareVersion;
		this.functionSwitch=functionSwitch;
		//dataEncode();
	}
	
	
	public int dataEncode(){
		int key;
		String oriStr=cpuId+diskId+softwareVersion+functionSwitch;
		key=oriStr.hashCode();
		return key;
	}

	public static String[] dataDecode(String key){
		String[] info = null;
		return info;
	}

}
