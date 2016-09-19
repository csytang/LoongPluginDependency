package loongplugindependency.cfgmodel;

import org.eclipse.jdt.core.dom.ASTNode;

public class CFGNodeSpecial extends CFGNode {

	
	public String value;
	public CFGNodeSpecial(String startend){
		if(startend.equals("ENTRY")){
			this.value = "ENTRY";
		}else if(startend.equals("EXIT")){
			this.value = "EXIT";
		}else{
			try {
				throw new Exception("Unexcepted Node");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return value;
	}
	@Override
	public boolean isSpecial() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
