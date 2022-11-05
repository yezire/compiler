package lexical_analysis;

import java.util.ArrayList;

public class MFA {
	private static final String NULL = null;
	TokenEnum tokenenum=new TokenEnum();
	int nowId;
	int startId;
	//存放节点
	ArrayList<Node> nodes = new ArrayList<Node>();
	    //存放边
	ArrayList<DFAEdge> edges = new ArrayList<DFAEdge>();
		
	public MFA() {
		
		// 当前状态机所在的位置
		this.nowId = 0;
		// 开始节点
		this.startId = 0;


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
