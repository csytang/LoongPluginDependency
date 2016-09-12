package loongplugindependency.views.CFGViews;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.swt.SWT;

import java.awt.Frame;

import org.eclipse.swt.awt.SWT_AWT;

import java.awt.Panel;
import java.awt.BorderLayout;

import javax.swing.JRootPane;
import org.eclipse.zest.core.*;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.layouts.*;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

public class CFGView extends ViewPart implements IZoomableWorkbenchPart {
 

	private GraphViewer graphViewer;
	
	public CFGView() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void createPartControl(Composite parent) {
		graphViewer = new GraphViewer(parent, SWT.BORDER);
		graphViewer.setLabelProvider(new CFGNodeLabelProvider());
		graphViewer.setContentProvider(new CFGContentProvider());
		CFGNodeModuleContentProvider model = new CFGNodeModuleContentProvider();
		graphViewer.setInput(model.getNodes());
		LayoutAlgorithm layout = setLayout();
		graphViewer.setLayoutAlgorithm(layout, true);
		graphViewer.applyLayout();
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
	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		// TODO Auto-generated method stub
		return graphViewer;
	}

	

}
