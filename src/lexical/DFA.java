package lexical_analysis;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class DFA {
	TokenEnum tokenenum=new TokenEnum();
	int nowId;
	int startId;
	//存放节点
	ArrayList<Node> nodes = new ArrayList<Node>();
	    //存放边
	ArrayList<DFAEdge> edges = new ArrayList<DFAEdge>();
		
	public DFA() {
		NFA nfa = null;//初始化使用一个NFA，然后用确定化和最小化算法
		// 当前状态机所在的位置
		this.nowId = 0;
		// 开始节点
		this.startId = 0;
		// 确定化将NFA转化为DFA，将nodes和edges填上
	    this.determine(nfa);
	}
	
	
	
		//e-closure计算
	    public  Set<Node> epsilon_closure(Set<Node> node_set, NFA nfa) {// 查找node_set经过任意条epsilon弧能抵达的节点们
	    	//加入nfa定义的边
	    	List<Edge> nfaedges = nfa.edges;
	    	// 过滤掉重复的，所来了，以先建立空的集合set，然后把node_set的id加进去
	    	Set node_id_set = Collections.EMPTY_SET;
	    	// 获得所有的node的id，写入node_id_set
	    	for (Node node:node_set) {node_id_set.add(node.id);}

	    	for (Node node:node_set){
				int node_id = node.id;//把每个id赋值给node_id,node_id是一次性的，每次遍历一个节点就刷新
	    		for (Edge edge:nfaedges){
	    			if (edge.tag == "epsilon" && edge.fromNodeId == node_id) {//遍历nfa的边，如果tag是epsilon且边的起始点和该节点的id一样
	    				// 如果新的node不在node_set中则加入，否则跳过
	    				// 遍历所有的可以抵达的node
	    				for ( Integer toNodeId : edge.toNodeIds) {
							node_set.add(nfa.nodes.get(toNodeId));
	    				    }
	    			}
	    		}
	    	}
	    	return node_set;
	    }
					
	    	
	    	
	     public  Set<Node> move(Set<Node> node_set, NFA nfa,String tag) {//计算node_set匹配tag移动后产生的新的节点集合
	    	     //加入nfa定义的边
		    	List<Edge> nfaedges = nfa.edges;
		    	//返回新的node集合new_node_set;
		    	Set new_node_set = Collections.EMPTY_SET;
		    	// 过滤掉重复的，所来了，以先建立空的集合set，然后把node_set的id加进去
		    	Set node_id_set = Collections.EMPTY_SET;
		    	// 获得所有的node的id，写入node_id_set
		    	for (Node node:node_set) {node_id_set.add(node.id);}

		    	for (Node node:node_set){
					int node_id = node.id;//把每个id赋值给node_id,node_id是一次性的，每次遍历一个节点就刷新
		    		for (Edge edge:nfaedges){
		    			if (edge.tag == tag && edge.fromNodeId == node_id) {//遍历nfa的边，如果tag是匹配且边的起始点和该节点的id一样
		    				// 如果新的node不在node_set中则加入，否则跳过
		    				// 遍历所有的可以抵达的node
		    				for ( Integer toNodeId : edge.toNodeIds) {
		    					new_node_set.add(nfa.nodes.get(toNodeId));
		    				    }
		    			}
		    		}
		    	}
		    	return new_node_set;
		    }
					
							
					//确定化算法
	     public  void determine(NFA nfa) {

					//先计算nfa的起始节点的闭包
					Node start_node = nfa.nodes.get(startId);
					Set<Node> new_start_node_set =Collections.EMPTY_SET;
					new_start_node_set.add(start_node);
				   epsilon_closure(new_start_node_set, nfa);//start_node加入new_start_node_set集合并求闭包
					// 初始化将初始点的 集合！！加入队列中
				   ArrayList<Set<Node>> node_queue = new ArrayList<Set<Node>>();
			        //把初始点的集合作为node_queue队列的第一个元素加入到队列
				   node_queue.add(new_start_node_set);
	               	
		 					int now_id = 0;
							this.add_node(now_id, 0, 0, "");
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
							this.add_node(now_id, is_final, is_back_off, node_tag);
							this.add_edges(point, now_id, tag);
							}
							
							//非空但出现过只需要连接edge
							else{
							// 计算to_node_id，就是在node_queue中的index
							int to_node_id = node_queue.indexOf(move_node_set);  
							this.add_edges(point, to_node_id, tag);
							point += 1;
							// for i in self.edges:
							// print('fromId:'+str(i.fromNodeId)+' tag:'+str(i.tag)+' toNodeId:
							//'+str(i.toNodeIds))

							}
			
		}
							}

	               }
	               
	     
	     
	     
	   //添加节点
	 	public void add_node(int id, int i, int j,String tag) {
	 	Node new_node = new Node(id, i, j, tag);
	 	nodes.add(new_node);
	 		
	 	}
	 	
	 	//添加边
	 	public void add_edges(int from_node_id, int i, String tag) {
	    // for(DFAEdge edge:edges) 
	 	DFAEdge new_edge =new DFAEdge(from_node_id, i, tag);
	 	edges.add(new_edge);
	 	}//如果from_node_id不存在了，创建一个新的set叫I并且将i写入I，再new一条边
	     
	 	//将指针指向开始节点
		public void get_start() {
		this.nowId = this.startId;
		}
		
		//是否结束
		public int is_final(int id) {
		// 因为是按照顺序添加的节点,所以nodes的下标对应着一样的id
		return this.nodes.get(id).isFinal;
		}
		
		//是否需要退出一个字符
		public int is_back_off(int id) {
		return nodes.get(id).isBackOff;
		}
		//获得tag
		
		public String get_tag(int id) {
		//可以根据tag返回需要的内容
		return this.nodes.get(id).tag;
		}
		
		// 获得下一个ID
		public boolean next_id(String tag) {
			for (DFAEdge edge : this.edges) {
				if (edge.fromNodeId == this.nowId && edge.tag==tag){
					//并将nowId指向新的位置
					this.nowId = edge.toNodeId;
					return true;// 说明成功找到下一个节点
				}
					return false;
			}
			return false;
		}
		
		public String get_token_type(String token, String node_tag) {
			//KW, OP, SE, IDN, INT, FLOAT, STR
			//OP, SE, INT, FLOAT,STR都可以直接判断
			if (node_tag == "OP" || node_tag == "SE" ||node_tag == "INT" || node_tag== "FLOAT" ||node_tag == "STRING") {
			return node_tag;
			}
			else if (node_tag == "IDNorKWorOP") {
				if (tokenenum.TYPE_TO_CONTENT_DICT_KW.containsKey(token)) {
				return "KW";
				}
				else if (tokenenum.TYPE_TO_CONTENT_DICT_OP.containsKey(token)) {
				return "OP";
				}
				else {
				return "IDN";
				}
			}
			return null;
		}
					
		// 判断编号
		public String get_token_num(String token, String token_type) {
			if (token_type == "IDN" || token_type == "INT" || token_type == "FLOAT"|| token_type == "STRING") {
			return token;
			}
			else if (token_type == "KW") {
			return tokenenum.TYPE_TO_CONTENT_DICT_KW.get(token).toString();
			}
			else if (token_type == "OP") {
			return tokenenum.TYPE_TO_CONTENT_DICT_OP.get(token).toString();
			}
			else if(token_type == "SE") {
			return tokenenum.TYPE_TO_CONTENT_DICT_SE.get(token).toString();
			}
			return null;
		}
	     
	     
	     
	     
	     
	     
	}
					
					
					
					

					
					

