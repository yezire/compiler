package lexical_analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.NodeList;

public class NFA {
	private int nowId;
	private int startId;
	      //��Žڵ�
			ArrayList<Node> nodes = new ArrayList<Node>();
		  //��ű�
			ArrayList<Edge> edges = new ArrayList<Edge>();
	public NFA() {
			
			// ��ǰ״̬�����ڵ�λ��
			this.nowId = 0;
			// ��ʼ�ڵ�
			this.startId = 0;
			
			// ��ʼ��nodes��edges
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
			//��� SE
			this.add_node(21, 1, 0, "SE");
			this.add_node(22, 1, 0, "SE");
			this.add_node(23, 1, 0, "SE");
			this.add_node(24, 1, 0, "SE");
			this.add_node(25, 1, 0, "SE");
			this.add_node(26, 1, 0, "SE");
			//��ʶ��IDN
			this.add_node(27, 1, 0, "OP");
			this.add_node(28, 1, 1, "IDNorKWorOP");
			// ������������
			this.add_node(29, 0, 0, "");
			this.add_node(30, 1, 1, "INT");
			this.add_node(31, 0, 0, "");
			this.add_node(32, 1, 1, "FLOAT");
			
			this.add_node(33, 1, 0, "INT");
			
			
			//��ӱߵ���Ϣ
			//����OP�� <=>Ϊֹ
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
			//���SE
			this.add_edges(0, 21, "[(]");
			this.add_edges(0, 22, "[)]");
			this.add_edges(0, 23, "[{]");
			this.add_edges(0, 24, "[}]");
			this.add_edges(0, 25, ";");
			this.add_edges(0, 26, ",");
			//��ʶ��
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
			//�������ֵ�
			this.add_edges(0,  33, "[0]");
			
	}

	
	//��ӽڵ�
	public void add_node(int id, int i, int j,String tag) {
	Node new_node = new Node(id, i, j, tag);
	nodes.add(new_node);
		
	}
	
	//��ӱ�
	public void add_edges(int from_node_id, int i, String tag) {
	Edge new_edge =new Edge(from_node_id, i, tag);
	edges.add(new_edge);
	}
	
	//��ָ��ָ��ʼ�ڵ�
	public void get_start() {
	this.nowId = this.startId;
	}
	
	//�Ƿ����
	public int is_final(int id) {
	// ��Ϊ�ǰ���˳����ӵĽڵ�,����nodes���±��Ӧ��һ����id
	return this.nodes.get(id).isFinal;
	}
	
	//�Ƿ���Ҫ�˳�һ���ַ�
	public int is_back_off(int id) {
	return nodes.get(id).isBackOff;
	}
	//���tag
	
	public String get_tag(int id) {
	//���Ը���tag������Ҫ������
	return this.nodes.get(id).tag;
	}


	
	
}
