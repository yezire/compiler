package lexical_analysis;

import java.util.Set;

public class Edge {
		int fromNodeId;
		String tag;
		int toNodeIds;//应该是set集合的Set<Integer>
		public Edge(int from_node_id, int i, String tag){
			// from 节点
			this.fromNodeId = from_node_id;
			// to 节点 同一个tag可以去到的所有节点集合
			this.toNodeIds = i;
			// 转化需要的信息，使用正则表达式表示
			this.tag = tag;

		}
	}
	
