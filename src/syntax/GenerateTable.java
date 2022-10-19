package syntax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class GenerateTable {
  //装入first集map follow集map
private Generate preprocess=new Generate();
private final Set<Item>items=new HashSet<>();//项目集
  private  final Var dot=new Var(".",false);//没有type

//    for(int i=0;i<G.size();i++) {
//    String left = G.get(i).left;
//    String right=G.get(i).right;
//    for(int j=0;j<=right.length();j++) {
//      StringBuilder sb = new StringBuilder(right);
//      sb.insert(j,CH);
//      String m = sb.toString();
//      item m1 = new item();
//      m1.left=left;
//      m1.right=m;
//      items.add(m1);
//    }
//  }


  //将A->dot B A-> dot C找出来
//  public void findProductionsByLeft() {
//    //map : A对应的所有产生式 并且加上点
//    Map<Integer, List<Var>> productions=preprocess.getProductions();
//for(List<Var>list:productions.values()){
//  Item item=new Item();
//  List<List<Var>>groups=new ArrayList<>();
//  Var left=list.get(0);
//
//  List<Var>right=list.subList(1,list.size());
//  for (int i = 0; i < right.size(); i++) {
//    List<Var>line=new ArrayList<>();
//    right.add(i,dot);
//    line.add(left);
//    line.addAll(right);
//    groups.add(line);
//  }
//  item.setGroups(groups);
//}
//
//  }


  public Item transfer(String var,Item item){
    //transfer input var 点前进并判断要不要拓展
    //expand:find A closure
    return null;
  }

public List<List<Var>> getClosure(String var){
    //递归 找到闭包
    return null;
}

public void generateItems(){
    //构造规范族
  // add item 0
}



public boolean IsIntersection(List<String> l, List<String> r) {
    // 判断有无交集 IsIntersection
    return false;
  }
}

