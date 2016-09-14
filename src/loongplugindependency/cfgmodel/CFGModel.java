package loongplugindependency.cfgmodel;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import loongplugindependency.views.CFGViews.CFGModelChangedEvent;
import loongplugindependency.views.CFGViews.CFGView;
import loongplugindependency.views.CFGViews.ICFGModelChangeListener;

public class CFGModel {
	private CFGBuilder builder;
	private ICFGModelChangeListener listener;
	public CFGModel(){
		
	}
	public void setListener(ICFGModelChangeListener plistener){
		this.listener = plistener;
	}
	public void setCFGModel(CFGBuilder pbuilder){
		this.builder = pbuilder;
		this.builder.build();
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(CFGView.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notifyFeatureModelListener();
	}
	
	public void notifyFeatureModelListener(){
		CFGModelChangedEvent event = new CFGModelChangedEvent(this, this);
		listener.featureModelChanged(event);
		
	}
	public Object[] getInput() {
		// TODO Auto-generated method stub
		if(this.builder==null)
			return null;
		else
			return this.builder.getNodes();
	}
	
	
	
	
}
