package syntax;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*  preprocess
 * 输入文法，将文法中"|"展开，存放在map中，从1开始，key:产生式左边，value:list 产生式右
 * 在0新增一条，形成拓广文法
 * 标记终结符，非终结符
 */

public class Grammar {

  private static List<Production> productions = new ArrayList<>();
  private static Map<String, List<Production>> productionsByLeft = new HashMap<>();
  private static Set<String> terminals = new HashSet<>();
  private static Set<String> nonTerminals = new HashSet<>();
  private static Map<String, Set<String>> first = new HashMap<>();
  private static Map<String, Set<String>> follow = new HashMap<>();

  public static String getStart() {
    return start;
  }

  private  static  String start;


  /**
   * 输入语法 生成终结符、非终结符 生成first集合，follow集合
   */
  public void inputGrammar( String path) {
    try {
      String ori = readFromTxt(path);
      for (String line : ori.split("\n")) {
        String left = line.split(" -> ")[0];
        if(start==null)start=left;
        List<String> right = new ArrayList<>(Arrays.asList(line.split(" -> ")[1].split(" ")));
        Production p = new Production(left, right);
        productions.add(p);
        nonTerminals.add(left);
        terminals.addAll(right);
      }
      terminals.removeIf(s -> nonTerminals.contains(s));
      //remove $
      terminals.remove("$");
      //add #
      terminals.add("#");
      generateProductionsByLeft();
      generateFirstCollection();
      //todo
      //generateFollowCollection();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 求first集:非终结符，终结符就是它本身 first(A)={a| A ==>+ B} - if (A ==>+ ε) then add ε to first(A) - 有产生式A ==>
   * αβ if (α ∈ VT) then add α to first(A) - 有产生式A ==> Bβ add first(B) to first(A)
   */
  public void generateFirstCollection() {
    //所有终结符的first集合就是自己
    for (String ter : terminals) {
      Set<String> set = new HashSet<>();
      set.add(ter);
      first.put(ter, set);
    }
    //非终结符注册
    for (String non : nonTerminals) {
      Set<String> set = new HashSet<>();
      first.put(non, set);
    }

    while (true) {
      Map<String, Set<String>> firstClone = new HashMap<>();
      for(Entry<String,Set<String>>entry:first.entrySet()){
        Set<String>set=new HashSet<>();
        for(String s:entry.getValue() ){
          set.add(s);
        }
        firstClone.put(entry.getKey(),set);
      }

      for (Production p : productions) {

        String rightHead = p.getRight().get(0);
        // * - 有产生式A ==> αβ if (α ∈ VT) then add α to first(A)
        if (terminals.contains(rightHead)) {
          Set<String> set = firstClone.get(p.getLeft());
          set.add(rightHead);
          firstClone.replace(p.getLeft(), set);
        }
        //   * - 有产生式A ==> Bβ add first(B) to first(A)
        if (nonTerminals.contains(rightHead) && firstClone.containsKey(rightHead)) {
          Set<String> set = firstClone.get(p.getLeft());
          set.addAll(firstClone.get(rightHead));
          int index=0;
          //A->BC 若first(B)包含空ε，需要继续求first(C )加入first(A)中。
          // 若first（c）仍旧包含空ε，将空字符ε加入first(A)
          while(firstClone.get(p.getRight().get(index)).contains("$")){
            index++;
            if(index==p.getRight().size())break;
            if(firstClone.get(p.getRight().get(index)).size()!=0){
              set.addAll(firstClone.get(p.getRight().get(index)));
            }
          }
          firstClone.replace(p.getLeft(), set);
        }
      }
      //判断有无改变
      if (isSameMap(first, firstClone)) {
        return;
      } else {
        first = firstClone;
      }
    }
  }

  /**
   * 判断两个map是否相同
   *
   * @param a mapA
   * @param b mapB
   * @return boolean
   */
  private boolean isSameMap(Map<String, Set<String>> a, Map<String, Set<String>> b) {
    for (Entry<String, Set<String>> entry : a.entrySet()) {
      Set<String> thisSet = entry.getValue();
      Set<String> that = b.get(entry.getKey());
      if (!(thisSet.containsAll(that) && that.containsAll(thisSet))) {
        return false;
      }
    }
    return true;
  }


//  public void generateFollowCollection() {
////开始符号的follow集加入#
////若有A->αBβ,就将first(β)\ε 放入follow(B)
////若有A->αB，就将follow(A)放入follow(B)
//    Set<String> startSet = new HashSet<>();
//    startSet.add("#");
//    follow.put(start,startSet);
//   // 只有非终结符才有follow
//    //非终结符注册
//    for (String non : nonTerminals) {
//      Set<String> set = new HashSet<>();
//      follow.put(non, set);
//    }
//
//    while (true) {
//      Map<String, Set<String>> followClone = new HashMap<>();
//      //clone
//      for(Entry<String,Set<String>>entry:follow.entrySet()){
//        Set<String>set=new HashSet<>();
//        for(String s:entry.getValue() ){
//          set.add(s);
//        }
//        followClone.put(entry.getKey(),set);
//      }
//
//      for (Production p : productions) {
//        String rightHead = p.getRights().get(0);
//        //若有A->αBβ,就将first(β)\ε 放入follow(B)
//        if (terminals.contains(rightHead)) {
//          Set<String> set = followClone.get(p.getLeft());
//          set.add(rightHead);
//          followClone.replace(p.getLeft(), set);
//        }
//        //   * - 有产生式A ==> Bβ add follow(B) to follow(A)
//        if (nonTerminals.contains(rightHead) && followClone.containsKey(rightHead)) {
//          Set<String> set = followClone.get(p.getLeft());
//          set.addAll(followClone.get(rightHead));
//          int index=0;
//          //A->BC 若follow(B)包含空ε，需要继续求follow(C )加入follow(A)中。
//          // 若follow（c）仍旧包含空ε，将空字符ε加入follow(A)
//          while(followClone.get(p.getRights().get(index)).contains("$")){
//            index++;
//            if(index==p.getRights().size())break;
//            if(followClone.get(p.getRights().get(index)).size()!=0){
//              set.addAll(followClone.get(p.getRights().get(index)));
//            }
//          }
//          followClone.replace(p.getLeft(), set);
//        }
//      }
//      //判断有无改变
//      if (isSameMap(follow, followClone)) {
//        return;
//      } else {
//        follow = followClone;
//      }
//    }
//
//  }

  /**
   * K: 某个非终结符 V:同一非终结符的所有产生式组成的set
   */
  public void generateProductionsByLeft() {
    for (Production p : productions) {
      if (productionsByLeft.containsKey(p.getLeft())) {
        List<Production> list = productionsByLeft.get(p.getLeft());
        list.add(p);
        productionsByLeft.replace(p.getLeft(), list);
      } else {
        List<Production> list = new ArrayList<>();
        list.add(p);
        productionsByLeft.put(p.getLeft(), list);
      }
    }
  }


  public static Set<String> getTerminals() {
    return terminals;
  }

  public static Set<String> getNonTerminals() {
    return nonTerminals;
  }

  public static Map<String, Set<String>> getFirst() {
    return first;
  }

  public static List<Production> getProductions() {
    return productions;
  }

  public static Map<String, List<Production>> getProductionsByLeft() {
    return productionsByLeft;
  }

  public static Map<String, Set<String>> getFollow() {
    return follow;
  }

  private String readFromTxt(String filename) throws Exception {
    Reader reader = null;
    try {
      StringBuffer buf = new StringBuffer();
      char[] chars = new char[1024];
      reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
      int readed = reader.read(chars);
      while (readed != -1) {
        buf.append(chars, 0, readed);
        readed = reader.read(chars);
      }
      return buf.toString();
    } finally {
      close(reader);
    }
  }

  /**
   * 关闭输入输入流
   *
   * @param inout
   */
  private void close(Closeable inout) {
    if (inout != null) {
      try {
        inout.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
