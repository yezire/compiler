package lexical_analysis;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DFA {
	//��Žڵ�
	ArrayList<Node> nodes = new ArrayList<Node>();
	    //��ű�
	ArrayList<Edge> edges = new ArrayList<Edge>();
		// ��ǰ״̬�����ڵ�λ��
		this.nowId = 0;
		// ��ʼ�ڵ�
		this.startId = 0;
		// ȷ������NFAת��ΪDFA����nodes��edges����
	    this.determine(nfa);
		
		//e-closure����
	    public static Set<Node> epsilon_closure(Set node_set, NFA nfa) {// ����node_set����������epsilon���ִܵ�Ľڵ���
	    	
	    	//����nfa����ı�
	    	List<Edge> nfaedges = nfa.edges;
	    	// ���˵��ظ��ģ������ˣ����Ƚ����յļ���set��Ȼ���node_set��id�ӽ�ȥ
	    	Set node_id_set = Collections.EMPTY_SET;
	    	// ������е�node��id
	    	for node in node_set:
	    		node_id_set.add(node.id);

	    	for node in node_set{
	    		node_id = node.id;//��ÿ��id��ֵ��node_id
	    		for edge in edges{
	    			if (edge.tag == "epsilon" && edge.fromNodeId == node_id) {
	    				// ����µ�node����node_set������룬��������
	    				// �������еĿ��Եִ��node
	    				for toNodeId in edge.toNodeIds:
	    					if toNodeId in node_id_set:
	    						continue;
	    				    else:
	    					// ���ܹ��ִ��node����new_node_set
	    					node_set.add(nfa.nodes[toNodeId]);
	    			}
	    		}
	    	}
	    	return node_set;
	    }
					
	     public static move(Set node_set, NFA nfa,String tag) {
					edges = nfa.edges;
					# ���ص�ȫ��node����
					new_node_set = set()
					# �����ж��Ƿ��Ѿ����ֹ���
					node_id_set = set()
					# # ������е�node��id
					# for node in node_set:
					# node_id_set.add(node.id)
					# ����ÿһ��node
					for node in node_set:
					for edge in edges:
					# ��ͬtag��ƥ��
					if edge.fromNodeId == node.id and edge.tag == tag:
					for toNodeId in edge.toNodeIds:
					if toNodeId in node_id_set:
					continue
					else:
					new_node_set.add(nfa.nodes[toNodeId])
					return new_node_set
					# ȷ�����㷨
					def determine(self, nfa: NFA):
					self.nodes = []
					# �ȼ���nfa����ʼ�ڵ�ıհ�
					start_node = nfa.nodes[nfa.startId]
							# new_start_node_set = self.epsilon_closure(self, {start_node}, nfa)
							new_start_node_set = {start_node}
							# ��ʼ������ʼ����뼯����
							node_queue = [new_start_node_set]
							now_id = 0
							self.add_node(now_id, 0, 0, "")
							# ��Ϊ�ǰ���˳�����ģ�����point��from_node_id����ͬ��
							point = 0
							while point < len(node_queue):
							# ȡ��������δ������ǰ��set
							node_set = node_queue[point]
							# ��ÿһ��tag����move����
							for tag in tags:
							move_node_set = self.move(self, node_set, nfa, tag)
							# ����ǿ������
							if len(move_node_set) == 0:
							continue
							# �ǿ���δ���ֹ���Ҫ����edge�������node
							elif not (move_node_set in node_queue):
							# �ȼ�����У����ڼ�������
							node_queue.append(move_node_set)
							# ��DFA����node��edges
							# ��move_node_set�еĵ�һ���ڵ���is_final �� is_back_off
							is_final = 0
							is_back_off = 0
							# ���һ��isFinal, isBackOff
							node_tag = ""
							for one in move_node_set:
							is_final = one.isFinal
							is_back_off = one.isBackOff
							node_tag = one.tag
							break
							now_id += 1
							# self.add_node(now_id, is_final, is_back_off, tag)
							# print(now_id, is_final, is_back_off, node_tag)
							self.add_node(now_id, is_final, is_back_off, node_tag)
							self.add_edges(point, now_id, tag)
							# �ǿյ����ֹ�ֻ��Ҫ����edge
							else:
							# ����to_node_id��������node_queue�е�index
							to_node_id = node_queue.index(move_node_set)
							self.add_edges(point, to_node_id, tag)
							point += 1
							# for i in self.edges:
							# print('fromId:'+str(i.fromNodeId)+' tag:'+str(i.tag)+' toNodeId:
							'+str(i.toNodeIds))
							# ��ӽڵ�
							def add_node(self, id, is_final, is_back_off, tag):
							new_node = Node(id, is_final, is_back_off, tag)
							self.nodes.append(new_node)
							# ��ӱ�
							def add_edges(self, from_node_id, to_node_id: int, tag):
							new_edge = DFAEdge(from_node_id, to_node_id, tag)
							self.edges.append(new_edge)
			
		}
	     
	}
					
					
					
					

					
					

