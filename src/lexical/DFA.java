package lexical_analysis;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DFA {
	//存放节点
	ArrayList<Node> nodes = new ArrayList<Node>();
	    //存放边
	ArrayList<Edge> edges = new ArrayList<Edge>();
		// 当前状态机所在的位置
		this.nowId = 0;
		// 开始节点
		this.startId = 0;
		// 确定化将NFA转化为DFA，将nodes和edges填上
	    this.determine(nfa);
		
		//e-closure计算
	    public static Set<Node> epsilon_closure(Set node_set, NFA nfa) {// 查找node_set经过任意条epsilon弧能抵达的节点们
	    	
	    	//加入nfa定义的边
	    	List<Edge> nfaedges = nfa.edges;
	    	// 过滤掉重复的，所来了，以先建立空的集合set，然后把node_set的id加进去
	    	Set node_id_set = Collections.EMPTY_SET;
	    	// 获得所有的node的id
	    	for node in node_set:
	    		node_id_set.add(node.id);

	    	for node in node_set{
	    		node_id = node.id;//把每个id赋值给node_id
	    		for edge in edges{
	    			if (edge.tag == "epsilon" && edge.fromNodeId == node_id) {
	    				// 如果新的node不在node_set中则加入，否则跳过
	    				// 遍历所有的可以抵达的node
	    				for toNodeId in edge.toNodeIds:
	    					if toNodeId in node_id_set:
	    						continue;
	    				    else:
	    					// 将能够抵达的node加入new_node_set
	    					node_set.add(nfa.nodes[toNodeId]);
	    			}
	    		}
	    	}
	    	return node_set;
	    }
					
	     public static move(Set node_set, NFA nfa,String tag) {
					edges = nfa.edges;
					# 返回的全新node集合
					new_node_set = set()
					# 用于判断是否已经出现过了
					node_id_set = set()
					# # 获得所有的node的id
					# for node in node_set:
					# node_id_set.add(node.id)
					# 遍历每一个node
					for node in node_set:
					for edge in edges:
					# 相同tag的匹配
					if edge.fromNodeId == node.id and edge.tag == tag:
					for toNodeId in edge.toNodeIds:
					if toNodeId in node_id_set:
					continue
					else:
					new_node_set.add(nfa.nodes[toNodeId])
					return new_node_set
					# 确定化算法
					def determine(self, nfa: NFA):
					self.nodes = []
					# 先计算nfa的起始节点的闭包
					start_node = nfa.nodes[nfa.startId]
							# new_start_node_set = self.epsilon_closure(self, {start_node}, nfa)
							new_start_node_set = {start_node}
							# 初始化将初始点加入集合中
							node_queue = [new_start_node_set]
							now_id = 0
							self.add_node(now_id, 0, 0, "")
							# 因为是按照顺序进入的，所以point和from_node_id是相同的
							point = 0
							while point < len(node_queue):
							# 取出队列中未计算的最靠前的set
							node_set = node_queue[point]
							# 对每一个tag进行move计算
							for tag in tags:
							move_node_set = self.move(self, node_set, nfa, tag)
							# 如果是空则忽略
							if len(move_node_set) == 0:
							continue
							# 非空且未出现过需要连接edge，并添加node
							elif not (move_node_set in node_queue):
							# 先加入队列，用于继续计算
							node_queue.append(move_node_set)
							# 对DFA处理node和edges
							# 从move_node_set中的第一个节点获得is_final 和 is_back_off
							is_final = 0
							is_back_off = 0
							# 获得一个isFinal, isBackOff
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
							# 非空但出现过只需要连接edge
							else:
							# 计算to_node_id，就是在node_queue中的index
							to_node_id = node_queue.index(move_node_set)
							self.add_edges(point, to_node_id, tag)
							point += 1
							# for i in self.edges:
							# print('fromId:'+str(i.fromNodeId)+' tag:'+str(i.tag)+' toNodeId:
							'+str(i.toNodeIds))
							# 添加节点
							def add_node(self, id, is_final, is_back_off, tag):
							new_node = Node(id, is_final, is_back_off, tag)
							self.nodes.append(new_node)
							# 添加边
							def add_edges(self, from_node_id, to_node_id: int, tag):
							new_edge = DFAEdge(from_node_id, to_node_id, tag)
							self.edges.append(new_edge)
			
		}
	     
	}
					
					
					
					

					
					

