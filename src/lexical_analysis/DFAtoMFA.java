package lexical_analysis;



import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DFAtoMFA {
	private static final String NULL = null;
	TokenEnum tokenenum=new TokenEnum();
	 DFA dfa;
	int nowId;
	int startId;
	//存放节点
		ArrayList<Node> nodes = new ArrayList<Node>();
		    //存放边
		ArrayList<DFAEdge> edges = new ArrayList<DFAEdge>();
	MFA mfa=new MFA();

	public  DFAtoMFA(DFA dfa) {
		this.dfa = dfa;
		// 当前状态机所在的位置
		this.nowId = 0;
		// 开始节点
		this.startId = 0;
		this.buildMFA(dfa);
	}
		//从 node 经过 tag 转移能到达的所有状态的集合
		public int getToNode(int nodeId,String tag) {
		for( DFAEdge i : this.dfa.edges) {
		if(i.fromNodeId == nodeId && i.tag == tag) {
		return i.toNodeId;
		}
		}
		return 0;
		}

        public MFA buildMFA(DFA dfa) {
		//如果只有一个节点，那它本身为MFA
			if (this.dfa.nodes.size() <= 1) {
			this.mfa.nodes = dfa.nodes;
			this.mfa.edges = dfa.edges;
			}

			ArrayList<Integer> finalNoBackNodesOP = new ArrayList<Integer>();//存储终态并且不会回退的节点的编号
			ArrayList<Integer> finalNoBackNodesSE = new ArrayList<Integer>();
			ArrayList<Integer> finalNoBackNodesID = new ArrayList<Integer>();
			ArrayList<Integer> finalNoBackNodesSTR = new ArrayList<Integer>();
			
			ArrayList<Integer> finalBackNodesOP = new ArrayList<Integer>();//存储终态并且会回退的节点的编号
			ArrayList<Integer> finalBackNodesID = new ArrayList<Integer>();
			ArrayList<Integer> finalBackNodesINT = new ArrayList<Integer>();
			ArrayList<Integer> finalBackNodesFL = new ArrayList<Integer>();
			
			ArrayList<Integer> noFinalNodes = new ArrayList<Integer>();
			
			ArrayList<Integer> nodeIds = new ArrayList<Integer>();//存储所有节点的Id
			
			for (Node i: this.dfa.nodes) {
			nodeIds.add(i.id);
		
			if (i.isFinal == 1 && i.isBackOff == 1) {
				if (i.tag == "OP") finalBackNodesOP.add(i.id);
				if (i.tag == "IDNorKWorOP") finalBackNodesID.add(i.id);
				if (i.tag == "INT") finalBackNodesINT.add(i.id);
				if (i.tag == "FLOAT") finalBackNodesFL.add(i.id);
			}
			if (i.isFinal == 1 && i.isBackOff == 0) {
				if (i.tag == "OP") finalNoBackNodesOP.add(i.id);
				if (i.tag == "SE") finalNoBackNodesSE.add(i.id);
				if (i.tag == "IDNorKWorOP") finalNoBackNodesID.add(i.id);
				if (i.tag == "STRING") finalNoBackNodesSTR.add(i.id);
			}
			if (i.isFinal == 0) noFinalNodes.add(i.id);
			}
			
			
			
			HashMap<Integer, Integer> pos = new HashMap<Integer, Integer>();
			for(int i=0;i<nodeIds.size();i++) {
				pos.put(nodeIds.get(i), i);
			//pos = dict(zip(nodeIds,range(len(nodeIds))))
			//pos 中存储每一个节点对应处于的集合编号 一直处于不断地变化中
			}
			
			//把arraylist转换为set，再换回为arraylist确保了唯一性
			Set<Integer> set1  = new HashSet<Integer>(finalBackNodesOP);
			Set<Integer> set2  = new HashSet<Integer>(finalBackNodesID);
			Set<Integer> set3  = new HashSet<Integer>(finalBackNodesINT);
			Set<Integer> set4  = new HashSet<Integer>(finalBackNodesFL);
			
			Set<Integer> set5  = new HashSet<Integer>(finalNoBackNodesID);
			Set<Integer> set6  = new HashSet<Integer>(finalNoBackNodesOP);
			Set<Integer> set7  = new HashSet<Integer>(finalNoBackNodesSE);
			Set<Integer> set8  = new HashSet<Integer>(finalNoBackNodesSTR);
			
			Set<Integer> set9  = new HashSet<Integer>(noFinalNodes);
			
			ArrayList<Integer> list1 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list2 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list3 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list4 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list5 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list6 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list7 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list8 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list9 = new ArrayList<Integer>(set1);
			ArrayList<Integer> list10 = new ArrayList<Integer>(set1);
			
			//可用于判断状态《节点ID：节点状态》
			for (Integer i : list1) {
			pos.put(i, 0);//finalBackNodesOP
			}
			for (Integer i : list2) {
				pos.put(i, 1);//finalBackNodesID
				}
			for (Integer i : list3) {
				pos.put(i, 2);//finalBackNodesINT
				}
			for (Integer i : list4) {
				pos.put(i, 3);//finalBackNodesFL
				}
			for (Integer i : list5) {
				pos.put(i, 4);//finalNoBackNodesID
				}
			for (Integer i : list6) {
				pos.put(i, 5);//finalNoBackNodesOP
				}
			for (Integer i : list7) {
				pos.put(i, 6);//finalNoBackNodesSE
				}
			for (Integer i : list8) {
				pos.put(i, 7);//finalNoBackNodesSTR
				}
			for (Integer i : list9) {
				pos.put(i, 8);//noFinalNodes
				}

			ArrayList<ArrayList<Integer>> allsets = new ArrayList<ArrayList<Integer>>();
			allsets.add(list1);
			allsets.add(list2);
			allsets.add(list3);
			allsets.add(list4);
			allsets.add(list5);
			allsets.add(list6);
			allsets.add(list7);
			allsets.add(list8);
			allsets.add(list9);
			
			int counts = 10;
			boolean flag = true;
			while(flag)
				flag = false;
				for (String tag : tokenenum.tags) {
					for (ArrayList<Integer> list : allsets) {
						//对每个list建立一个map dic和一个数组lists
						HashMap<Integer, Integer> dic = new HashMap<Integer, Integer>();// 存储节点和新对应的编号
						ArrayList<Integer> lists = new ArrayList<>();
					
						//	 找出某个set中通过某个tag能够到达的所有节点
						for (Integer oneNode:list){
							int num = this.getToNode(oneNode,tag);
							 num = pos.get(num);//获取转移状态对应的集合编号
							// print(num)
							if (! dic.containsKey(num)) { //如果没有，建立该状态对应的集合编号 的字典关系 新建
								dic.put(num, counts) ;
								counts+=1;
							}//dic记录《能到的节点id，set目前拆分出来的总数》即《num，counts》
							lists.add(dic.get(num));
				        }
							
						if (lists.size() == 0) continue;
			                //print(lists)说明不拆分
						
						//拆分的过程
						if (dic.size()>1) { //证明该集合中状态转移不是转移到同一处, 拆分元素, 跳出循环
							flag = true;
							HashMap<Integer, ArrayList<Integer>> tmp_set = new HashMap<Integer, ArrayList<Integer>>(); //新编号与新数值相对应 加入不同的新list
							//tmp_set是新生成的，《新list编号，list《节点ID》》
							for (int i=0;i<list.size();i++) {//list.size对与每一个list1，list2……
								if (! tmp_set.containsKey(lists.get(i))){
								tmp_set.put(lists.get(i),list);
								pos.put(list.get(i),lists.get(i)); //更新pos 更新状态 所在的集合的编号
							}
							allsets.remove(list); //将旧的list移除
							
							
							for (ArrayList j : tmp_set.values()){//将新的list 加入
								allsets.add(j);
							    break;
						}
						}
						}
							
			if (flag == true) break;
			
			        } 
				}
			

			
			
			for (int i=0; i<allsets.size();i++) { //计算出每个数值对应的新的数值
				for (int j : allsets.get(i)) {
				pos.put(j, i+1);
				}
			// print(pos)
			// print(set1)
			// print(pos)
			// for i in self.dfa.nodes:
			// print('id: ' + str(i.id) + 'isBackOff: ' + str(i.isBackOff))
			
			this.getnewDfa(pos,this.dfa);
			
	        }
			return mfa;
      }
		
	
		
											
		public void getnewDfa(HashMap hashmap,DFA dfa) {
			this.startId = (int) hashmap.get(dfa.startId);
			// print(self.startId)
			HashSet <Integer> addSets = new HashSet<Integer>(); // 存储加入新的DFA的节点
			// 在新的DFA加入节点
			
			hashmap.forEach(( key,  value) -> {
				if (!addSets.contains(value)) {
					addSets.addAll(hashmap.values());
					mfa.add_node((int)value, dfa.nodes.get((int) key).isFinal, dfa.nodes.get((int) key).isBackOff,dfa.nodes.get((int) key).tag);	
		        }
			});
			
		    //三元组形式的dfaedge，addedges记录已加入的边
		    HashMap <Integer, String> map = new HashMap<Integer, String>();  
		    HashMap <Integer, HashMap<Integer, String>> addEdges = new HashMap<Integer, HashMap<Integer, String>>();
		    for (DFAEdge edge : dfa.edges) {
		    	map.put((int)hashmap.get(edge.toNodeId), edge.tag);
		        HashMap <Integer, HashMap<Integer, String>> tup = new HashMap<Integer, HashMap<Integer, String>>();
		        tup.put ((int) hashmap.get(edge.fromNodeId), map);
		        //再加入addedges，map和tup只是中间结构
					for(int tupkey:tup.keySet()) {
					if (!addEdges.containsKey(tupkey)) {
					mfa.add_edges((int)hashmap.get(edge.fromNodeId), (int)hashmap.get(edge.toNodeId),edge.tag);
					addEdges.put((int) hashmap.get(edge.fromNodeId), map);
					}
					// print('addSets: ' + str(addSets))
					}
	        }
		
			}
		
		
		//添加节点
		public void add_node( int id, int is_final, int is_back_off, String tag) {
			Node new_node = new Node(id, is_final, is_back_off, tag);
			this.nodes.add(new_node);
		}
							
		// 添加边
		public void add_edges( int from_node_id, int to_node_id, String tag) {
			DFAEdge new_edge = new DFAEdge(from_node_id, to_node_id, tag);
			this.edges.add(new_edge);
		}
							
		// 将指针指向开始节点
		public void get_start() {
			this.nowId = this.startId;
		}
							
		// 获得下一个ID
		public boolean next_id(String tag) {
			for (DFAEdge edge : this.edges) {
				if (edge.fromNodeId == this.nowId &&  edge.tag.matches(tag)){
					//并将nowId指向新的位置
					this.nowId = edge.toNodeId;
					return true;// 说明成功找到下一个节点
				}
					return false;
			}
			return false;
		}
											
		public int is_final(int id) {
			// 因为是按照顺序添加的节点,所以nodes的下标对应着一样的id
			for(Node i : this.nodes){
			if (i.id == id) return i.isFinal;
			}
			return 0;
		}
		
		//是否需要退出一个字符					
		public int is_back_off(int id) {
			for (Node i : this.nodes){
			if (i.id == id) return i.isBackOff;
			}
			return 0;
		}
		
		// 获得tag	
		public String get_tag(int id) {
			// 可以根据tag返回需要的内容
			for (Node i : this.nodes) {
			if (i.id == id) return i.tag;
			}
			return NULL;
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
