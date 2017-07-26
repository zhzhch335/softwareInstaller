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
		for(int i=0;i<oriStr.length()-1;++i) {
			oriStr.getChars(i, i+1, String.valueOf(Math.random()).toCharArray(), 0);
		}
		key=oriStr.hashCode();
		return key;
	}


}
