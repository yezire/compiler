package lexical_analysis;

public class DFAtoMFA {
	def __init__(self,dfa):
		self.dfa = dfa
		# ��Žڵ�
		self.nodes = []
		self.edges = []
		# ��ǰ״̬�����ڵ�λ��
		self.nowId = 0
		# ��ʼ�ڵ�
		self.startId = 0
		self.buildMFA()
		# �� node ���� tag ת���ܵ��������״̬�ļ���
		def getToNode(self,nodeId,tag):
		for i in self.dfa.edges:
		if(i.fromNodeId == nodeId and i.tag == tag):
		return i.toNodeIds
				return 100
						def buildMFA(self):
						# ���ֻ��һ���ڵ㣬��������ΪMFA
						if len(self.dfa.nodes) <= 1:
						self.mfa = self.dfa
						return
						finalNoBackNodesOP = [] # �洢��̬���Ҳ�����˵Ľڵ�ı��
						finalNoBackNodesSE = []
						finalNoBackNodesID = []
						finalNoBackNodesSTR = []
						finalBackNodesOP = [] #�洢��̬���һ���˵Ľڵ�ı��
						finalBackNodesID = []
						finalBackNodesINT = []
						finalBackNodesFL = []
						noFinalNodes = []
						nodeIds = [] #�洢���нڵ��Id
						for i in self.dfa.nodes:
						nodeIds.append(i.id)
						if i.isFinal == 1 and i.isBackOff == 1 :
						if i.tag == 'OP':
						finalBackNodesOP.append(i.id)
						if i.tag == 'IDNorKWorOP':
						finalBackNodesID.append(i.id)
						if i.tag == 'INT':
						finalBackNodesINT.append(i.id)
						if i.tag == 'FLOAT':
						finalBackNodesFL.append(i.id)
						if i.isFinal == 1 and i.isBackOff == 0 :
						if i.tag == 'OP':
						finalNoBackNodesOP.append(i.id)
						if i.tag == 'SE':
						finalNoBackNodesSE.append(i.id)
						if i.tag == 'IDNorKWorOP':
						finalNoBackNodesID.append(i.id)
						if i.tag == 'STRING':
						finalNoBackNodesSTR.append(i.id)
						if i.isFinal == 0:
						noFinalNodes.append(i.id)
						pos = dict(zip(nodeIds,range(len(nodeIds))))#pos �д洢ÿһ���ڵ��Ӧ���ڵļ�
						�ϱ�� һֱ���ڲ��ϵر仯��
						pos[100] = 9
						set1 = set(finalBackNodesOP)
						set2 = set(finalBackNodesID)
						set3 = set(finalBackNodesINT)
						set4 = set(finalBackNodesFL)
						set5 = set(finalNoBackNodesID)
						set6 = set(finalNoBackNodesOP)
						set7 = set(finalNoBackNodesSE)
						set8 = set(finalNoBackNodesSTR)
						set9 = set(noFinalNodes)
						set1 = list(set1)
						set2 = list(set2)
						set3 = list(set3)
						set4 = list(set4)
						set5 = list(set5)
						set6 = list(set6)
						set7 = list(set7)
						set8 = list(set8)
						set9 = list(set9)
						for i in set1:
						pos[i] = 0
						for i in set2:
						pos[i] = 1
						for i in set3:
						pos[i] = 2
						for i in set4:
						pos[i] = 3
						for i in set5:
						pos[i] = 4
						for i in set6:
						pos[i] = 5
						for i in set7:
						pos[i] = 6
						for i in set8:
						pos[i] = 7
						for i in set9:
						pos[i] = 8
						allsets = [set1,set2,set3,set4,set5,set6,set7,set8,set9]
						counts = 10
						flag = True
						while flag:
						flag = False
						for char in tags:
						for sub_set in allsets:
						dic = dict() # �洢�ڵ���¶�Ӧ�ı��
						lists = []
						# �ҳ�ĳ��set��ͨ��ĳ��tag�ܹ���������нڵ�
						for oneNode in sub_set:
						num = self.getToNode(oneNode,char)
						num = pos[num] #��ȡת��״̬��Ӧ�ļ��ϱ��
						# print(num)
						if num not in dic.keys(): #���û�н��� ��״̬��Ӧ�ļ��ϱ�
						�� ���ֵ��ϵ �½�
						dic[num] =counts
						counts+=1
						lists.append(dic[num])
						if len(lists) == 0:
						continue
						# print(lists)
						if len(dic) >1: #֤���ü�����״̬ת�� ����ת�Ƶ�ͬһ�� ���Ԫ�� ����
						ѭ��
						flag = True
						tmp_set=dict() #�±��������ֵ���Ӧ ���벻ͬ����list
						for i1 in range(len(sub_set)):
						if lists[i1] not in tmp_set.keys():
						tmp_set[lists[i1]]=list()
						tmp_set[ lists[i1] ].append(sub_set[i1])
						pos[sub_set[i1]] = lists[i1] #����pos ����״̬ ���ڵļ�
						�ϵı��
						allsets.remove(sub_set) #���ɵ�list�Ƴ�
						for i1 in tmp_set.values(): #���µ�list ����
						allsets.append(i1)
						break
						if flag == True:
						break
						for i in range(len(allsets)): #�����ÿ����ֵ��Ӧ���µ���ֵ
						for j in allsets[i]:
						pos[j] =i+1
						# print(pos)
						# print(set1)
						pos.pop(100)
						# print(pos)
						# for i in self.dfa.nodes:
						# print('id: ' + str(i.id) + 'isBackOff: ' + str(i.isBackOff))
						self.getnewDfa(pos,self.dfa)
						# ��ӽڵ�
						def add_node(self, id, is_final, is_back_off, tag):
						new_node = Node(id, is_final, is_back_off, tag)
						self.nodes.append(new_node)
						# ��ӱ�
						def add_edges(self, from_node_id, to_node_id: int, tag):
						new_edge = DFAEdge(from_node_id, to_node_id, tag)
						self.edges.append(new_edge)
						# ��ָ��ָ��ʼ�ڵ�
						def get_start(self):
						self.nowId = self.startId
						# �����һ��ID
						def next_id(self, tag):
						for edge in self.edges:
							if edge.fromNodeId == self.nowId and re.match(edge.tag, tag):
								# ����nowIdָ���µ�λ��
								self.nowId = edge.toNodeIds
								# ˵���ɹ��ҵ���һ���ڵ�
								return True
								return False
								def is_final(self, id):
								# ��Ϊ�ǰ���˳����ӵĽڵ�,����nodes���±��Ӧ��һ����id
								for i in self.nodes:
								if i.id == id:
								return i.isFinal
								# �Ƿ���Ҫ�˳�һ���ַ�
								def is_back_off(self, id):
								for i in self.nodes:
								if i.id == id:
								return i.isBackOff
								# ���tag
								def get_tag(self, id):
								# ���Ը���tag������Ҫ������
								for i in self.nodes:
								if i.id == id:
								return i.tag
								def get_token_type(self, token, node_tag):
								# KW, OP, SE, IDN, INT, FLOAT, STR
								# OP, SE, INT, FLOAT,STR������ֱ���ж�
								if node_tag == "OP" or node_tag == "SE" or node_tag == "INT" or node_tag
								== "FLOAT" or node_tag == "STRING":
								return node_tag
								elif node_tag == "IDNorKWorOP":
								keywords = TYPE_TO_CONTENT_DICT_KW.keys()
								ops = TYPE_TO_CONTENT_DICT_OP.keys()
								if token in keywords:
								return "KW"
								elif token in ops:
								return "OP"
								else:
								return "IDN"
								# �жϱ��
								def get_token_num(self, token, token_type):
								if token_type == "IDN" or token_type == "INT" or token_type == "FLOAT"
								or token_type == "STRING":
								return token
								elif token_type == "KW":
								return TYPE_TO_CONTENT_DICT_KW[token]
								elif token_type == "OP":
								return TYPE_TO_CONTENT_DICT_OP[token]
								elif token_type == "SE":
								return TYPE_TO_CONTENT_DICT_SE[token]
								def getnewDfa(self,dicts,dfa):
									self.startId = dicts[dfa.startId]
											# print(self.startId)
											addSets = [] # �洢�����µ�DFA�Ľڵ�
											# ���µ�DFA����ڵ�
											for key,value in dicts.items():
											if value not in addSets:
											addSets.append(value)
											self.add_node(value,dfa.nodes[key].isFinal,dfa.nodes[key].isBackOff,dfa.nodes[k
											ey].tag)
											addEdges = []
											for edge in dfa.edges:
											tup = (dicts[edge.fromNodeId],dicts[edge.toNodeIds],edge.tag)
											if tup not in addEdges:
											self.add_edges(dicts[edge.fromNodeId],dicts[edge.toNodeIds],edge.tag)
											addEdges.append(tup)
											# print('addSets: ' + str(addSets))

}
