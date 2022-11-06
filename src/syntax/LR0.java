package syntax;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import lexical_analysis.Token;

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
          Item newItem = Item.createProduction(p);
          if (!result.contains(newItem)) {
            result.add(newItem);
            // 添加到栈中，新项目需要查找它的相同项目
            stack.push(newItem);
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
    Set<Item> nextItems = new HashSet<>();
    for (Item item : current.getGroup()) {
      /**
       * 项目pos点的符号与 symbol 是否相同
       * 即项目 a•Bb 匹配 符号B
       * 后继项目就是 aB•b
       */
      if (item.getPosVar().equals(var)) {
        // 将后继项目添加到nextItems
        Item nextItem = Item.nextByItem(item);
        List<String> label = Item.nextByItem(item).getLabel();
        nextItems.add(nextItem);
      }
    }
    // 如果nextItems为空，说明当前项目集没有 符号symbol的后继项目
    if (nextItems.isEmpty()) {
      return null;
    }
    // 创建后继项目集
    Group gotoItemSet = Group.create(closures(nextItems));
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
      Group itemSet = stack.pop();
      // 遍历所有符号
      for (String var : allVars) {
        // 当前项目集遇到字符var得到后继项目集
        Group gotoItemSet = gotoState(itemSet, var);
        /**
         * 如果得到的后继项目集gotoItemSet 在allItemSetList中没有，
         * 那么代表它是全新的，那么添加进行。
         */
        if (gotoItemSet != null && !allGroupsList.contains(gotoItemSet)) {
          allGroupsList.add(gotoItemSet);
          // 新的项目集要添加到栈中，进行遍历
          stack.push(gotoItemSet);
        }
      }
    }
    return allGroupsList;
  }

  public static void createTable() {
//求出所有项目集
    allGroups = getAllGroups();

    for (Group group : allGroups) {

      for (String var : allVars) {
        //终结符 action表
        if (Grammar.getTerminals().contains(var)) {
          //接受 acc
          if (var.equals("#") && group.isAccGroup()) {
            addToDoubleMap(table, group, var, Action.createAcc());
            continue;
          }
         //  归约 r
          else if (group.isReductionGroup()) {
            Production production = group.reProduction;
            addToDoubleMap(table, group, var, Action.createR(production));
          }
//          for(Item item: group.collectReductionItems()){
//            addToDoubleMap(table, group, var, Action.createR(item.getProduction()));
//          }

          // 移进 s
         else if (GOTO_TABLE.get(group).containsKey(var)) {
            Group next = GOTO_TABLE.get(group).get(var);
            addToDoubleMap(table, group, var, Action.createS(next));
          } else {
            addToDoubleMap(table, group, var, Action.createError());
          }
        }
        //goto表
        if (Grammar.getNonTerminals().contains(var)) {
          if (GOTO_TABLE.containsKey(group)) {
            Group next = GOTO_TABLE.get(group).get(var);
            if (next != null) {
              addToDoubleMap(table, group, var, Action.createGoto(next));
            } else {
              addToDoubleMap(table, group, var, Action.createError());
            }
          } else {
            addToDoubleMap(table, group, var, Action.createError());
          }
        }
      }
    }
  }

  public static void showTable() {
//    static Map<Group, Map<String, Action>> table = new HashMap<>();//分析表 K :状态 行 V：{符号 列, action 格子 }
    //print表头
    StringBuilder sb = new StringBuilder();
    sb.append("=========================TABLE=================\n");
    sb.append("state").append("\t\t");
    for (String var : Grammar.getTerminals()) {
      sb.append(var).append("\t\t");
    }
    for (String var : Grammar.getNonTerminals()) {
      sb.append(var).append("\t\t");
    }
    sb.append("\n");
    for (Entry<Group, Map<String, Action>> row : table.entrySet()) {
      sb.append(row.getKey().getId()).append("\t\t\t");
      for (Entry<String, Action> grid : row.getValue().entrySet()) {
        sb.append(grid.getValue()).append("\t");
      }
      sb.append("\n");
    }
    System.out.println(sb.toString());
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
      // 当前状态栈栈顶状态
      Group currentStatus = stateStack.peek();
      //当前面临符号
      //  String currentInput = inputTokens.get(pos);
      // 获取当前栈顶状态遇到输入符号得到的 action
      Action action = table.get(currentStatus).get(inputTokens.get(pos));
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
      }
      else if (action.isError()) {
        System.out.println("!error!");
        return;
      }
      //移进
      else if (action.isS()) {
        stateStack.push(action.getGroup());
        opStack.push(inputTokens.get(pos));
        pos++;
        showStep(step++, opStack.peek(), inputTokens.get(pos), "move");
      } else if (action.isR()) {
        // 归约操作的产生式
        Production production = action.getProduction();
        int size = production.getRight().size();
        // 记录状态栈弹出的状态
        List<Integer> popStates = new ArrayList<>();
        //记录弹出的符号
        List<String> popVars = new ArrayList<>();
        // 根据产生式右部的字符数量，从状态栈和字符栈中弹出对应数量的元素
        for (int index = 0; index < size; index++) {
          if(production.getRight().get(index).equals("$"))continue;
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
//    String actionName;
//    if (action.isAcc()) {
//      actionName = "accept";
//    } else if (action.isS()) {
//      actionName = "move";
//    } else if (action.isR()) {
//      actionName = "reduction";
//    } else {
//      actionName = "error";
//    }
    System.out.println(step + "\t" + opVar + "#" + inputVar + "\t" + info);
  }

}
