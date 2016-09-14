package loongplugindependency.popup.actions;

import java.util.ArrayList;
import java.util.List;

import loongplugin.featuremodeleditor.FeatureModelEditor;
import loongplugin.source.database.ApplicationObserver;
import loongplugin.source.database.ApplicationObserverException;
import loongplugin.source.database.model.LElement;
import loongplugin.source.database.model.LFlyweightElementFactory;
import loongplugindependency.cfgmodel.CFGBuilder;
import loongplugindependency.cfgmodel.CFGModel;
import loongplugindependency.views.CFGViews.CFGView;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class ControlFlowAction implements IObjectActionDelegate{

	private final List<IResource> resources = new ArrayList<IResource>();
	protected IWorkbenchPart part;
	private IProject aproject;
	private ApplicationObserver lDB;
	private Shell shell;
	private IResource singleresource;
	private LFlyweightElementFactory aElementFactory;
	public ControlFlowAction() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		assert !resources.isEmpty();
		
		aproject = resources.get(0).getProject();
		for (IResource r : resources) {
			if (r.getProject() != aproject) {
				MessageBox messageBox = new MessageBox(Display.getCurrent()
						.getActiveShell(), SWT.OK);
				messageBox
						.setText("Unsupported selection. Select resources from a single project only.");
				messageBox.open();
				return;
			}
		}
		
		if (!resources.isEmpty()) {
			lDB = ApplicationObserver.getInstance();
			if(!lDB.isInitialized(aproject)){
				WorkspaceJob op = new WorkspaceJob("CreateDatabaseAction") {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						// TODO Auto-generated method stub
						try {
							// get instance and init the database
							ApplicationObserver lDB = ApplicationObserver.getInstance();
							lDB.initialize(aproject, monitor);

						} catch (ApplicationObserverException lException) {
							lException.printStackTrace();
						}
						
						return Status.OK_STATUS;
					}};
				op.setUser(true);
				op.schedule();	
				try {
					op.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// 获得单一的resource
			singleresource = resources.get(0);
			
			
			// 判断resource是否为 java 类型文件
			if(singleresource!=null){
				//System.out.println(singleresource.getFileExtension());
				if(singleresource.getType()==IResource.FILE
						&&singleresource.getFileExtension().equals("java")){
					IFile javafile = (IFile)singleresource;
					ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(javafile);
					ASTParser parser = ASTParser.newParser(AST.JLS3);
					parser.setSource(compilationUnit);
					CompilationUnit compilation = (CompilationUnit) parser.createAST(null);
					this.aElementFactory = this.lDB.getLFlyweightElementFactory();
					LElement compilationunitelement = aElementFactory.getElement(compilation);
					
					CFGBuilder builder = new CFGBuilder(compilationunitelement,this.aElementFactory);
					//builder.build();
					CFGModel model = new CFGModel();
					model.setListener(CFGView.getInstance().getCFGModelListener());
					model.setCFGModel(builder);
					
					
					
				}
			}
			
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
		shell = targetPart.getSite().getShell();
	}

}
