package loongplugindependency.views.CFGViews;

import loongplugindependency.cfgmodel.CFGNode;

import org.eclipse.jface.viewers.LabelProvider;

public class CFGNodeLabelProvider extends LabelProvider{

	@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		if(element instanceof CFGNode){
			CFGNode node = (CFGNode) element;
			return node.getASTID();
		}
		throw new RuntimeException("Wrong type: "
			        + element.getClass().toString());
	}
	
}
