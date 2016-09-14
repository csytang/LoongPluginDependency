package loongplugindependency.views.CFGViews;

import java.util.EventObject;

import loongplugindependency.cfgmodel.CFGModel;

public class CFGModelChangedEvent extends EventObject {
	
	private CFGModel model;
	public CFGModelChangedEvent(Object source, CFGModel pmodel) {
		super(source);
		// TODO Auto-generated constructor stub
		this.model = pmodel;
	}
	public CFGModel getModel(){
		return model;
	}

}
