package lexical_analysis;

import java.util.Set;

public class DFAEdge {
	private int fromNodeId;
	private String tag;
	private int toNodeIds;
	// from 节点
	public DFAEdge(int from_node_id, int to_node_id, String tag){
	this.fromNodeId = from_node_id;
	// to 节点 同一个tag可以去到的所有节点集合
	this.toNodeIds = to_node_id;
	// 转化需要的信息，使用正则表达式表示
	this.tag = tag;
	}


}
