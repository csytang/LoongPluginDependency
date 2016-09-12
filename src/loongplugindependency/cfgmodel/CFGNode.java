package loongplugindependency.cfgmodel;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jdt.core.dom.ASTNode;

import loongplugin.color.coloredfile.CLRAnnotatedSourceFile;
import loongplugin.source.database.model.LElement;
import loongplugin.source.database.model.LICategories;

public class CFGNode extends LElement{

	//////////////////////Fields///////////////////////////////
	// Predecessors statements
	private ArrayList<CFGNode> predecessorCFGNodes;
	
	// Successor statements
	private ArrayList<CFGNode> successorCFGNodes;
	
	
	/////////////////////Constructors////////////////////////
	public CFGNode(String pId, LICategories pcategory,
			CLRAnnotatedSourceFile pColorSourceFile, ASTNode pastNode) {
		super(pId, pcategory, pColorSourceFile, pastNode);
		predecessorCFGNodes = new ArrayList<CFGNode>();
		successorCFGNodes = new ArrayList<CFGNode>();
	}
	public CFGNode(LElement element){
		super(element.getId(),element.getCategory(),element.getCLRFile(),element.getASTNode());
		predecessorCFGNodes = new ArrayList<CFGNode>();
		successorCFGNodes = new ArrayList<CFGNode>();
	}
	///////////////////////////////////////////////////////
	
	

	///////////////////Functions//////////////////////////
	public void addPredecessor(CFGNode prenode){
		if(!this.predecessorCFGNodes.contains(prenode))
			this.predecessorCFGNodes.add(prenode);
	}
	
	public void addPredecessor(Collection<CFGNode>precessors){
		this.predecessorCFGNodes.addAll(precessors);
	}
	
	public void addSuccessor(CFGNode successor){
		if(!this.successorCFGNodes.contains(successor))
			this.successorCFGNodes.add(successor);
	}
	
	public void addSuccessor(Collection<CFGNode>successors){
		this.successorCFGNodes.addAll(successors);
	}
	
	
	public ArrayList<CFGNode> getSucessors(){
		return this.successorCFGNodes;
	}
	
	
}
