package lexical_analysis;

import java.util.Set;

public class Edge {
		private int fromNodeId;
		private String tag;
		private int toNodeIds;//Ӧ����set���ϵ�
		public Edge(int from_node_id, int i, String tag){
			// from �ڵ�
			this.fromNodeId = from_node_id;
			// to �ڵ� ͬһ��tag����ȥ�������нڵ㼯��
			this.toNodeIds = i;
			// ת����Ҫ����Ϣ��ʹ��������ʽ��ʾ
			this.tag = tag;

		}
	}
	

