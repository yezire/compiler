package lexical_analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NFA {
	     private int nowId;
	     private int startId;
	      //存放节点
			ArrayList<Node> nodes = new ArrayList<>();
		  //存放边
			ArrayList<Edge> edges = new ArrayList<Edge>();

	public NFA() {
			
			// 当前状态机所在的位置
			this.nowId = 0;
			// 开始节点
			this.startId = 0;
			
			// 初始化nodes和edges
			// OP
			this.add_node(0, 0, 0, "");
			this.add_node(1, 1, 0, "OP");
			this.add_node(2, 1, 0, "OP");
			this.add_node(3, 1, 0, "OP");
			this.add_node(4, 1, 0, "OP");
			this.add_node(5, 1, 0, "OP");
			this.add_node(6, 0, 0, "");
			this.add_node(7, 0, 0, "");
			this.add_node(8, 0, 0, "");
			this.add_node(9, 1, 0, "OP");
			this.add_node(10, 1, 0, "OP");
			this.add_node(11, 1, 0, "OP");
			this.add_node(12, 0, 0, "");
			this.add_node(13, 1, 0, "OP");
			this.add_node(14, 0, 0, "");
			this.add_node(15, 1, 0, "OP");
			this.add_node(16, 0, 0, "");
			this.add_node(17, 1, 0, "OP");
			this.add_node(18, 1, 1, "OP");
			this.add_node(19, 1, 1, "OP");
			this.add_node(20, 1, 1, "OP");
			//界符 SE
			this.add_node(21, 1, 0, "SE");
			this.add_node(22, 1, 0, "SE");
			this.add_node(23, 1, 0, "SE");
			this.add_node(24, 1, 0, "SE");
			this.add_node(25, 1, 0, "SE");
			this.add_node(26, 1, 0, "SE");
			//标识符IDN
			this.add_node(27, 1, 0, "OP");
			this.add_node(28, 1, 1, "IDNorKWorOP");
			// 整数、浮点数
			this.add_node(29, 0, 0, "");
			this.add_node(30, 1, 1, "INT");
			this.add_node(31, 0, 0, "");
			this.add_node(32, 1, 1, "FLOAT");
			
			this.add_node(33, 1, 0, "INT");
			
			
			//添加边的信息
			//部分OP到 <=>为止
			this.add_edges(0, 0, " ");
			this.add_edges(0, 1, "+");
			this.add_edges(0, 2, "-");
			this.add_edges(0, 3, "*");
			this.add_edges(0, 4, "/");
			this.add_edges(0, 5, "%");
			this.add_edges(0, 6, "=");
			this.add_edges(0, 7, ">");
			this.add_edges(0, 8, "<");
			this.add_edges(6, 18, "[^=]");
			this.add_edges(6, 9, "=");
			this.add_edges(7, 19, "[^=]");
			this.add_edges(7, 11, "=");
			this.add_edges(8, 20, "[^=]");
			this.add_edges(8, 10, "=");
			this.add_edges(0, 12, "!");
			this.add_edges(12,13, "=");
			this.add_edges(0, 14, "&");
			this.add_edges(14,15, "&");
			this.add_edges(0, 16, "[|]");
			this.add_edges(16, 17, "[|]");
			//界符SE
			this.add_edges(0, 21, "[(]");
			this.add_edges(0, 22, "[)]");
			this.add_edges(0, 23, "[{]");
			this.add_edges(0, 24, "[}]");
			this.add_edges(0, 25, ";");
			this.add_edges(0, 26, ",");
			//标识符
			this.add_edges(0,  27, "[_a-zA-Z]");
			this.add_edges(27, 27, "[_0-9a-zA-Z]");
			this.add_edges(27, 28, "[^_0-9a-zA-Z]");
			//int, float
			this.add_edges(0,  29, "[1-9]");
			this.add_edges(29, 29, "[0-9]");
			this.add_edges(29, 30, "[^.0-9]");
			this.add_edges(29, 31, "[.]");
			this.add_edges(31, 31, "[0-9]");
			this.add_edges(31, 32, "[^0-9]");
			//后续发现的
			this.add_edges(0,  33, "[0]");
	}
	
	//添加节点
	public void add_node(int id, int i, int j,String tag) {
	Node new_node = new Node(id, i, j, tag);
	nodes.add(new_node);
	}

	//添加边
	public void add_edges(int from_node_id, int i, String tag) {
    for(Edge edge:edges) {
	if(edge.fromNodeId==from_node_id) {
		edge.toNodeIds.add(i);
	}//如果该from_node_id已经存在了，那么只用将i加入toNodeIds这个set
	else{
    Set<Integer> I = null;
    I.add(i);
	Edge new_edge =new Edge(from_node_id, I, tag);
	edges.add(new_edge);
	}//如果from_node_id不存在了，创建一个新的set叫I并且将i写入I，再new一条边
    }
    
	}
	
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


	
	
}
