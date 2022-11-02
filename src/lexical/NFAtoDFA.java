package lexical_analysis;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class NFAtoDFA {
	TokenEnum tokenenum=new TokenEnum();
	int nowId;
	int startId;
	NFA nfa;
	DFA dfa=new DFA();



	public  NFAtoDFA(NFA nfa) {
		// 当前状态机所在的位置
		this.nowId = 0;
		// 开始节点
		this.startId = 0;
		this. nfa =nfa ;//初始化使用一个NFA，然后用确定化和最小化算法
		// 确定化将NFA转化为DFA，将nodes和edges填上
	   // this.determine(nfa);
	}
	
	
	
	//e-closure计算     通过nfa计算node_set的闭包，并返回新的node_set
    public  Set<Node> epsilon_closure(Set<Node> node_set, NFA nfa) {// 查找node_set经过任意条epsilon弧能抵达的节点们
    	//加入nfa定义的边
    	List<Edge> nfaedges = new ArrayList<>();
    	for (Edge edge:nfa.edges) {
    		nfaedges.add(edge);
    	}
    	
    	//System.out.println(nfaedges.get(7).fromNodeId);
    	// 过滤掉重复的，所来了，以先建立空的集合set，然后把node_set的id加进去
    	Set node_id_set =new HashSet();
    	// 获得所有的node的id，写入node_id_set
    	for (Node node:node_set) {node_id_set.add(node.id);}

    	for (Node node:node_set){
			int node_id = node.id;//把每个id赋值给node_id,node_id是一次性的，每次遍历一个节点就刷新
    		for (Edge edge:nfaedges){
    			if (edge.tag == "epsilon" && edge.fromNodeId == node_id) {//遍历nfa的边，如果tag是epsilon且边的起始点和该节点的id一样
    				// 如果新的node不在node_set中则加入，否则跳过
    				// 遍历所有的可以抵达的node
    				/*
    				for ( Integer toNodeId : edge.toNodeIds) {
						node_set.add(nfa.nodes.get(toNodeId));
    				    }
    				    */
    				int toNodeId=edge.toNodeIds;
    				node_set.add(nfa.nodes.get(toNodeId));
    			}
    		}
    	}
    	return node_set;
    }
				
    	
  //move函数计算      通过nfa计算原来的节点集合node_set匹配tag移动后产生的新的节点集合
     public  Set<Node> move(Set<Node> node_set, NFA nfa,String tag) {
    	     //加入nfa定义的边
    	 List<Edge> nfaedges = new ArrayList<>();
     	for (Edge edge:nfa.edges) {
     		nfaedges.add(edge);
     	}
	    	//返回新的node集合new_node_set;
	    	Set new_node_set = new HashSet();
	    	// 过滤掉重复的，所来了，以先建立空的集合set，然后把node_set的id加进去
	    	Set node_id_set = new HashSet();
	    	// 获得所有的node的id，写入node_id_set
	    	for (Node node:node_set) {node_id_set.add(node.id);}

	    	for (Node node:node_set){
				int node_id = node.id;//把每个id赋值给node_id,node_id是一次性的，每次遍历一个节点就刷新
	    		for (Edge edge:nfaedges){
	    			if (edge.tag == tag && edge.fromNodeId == node_id) {//遍历nfa的边，如果tag是匹配且边的起始点和该节点的id一样
	    				// 如果新的node不在node_set中则加入，否则跳过
	    				// 遍历所有的可以抵达的node
	    				/*
	    				for ( Integer toNodeId : edge.toNodeIds) {
	    					new_node_set.add(nfa.nodes.get(toNodeId));
	    				    }
	    				    */
	    				int toNodeId=edge.toNodeIds;
	    				new_node_set.add(nfa.nodes.get(toNodeId));
	    			}
	    		}
	    	}
	    	return new_node_set;
	    }
					
							
					//确定化算法
	     public void  determine(NFA nfa) {

					//先计算nfa的起始节点的闭包
					Node start_node = nfa.nodes.get(nfa.startId);
					Set<Node> new_start_node_set =new HashSet<Node>();
					new_start_node_set.add(start_node);
				   epsilon_closure(new_start_node_set, nfa);//start_node加入new_start_node_set集合并求闭包
					// 初始化将初始点的 集合！！加入队列中
				   ArrayList<Set<Node>> node_queue = new ArrayList<Set<Node>>();
			        //把初始点的集合作为node_queue队列的第一个元素加入到队列
				   node_queue.add(new_start_node_set);
	               	
		 					int now_id = 0;
							dfa.add_node(now_id, 0, 0, "");
							// 对dfa的nodes操作，因为是按照顺序进入的，所以point和from_node_id是相同的
							
							int point = 0;
	               while (point < node_queue.size()) {
							// 取出队列中未计算的最靠前的set
						    Set<Node> node_set = (Set<Node>) node_queue.get(point);
							// 对每一个tag进行move计算
						    ArrayList<String> tags=new ArrayList<String>();
						    for (Node node:node_set) {
						    	tags.add(node.tag);
						    }
							for (String tag:tags) {
						    Set<Node> move_node_set = this.move( node_set, nfa, tag);
						    
							// 如果是空则忽略
							if (move_node_set.size() == 0){
							}

							// 非空且未出现过需要连接edge，并添加node
							else if (!node_queue.contains(move_node_set)) {
							// 先加入队列，用于继续计算
							node_queue.add(move_node_set);
							// 对DFA处理node和edges
							// 从move_node_set中的第一个节点获得is_final 和 is_back_off
							int is_final = 0;
							int is_back_off = 0;
							// 获得一个isFinal, isBackOff
							String node_tag = "";
							for (Node one : move_node_set) {
							is_final = one.isFinal;
							is_back_off = one.isBackOff;
							node_tag = one.tag;
							}
							now_id += 1;
							// self.add_node(now_id, is_final, is_back_off, tag)
							// print(now_id, is_final, is_back_off, node_tag)
							dfa.add_node(now_id, is_final, is_back_off, node_tag);
							dfa.add_edges(point, now_id, tag);
							}
							
							//非空但出现过只需要连接edge
							else{
							// 计算to_node_id，就是在node_queue中的index
							int to_node_id = node_queue.indexOf(move_node_set);  
							dfa.add_edges(point, to_node_id, tag);
							point += 1;
							// for i in self.edges:
							// print('fromId:'+str(i.fromNodeId)+' tag:'+str(i.tag)+' toNodeId:
							//'+str(i.toNodeIds))

							}
			
		}
							}
				

	               }
	               
	     
	     
	  
	     
	     
	     
	     
	}
					
					
					
					

					
					

