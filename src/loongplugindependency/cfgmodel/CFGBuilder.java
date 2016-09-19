package loongplugindependency.cfgmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import loongplugin.source.database.model.LElement;
import loongplugin.source.database.model.LFlyweightElementFactory;
import loongplugindependency.util.ASTNodeWalker;


public class CFGBuilder{
	private LElement root = null;
	private LFlyweightElementFactory aElementFactory = null;
	private ASTNode dominateASTNode = null;
	private Set<ASTNode> methodNodes;
	private Set<CFGNode> inputs;
	
	public CFGBuilder(LElement proot,LFlyweightElementFactory pElementFactory){
		this.root = proot;
		this.aElementFactory = pElementFactory;
		this.dominateASTNode = root.getASTNode();
		this.methodNodes = ASTNodeWalker.methodWalker(this.dominateASTNode);
		this.inputs =new HashSet<CFGNode>();
		
	}
	
	
	
	public void build(){
		
		for(ASTNode method:methodNodes){
			MethodDeclaration methoddec = (MethodDeclaration)method;
			ControlFlowGraph cfg = new ControlFlowGraph(methoddec);
			Map<Statement, Set<Statement>> statement_predecessors = cfg.predecessors;
			Map<Statement, Set<Statement>> statement_successors  = cfg.successors;
			
		}
		
		
	}
	
	
	
	



	public CFGNode[] getNodes(){
		List<CFGNode>list = new ArrayList<CFGNode>();
		list.addAll(inputs);
		CFGNode[] array = new CFGNode[list.size()];
		list.toArray(array);
		return array;
	}
	
}
