package lexical_analysis;

import java.util.Set;

public class DFAEdge {
	private int fromNodeId;
	private String tag;
	private int toNodeIds;
	// from �ڵ�
	public DFAEdge(int from_node_id, int to_node_id, String tag){
	this.fromNodeId = from_node_id;
	// to �ڵ� ͬһ��tag����ȥ�������нڵ㼯��
	this.toNodeIds = to_node_id;
	// ת����Ҫ����Ϣ��ʹ��������ʽ��ʾ
	this.tag = tag;
	}


}
