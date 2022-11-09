package syntax.syntaxLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import syntax.Grammar;
import syntax.Production;

public class LL1 {

  //k:left v:candidate productions 拓展之后的也在里面，Grammar.productions是原始产生式
  private static Map<String, List<Production>> allProductions = new HashMap<>();


  public void initProductions() {
    //填入原始产生式
    allProductions = Grammar.getProductionsByLeft();
//同时消除左递归
    for (Entry<String, List<Production>> e : allProductions.entrySet()) {
      for (Production p : e.getValue()) {
        if (isLeftRecursion(p)) {
          System.out.println("存在直接左递归");
          return;
        }
      }
    }
    System.out.println("不存在直接左递归");
  }

  public boolean isLeftRecursion(Production production) {
    //判断当前产生式是否直接左递归
    return production.getLeft().equals(production.getRight().get(0));
  }

  public void removeLeftRecursion() {
    //消除当前产生式的直接左递归
  }

  public void extract() {
    //提取公共左因子
    /**
     * A→δβ1 |δβ2 | ... |δβn |γ1 |γ2 | ... |γm(其中，每个γ不以δ开头
     * 改写为
     * A→δA’ |γ1 |γ2 | ... |γm
     * A’→β1 |β2 | ... |βn
     */
    //会产生一些新的产生式
    for (Entry<String, List<Production>> e : allProductions.entrySet()) {
      hasCommonFactor(e.getValue());
    }
  }

  /**
   * 判断是否存在公共因子
   *
   * @param candidates 候选产生式
   * @return boolean
   */
  public boolean hasCommonFactor(List<Production> candidates) {
    //只需判断第一个是否存在相同的
    for (int i = 0; i < candidates.size(); i++) {
      for (int j = i + 1; j < candidates.size(); j++) {
        String ci = candidates.get(i).getRight().get(0);
        String cj = candidates.get(j).getRight().get(0);
        if (ci.equals(cj) && !ci.equals("$")) {
          System.out.println("存在");
          return true;
        }
      }
    }
    System.out.println("不存在");
    return false;
  }

  /**
   * 判断是不是LL1文法 无直接左递归 候选首符集两两不相交 若存在某个候选首符集包含ε的非终结符的first集和follow集不相交
   *
   * @return boolean
   */
  public boolean isLL1() {
    Map<String, Set<String>> firsts = Grammar.getFirst();
    //判断首符号集是否相交
    for (Entry<String, List<Production>> e : allProductions.entrySet()) {
      List<Set<String>> lists = new ArrayList<>();
      for (Production p : e.getValue()) {
        if (p.getRight().get(0).equals("$")) {
          continue;
        }
        lists.add(firsts.get(p.getRight().get(0)));
      }

      for (int i = 0; i < lists.size(); i++) {
        for (int j = i + 1; j < lists.size(); j++) {
          Set<String> ci = lists.get(i);
          Set<String> cj = lists.get(j);
          if (!Collections.disjoint(ci,cj)) {
            System.out.println("首符集存在交集");
            return false;
          }
        }
      }
    }
    System.out.println("首符集不存在交集");
    return true;
  }


  public void createTable() {
    //双层循环，遍历map里面的完整版产生式，遍历每个非终结符
    //row:Vn col:Vt grid:production
    /*假设要用非终结符A进行匹配，面临的输入符号为a，A的所有产生式为 A→α1 |α2 | ... |αn
     • 若a∈FIRST(αi)，则指派αi去执行匹配任务。 • 若a不属于任何一个候选首字符集，则:
     • 若ε属于某个FIRST(αi)，且a∈FOLLOW(A)，则让A与ε自动匹配; • 否则，a的出现是一种语法错误。
     */
  }

  public void analysis(List<String> input) {
    //预测分析程序的总控程序在任何时候都是按STACK栈顶符号X和当前的输入符号a行事的。
    /**
     * 若X = a = ‘#’，则宣布分析成功，停止分析过程。
     * 若X = a ≠‘#’，则把X从STACK栈顶弹出，让a指向下一个输入符号。
     *
     *若X是一个非终结符，则查看分析表M。 若M[X,a]中存放着关于X的一个产生式，
     * 那么，先把X弹出STACK栈顶，然后把产生式的右部符号串按反序一一推进STACK栈(若右部符号为ε，则意味着不推任何符号进栈)。
     *
     * 若M[X,a]中存放着“出错标志”，则调用出错 诊断程序ERROR。
     */
    int step = 0;//步骤
    Stack<String> varStack = new Stack<String>();//分析栈
    //todo:push start
    input.add("#");

  }

  public void error() {
    System.out.println("error");
  }

}