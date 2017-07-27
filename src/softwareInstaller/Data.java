package softwareInstaller;

public class Data {

	
	public static int dataEncode(String cpuId,String diskId,String softwareVersion,String functionSwitch){
		int key;
		String oriStr=cpuId+diskId+softwareVersion+functionSwitch;
		for(int i=0;i<oriStr.length()-1;++i) {
			oriStr.getChars(i, i+1, String.valueOf(Math.random()).toCharArray(), 0);
		}
		key=oriStr.hashCode();
		return key;
	}


}
