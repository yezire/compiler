package syntax.syntaxLR;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import syntax.Grammar;
import syntax.Production;

/**
 * lr0
 *
 * @author yezizhi
 * @date 2022/10/21
 */
public class LR0 {

  public static Map<Group, Map<String, Action>> getTable() {
    return table;
  }

  private static final Map<Group, Map<String, Group>> GOTO_TABLE = new HashMap<>();// current state + var ->next state
  static Map<Group, Map<String, Action>> table = new HashMap<>();//分析表 K :状态 行 V：{符号 列, action 格子 }
  static Group startGroup;
  static Production startProduction;
  static List<Group> allGroups;
  static Set<String> allVars = new HashSet<>();


  /**
   * 闭包
   *
   * @param items 项目
   * @return {@link Set}<{@link Item}>
   */
  private static Set<Item> closures(Set<Item> items) {
    Set<Item> result = new HashSet<Item>();
    Stack<Item> stack = new Stack<Item>();
    stack.addAll(items);
    result.addAll(items);
    while (!stack.isEmpty()) {
      Item item = stack.pop();
      //圆点后面是终结符，不会继续拓展
      if (Grammar.getTerminals().contains(item.getPosVar()) || item.getPosVar().equals("END")) {
        continue;
      } else {
        List<Production> productions = Grammar.getProductionsByLeft().get(item.getPosVar());
        for (Production p : productions) {
          //创建产生式对应的项目
          if (!p.isEmptyRight()) {
            Item newItem = Item.createProduction(p);
            if (!result.contains(newItem)) {
              result.add(newItem);
              // 添加到栈中，新项目需要查找它的相同项目
              stack.push(newItem);
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * goto函数
   *
   * @param current 当前状态
   * @param var     识别字符
   * @return {@link Group} 下一个状态
   */
  private static Group gotoState(Group current, String var) {
    //先查缓存
    if (GOTO_TABLE.containsKey(current)) {
      Map<String, Group> map = GOTO_TABLE.get(current);
      if (map.containsKey(var)) {
        return map.get(var);
      }
    }
    // 当前项目集 group 遇到符号 var 的后继项目集合
    Set<Item> nextGroup = new HashSet<>();
    for (Item item : current.getGroup()) {
      /**
       * 项目pos点的符号与 symbol 是否相同
       * 即项目 a•Bb 匹配 符号B
       * 后继项目就是 aB•b
       */
      if (item.getPosVar().equals(var)) {
        // 将后继项目添加到nextItems
        Item nextItem = Item.nextByItem(item);
//        if (!item.getPosVar().equals("END") && Grammar.getFirst().get(item.getPosVar())
//            .contains("$")) {
//          Item removeEpsilon = Item.removeEpsilon(item);
//          if(removeEpsilon!=null)
//          nextGroup.add(removeEpsilon);
//        }
        nextGroup.add(nextItem);
      }
    }
    // 如果nextItems为空，说明当前项目集没有 符号symbol的后继项目
    if (nextGroup.isEmpty()) {
      return null;
    }
    // 创建后继项目集
    Group gotoItemSet = Group.create(closures(nextGroup));
    // 存放到缓存中
    addToDoubleMap(GOTO_TABLE, current, var, gotoItemSet);

    return gotoItemSet;
  }


  public static <OuterK, InnerK, InnerV> InnerV addToDoubleMap(
      Map<OuterK, Map<InnerK, InnerV>> outerMap, OuterK outerK, InnerK innerK, InnerV innerV) {
    Map<InnerK, InnerV> innerMap = outerMap.get(outerK);
    if (innerMap == null) {
      innerMap = new HashMap<>();
      outerMap.put(outerK, innerMap);
    }
    return innerMap.put(innerK, innerV);
  }

  private static List<Group> getAllGroups() {
    allVars.addAll(Grammar.getTerminals());
    allVars.addAll(Grammar.getNonTerminals());
    //添加结束符号
    allVars.add("#");

    //求解I0
    startProduction = Grammar.getProductions().get(0);
    Item startItem = Item.createProduction(startProduction);
    Set<Item> set = new HashSet<>();
    set.add(startItem);
    startGroup = Group.create(closures(set));
    // 当前文法所有的项目集
    List<Group> allGroupsList = new ArrayList<>();
    allGroupsList.add(startGroup);
    // 通过栈来遍历所有的项目集，得到项目集对所有符号产生的新的项目集
    Stack<Group> stack = new Stack<>();
    stack.push(startGroup);
    while (!stack.isEmpty()) {
      Group group = stack.pop();
      // 遍历所有符号
      for (String var : allVars) {
        // 当前项目集遇到字符var得到后继项目集
        if (var.equals("$")) {
          continue;
        }
        Group nextGroup = gotoState(group, var);
        /**
         * 如果得到的后继项目集group 在allGroupList中没有，
         * 那么代表它是全新的，那么添加进行。
         */
        if (nextGroup != null && !allGroupsList.contains(nextGroup)) {
          allGroupsList.add(nextGroup);
          // 新的项目集要添加到栈中，进行遍历
          stack.push(nextGroup);
        }
      }
    }
    return allGroupsList;
  }

  public static void completeGroups() {
    for (Group group : allGroups) {
      Set<Item> oris = new HashSet<>(group.getItemSet());
      for (Item item : oris) {
        // a-> b · c d , c->$
        //a->b·d 加入group
        if (!item.getPosVar().equals("END") && Grammar.getFirst().get(item.getPosVar())
            .contains("$")) {
          Item removeEpsilon = Item.removeEpsilon(item);
          if (removeEpsilon != null) {
            group.getItemSet().add(removeEpsilon);
          }
        }
      }
    }
  }


  public static void createTable() {
//求出所有项目集
    allGroups = getAllGroups();
    completeGroups();
    allGroups=getAllGroups();
    for (Group group : allGroups) {
      //acc group
      if (group.getId() == 120) {
        System.out.println("here");
      }
      if (group.isAccGroup()) {
        addToDoubleMap(table, group, "#", Action.createAcc());
        continue;
      }
      Map<String, Action> row = new HashMap<String, Action>();
      table.put(group, row);
      for (Item item : group.getItemSet()) {
        String var = item.getPosVar();
        //goto表
        if (Grammar.getNonTerminals().contains(var)) {
          addToDoubleMap(table, group, var, Action.createGoto(gotoState(group, var)));
        }
        //action表
        else {
          //s
          if (!var.equals("END")) {
            Group nextGroup = gotoState(group, var);
            addToDoubleMap(table, group, var, Action.createS(nextGroup));
            if (!allGroups.contains(nextGroup) ){
              allGroups.add(nextGroup);
            }
          }
          //r
          else {
            Production production = item.getProduction();
            for (String vt : Grammar.getFollow().get(production.getLeft())) {
              //   if(table.get(group).get(vt)==null){
              addToDoubleMap(table, group, vt, Action.createR(production));
              // }
            }
          }
        }
      }
    }

  }


  public static <OuterK, InnerK, InnerV> void addToDoubleMapPrintConflict(
      Map<OuterK, Map<InnerK, InnerV>> outerMap, OuterK outerK, InnerK innerK, InnerV innerV,
      String format) {
    InnerV old = addToDoubleMap(outerMap, outerK, innerK, innerV);
    if (old != null && !old.equals(innerV)) {
      System.out.println(String.format(format, old, innerV));
    }
  }

  public static void match(List<String> inputTokens) {
    //状态栈（状态==项目集）
    Stack<Group> stateStack = new Stack<>();
    //放入开始状态
    stateStack.push(startGroup);
    //符号栈
    Stack<String> opStack = new Stack<>();
    //放入结束符号
    opStack.push("#");

    //当前步骤
    int step = 1;
    //当前面临符号的位置
    int pos = 0;

    while (true) {

      // 获取当前栈顶状态遇到输入符号得到的 action
      Action action = table.get(stateStack.peek()).get(inputTokens.get(pos));
      if (action == null) {
        System.out.println("ssss");
      }
      if (action.isAcc()) {
        // 当输入字符是最后一个字符，表示匹配成功。否则输入字符还没有匹配完成，匹配失败
        if (pos == inputTokens.size() - 1) {
          showStep(step++, opStack.peek(), inputTokens.get(pos), "accept");
        } else {
          showStep(step++, opStack.peek(), inputTokens.get(pos), "error");
        }
        return;
      } else if (action.isError()) {
        System.out.println("!error!");
        return;
      }
      //移进
      else if (action.isS()) {
        stateStack.push(action.getGroup());
        opStack.push(inputTokens.get(pos));
        pos++;
        showStep(step++, opStack.peek(), inputTokens.get(pos), "move");
      }
      //规约
      else if (action.isR()) {
        // 归约操作的产生式
        Production production = action.getProduction();
        int size = production.getRight().size();
        // 记录状态栈弹出的状态
        List<Integer> popStates = new ArrayList<>();
        //记录弹出的符号
        List<String> popVars = new ArrayList<>();
        // 根据产生式右部的字符数量，从状态栈和字符栈中弹出对应数量的元素
        for (int index = 0; index < size; index++) {
          if (production.getRight().get(index).equals("$")) {
            continue;
          }
          popStates.add(stateStack.pop().getId());
          popVars.add(opStack.pop());
        }
        // 将产生式左部添加到字符栈
        opStack.push(production.getLeft());
        // 获取这个字符在 table 中对应的状态，添加到状态栈
        Action gotoAction = table.get(stateStack.peek()).get(opStack.peek());
        if (gotoAction.isGoto()) {
          Group gotoState = gotoAction.getGroup();
          stateStack.push(gotoState);
          showStep(step++, opStack.peek(), inputTokens.get(pos), "reduction");
        } else {
          System.out.println("something is wrong");
        }
      }
    }
  }

  public static void showStep(int step, String opVar, String inputVar, String info) {
    System.out.println(step + "\t" + opVar + "#" + inputVar + "\t" + info);
  }

}
