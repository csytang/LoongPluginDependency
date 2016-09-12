package loongplugindependency.views.CFGViews;

import loongplugindependency.cfgmodel.CFGNode;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

public class CFGContentProvider extends ArrayContentProvider  implements IGraphEntityContentProvider {
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getConnectedTo(Object entity) {
		// TODO Auto-generated method stub
		if(entity instanceof CFGNode){
			CFGNode node = (CFGNode)entity;
			return node.getSucessors().toArray();
		}
	    throw new RuntimeException("Type not supported");

	}

}
