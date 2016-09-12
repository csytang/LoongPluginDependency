package loongplugindependency.cfgmodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

import loongplugin.source.database.model.LElement;
import loongplugin.source.database.model.LFlyweightElementFactory;
import loongplugin.source.database.model.LICategories;
import loongplugindependency.util.ASTNodeWalker;

public class CFGBuilder {
	private LElement root = null;
	private LFlyweightElementFactory aElementFactory = null;
	private ASTNode dominateASTNode = null;
	private Set<LElement> allelements = new HashSet<LElement>();
	private Set<LElement> allelementmethods = new HashSet<LElement>();
	private Map<LElement,ControlFlowGraph> methodToCFG = new HashMap<LElement,ControlFlowGraph>();
	private Map<LElement,CFGNode> lelementToCFGNode = new HashMap<LElement,CFGNode>();
	public CFGBuilder(LElement proot,LFlyweightElementFactory pElementFactory){
		this.root = proot;
		this.aElementFactory = pElementFactory;
		this.dominateASTNode = root.getASTNode();
		Set<ASTNode> allnodes = ASTNodeWalker.allWalker(dominateASTNode);
		for(ASTNode node:allnodes){
			LElement subelement = aElementFactory.getElement(node);
			CFGNode subelementCFGNode = new CFGNode(subelement);
			
			lelementToCFGNode.put(subelement, subelementCFGNode);
			allelements.add(subelement);
			if(subelement.getCategory().equals(LICategories.METHOD))
				allelementmethods.add(subelement);
		}
	}
	public void build(){
		for(LElement method:allelementmethods){
			ASTNode methodastnode = method.getASTNode();
			assert methodastnode instanceof MethodDeclaration;
			// Create 
			
			ControlFlowGraph cfg = new ControlFlowGraph((MethodDeclaration)methodastnode);
			
			// Process the knowledge in the control flow graph
			if(cfg.isEmpty())
				continue;
			
			
			methodToCFG.put(method, cfg);
			
			
			Map<Statement, Set<Statement>> predecessors = cfg.getPredecessors();
			Map<Statement, Set<Statement>> successors = cfg.getSuccessors();
			 
			 // Iterate predecessor set
			 for(Map.Entry<Statement, Set<Statement>>entry:predecessors.entrySet()){
				 Statement current = entry.getKey();
				 Set<Statement> curr_predecessor = entry.getValue();
				 LElement curr_element = this.aElementFactory.getElement(current);
				 CFGNode curr_cfgNode = this.lelementToCFGNode.get(curr_element);
				 Set<CFGNode> curr_predecessor_cfgNode = batchstatement_CFGNodeConvert(curr_predecessor);
				 if(!curr_predecessor_cfgNode.isEmpty())
					 curr_cfgNode.addPredecessor(curr_predecessor_cfgNode);
			 }
			 
			 // Iterate successor set
			 for(Map.Entry<Statement, Set<Statement>>entry:successors.entrySet()){
				 Statement current = entry.getKey();
				 Set<Statement> curr_successor = entry.getValue();
				 LElement curr_element = this.aElementFactory.getElement(current);
				 CFGNode curr_cfgNode = this.lelementToCFGNode.get(curr_element);
				 Set<CFGNode> curr_successor_cfgNode = batchstatement_CFGNodeConvert(curr_successor);
				 if(!curr_successor_cfgNode.isEmpty())
					 curr_cfgNode.addSuccessor(curr_successor_cfgNode);
			 }
			
		}
	}
	
	public Set<CFGNode> batchstatement_CFGNodeConvert(Collection<Statement> astnodecollection){
		Set<CFGNode> converted = new HashSet<CFGNode>();
		for(Statement statement:astnodecollection){
			LElement element = this.aElementFactory.getElement(statement);
			CFGNode cfgNode = this.lelementToCFGNode.get(element);
			converted.add(cfgNode);
		}
		
		return converted;
	}
	
}
