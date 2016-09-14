package loongplugindependency.cfgmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import loongplugin.color.coloredfile.ASTID;
import loongplugin.source.database.model.LElement;
import loongplugin.source.database.model.LFlyweightElementFactory;
import loongplugin.source.database.model.LICategories;
import loongplugindependency.util.ASTNodeWalker;
import loongplugindependency.views.CFGViews.CFGView;

public class CFGBuilder{
	private LElement root = null;
	private LFlyweightElementFactory aElementFactory = null;
	private ASTNode dominateASTNode = null;
	private Set<ASTNode> methodNodes;
	private Set<Statement> allstatements;
	private Set<CFGNode> inputs;
	private Map<Statement,CFGNode>statementToCFGNode;
	public CFGBuilder(LElement proot,LFlyweightElementFactory pElementFactory){
		this.root = proot;
		this.aElementFactory = pElementFactory;
		this.dominateASTNode = root.getASTNode();
		this.allstatements = new HashSet<Statement>();
		this.methodNodes = ASTNodeWalker.methodWalker(this.dominateASTNode);
		this.inputs =new HashSet<CFGNode>();
		this.statementToCFGNode = new HashMap<Statement,CFGNode>();
	}
	
	
	
	public void build(){
		
		for(ASTNode method:methodNodes){
			MethodDeclaration methoddec = (MethodDeclaration)method;
			ControlFlowGraph cfg = new ControlFlowGraph(methoddec);
			for(Statement statement:cfg.getStatement()){
				Set<ASTNode> statementASTNodes = ASTNodeWalker.allWalker(statement);
				Set<LElement> pallLElements = convertToLELements(statementASTNodes);
				CFGNode node = new CFGNode(statement,pallLElements);
				
				this.statementToCFGNode.put(statement, node);
				this.inputs.add(node);
			}
			this.allstatements.addAll(cfg.getStatement());
			
			
			// add order information
			Map<Statement, Set<Statement>> predecessors = cfg.getPredecessors();
			Map<Statement, Set<Statement>> sucessors = cfg.getSuccessors();
			
			// iterate the predecessors;
			for(Map.Entry<Statement, Set<Statement>>entry:predecessors.entrySet()){
				Statement statement = entry.getKey();
				Set<Statement> predecessorstatements = entry.getValue();
				CFGNode cfgstate = statementToCFGNode.get(statement);
				for(Statement prestate:predecessorstatements){
					CFGNode cfgprestate = statementToCFGNode.get(prestate);
					cfgstate.addPredecessor(cfgprestate);
				}
			}
			
			// iterate the sucessors
			for(Map.Entry<Statement, Set<Statement>>entry:sucessors.entrySet()){
				Statement statement = entry.getKey();
				Set<Statement> successstates = entry.getValue();
				CFGNode cfgstate = statementToCFGNode.get(statement);
				for(Statement succstate:successstates){
					CFGNode cfgsucc = statementToCFGNode.get(succstate);
					cfgstate.addSuccessor(cfgsucc);
				}
			}
			
		}
		
		
		
	}
	
	
	
	private Set<LElement> convertToLELements(Set<ASTNode> statementASTNodes) {
		// TODO Auto-generated method stub
		Set<LElement> converted = new HashSet<LElement>();
		for(ASTNode node:statementASTNodes){
			LElement element = null;
			if(node instanceof TypeDeclaration){
				TypeDeclaration typedecl = (TypeDeclaration)node;
				IBinding binding = typedecl.resolveBinding();
				element = this.aElementFactory.getElement(binding);
			}else if(node instanceof EnumDeclaration){
				EnumDeclaration enumdecl = (EnumDeclaration)node;
				IBinding binding = enumdecl.resolveBinding();
				element = this.aElementFactory.getElement(binding);
			}else if(node instanceof  MethodDeclaration){
				MethodDeclaration  methoddecl = (MethodDeclaration)node;
				IBinding binding = methoddecl.resolveBinding();
				element = this.aElementFactory.getElement(binding);
			}else if(node instanceof VariableDeclarationFragment){
				VariableDeclarationFragment varfrag= (VariableDeclarationFragment)node;
				IBinding binding = varfrag.resolveBinding();
				element = this.aElementFactory.getElement(binding);
			}else if(node instanceof SingleVariableDeclaration){
				SingleVariableDeclaration singlenode = (SingleVariableDeclaration)node;
				IBinding binding = singlenode.resolveBinding();
				element = this.aElementFactory.getElement(binding);
				
			}else if(node instanceof EnumConstantDeclaration){
				EnumConstantDeclaration enumconst = (EnumConstantDeclaration)node;
				IBinding binding = enumconst.resolveConstructorBinding();
				element = this.aElementFactory.getElement(binding);
			}
			else{
				element = this.aElementFactory.getElement(node);
			}
			if(element!=null){
				converted.add(element);
			}
		}
		return converted;
	}



	public CFGNode[] getNodes(){
		List<CFGNode>list = new ArrayList<CFGNode>();
		list.addAll(inputs);
		CFGNode[] array = new CFGNode[list.size()];
		list.toArray(array);
		return array;
	}
	
}
