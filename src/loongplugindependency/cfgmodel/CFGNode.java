package loongplugindependency.cfgmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Statement;

import loongplugin.color.coloredfile.CLRAnnotatedSourceFile;
import loongplugin.source.database.model.LElement;
import loongplugin.source.database.model.LICategories;

public class CFGNode implements Serializable{

	//////////////////////Fields///////////////////////////////
	// Predecessors statements
	private ArrayList<CFGNode> predecessorCFGNodes;
	
	// Successor statements
	private ArrayList<CFGNode> successorCFGNodes;
	
	private Statement statement;
	
	
	
	/////////////////////Constructors////////////////////////
	
	public CFGNode(Statement pstatement){
		this.statement = pstatement;
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



	public String getContent() {
		// TODO Auto-generated method stub
		return this.statement.toString();
	}
	
	
	
	
}
