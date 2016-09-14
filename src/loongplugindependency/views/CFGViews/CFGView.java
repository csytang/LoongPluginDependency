package loongplugindependency.views.CFGViews;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.swt.SWT;


import loongplugindependency.cfgmodel.CFGModel;

import org.eclipse.zest.core.*;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.*;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

public class CFGView extends ViewPart implements IZoomableWorkbenchPart {
 

	private GraphViewer graphViewer;
	public static CFGView instance;
	public static final String ID = "loongplugindependency.views.ControlView";
	private CFGModel cfgmodel = new CFGModel();
	private Composite aparent;
	
	private CFGModelChangeListener listener = new CFGModelChangeListener();
	public static CFGView getInstance(){
		if(instance==null){
			instance = new CFGView();
		}
		return instance;
	}
	
	public CFGView() {
		// TODO Auto-generated constructor stub
		instance = this;
	}

	@Override
	public void createPartControl(Composite parent) {
		aparent = parent;
		
		graphViewer = new GraphViewer(parent, SWT.BORDER);
		
		graphViewer.setLabelProvider(new CFGNodeLabelProvider());
		graphViewer.setContentProvider(new CFGContentProvider());
		// 查看Editor 如果 editor 没有 则查看选择按钮就
		LayoutAlgorithm layout = setLayout();
		graphViewer.setLayoutAlgorithm(layout, true);
		graphViewer.applyLayout();
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);

		graphViewer.setInput(cfgmodel);
	    fillToolBar();
	}
	
	
	private LayoutAlgorithm setLayout() {
	    LayoutAlgorithm layout;
	    // layout = new
	    // SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	    layout = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	    // layout = new
	    // GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	    // layout = new
	    // HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	    // layout = new
	    // RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	    return layout;

	  }

	private void fillToolBar(){
		ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(this);
	    IActionBars bars = getViewSite().getActionBars();
	    bars.getMenuManager().add(toolbarZoomContributionViewItem);
	}
	
	/*
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		graphViewer.getControl().setFocus();

	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		// TODO Auto-generated method stub
		return graphViewer;
	}
	
	
	public void redraw(CFGModel cfgmodel){
		if(graphViewer==null){
			graphViewer = new GraphViewer(aparent, SWT.BORDER);
			graphViewer.setLabelProvider(new CFGNodeLabelProvider());
			graphViewer.setContentProvider(new CFGContentProvider());
			// 查看Editor 如果 editor 没有 则查看选择按钮就
			LayoutAlgorithm layout = setLayout();
			graphViewer.setLayoutAlgorithm(layout, true);
			graphViewer.applyLayout();
			graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
			graphViewer.setInput(cfgmodel);
		    fillToolBar();
		   
		}else{
			graphViewer.setInput(cfgmodel);
			
		}
		graphViewer.refresh();
	}
	
	class CFGModelChangeListener implements ICFGModelChangeListener{

		@Override
		public void featureModelChanged(CFGModelChangedEvent event) {
			// TODO Auto-generated method stub
			cfgmodel = event.getModel();
			redraw(cfgmodel);
					
		}
		
	}

	public ICFGModelChangeListener getCFGModelListener() {
		// TODO Auto-generated method stub
		return listener;
	}

}
