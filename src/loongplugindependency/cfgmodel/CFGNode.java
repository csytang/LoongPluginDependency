package loongplugindependency.cfgmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
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
	
	private ASTNode astnode;
	
	private String displayName;
	
	/////////////////////Constructors////////////////////////
	
	public CFGNode(ASTNode pnode){
		this.astnode = pnode;
		predecessorCFGNodes = new ArrayList<CFGNode>();
		successorCFGNodes = new ArrayList<CFGNode>();
		this.displayName = pnode.toString();
	}
	public CFGNode(){
		
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
		return this.astnode.toString();
	}

	public ASTNode getASTNode(){
		return this.astnode;
	}
	
	public boolean isSpecial(){
		if(this instanceof CFGNodeSpecial){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof CFGNode){
			CFGNode cfgnode = (CFGNode) obj;
			if(cfgnode instanceof CFGNodeSpecial){
				CFGNodeSpecial specialcfgnode = (CFGNodeSpecial)cfgnode;
				if(this.isSpecial()){
					return specialcfgnode.getContent().equals(this.getContent());
				}else{
					return false;
				}
			}
			else if(cfgnode.getASTNode().equals(this.astnode)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	
	
	
	
}
