package loongplugindependency.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ControlFlowAction implements IObjectActionDelegate{

	private final List<IResource> resources = new ArrayList<IResource>();
	protected IWorkbenchPart part;

	
	public ControlFlowAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		assert !resources.isEmpty();
		
		IProject project = resources.get(0).getProject();
		for (IResource r : resources) {
			if (r.getProject() != project) {
				MessageBox messageBox = new MessageBox(Display.getCurrent()
						.getActiveShell(), SWT.OK);
				messageBox
						.setText("Unsupported selection. Select resources from a single project only.");
				messageBox.open();
				return;
			}
		}
		
		if (!resources.isEmpty()) {
			
			
		}
		
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		resources.clear();
		if (selection instanceof IStructuredSelection) {
			for (Object selected : ((IStructuredSelection) selection).toArray()) {
				if (selected instanceof IResource) {
					resources.add((IResource) selected);
				}

			}
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		this.part = targetPart;
	}

}
