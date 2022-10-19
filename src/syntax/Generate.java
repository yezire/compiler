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
import java.util.Set;

/*  preprocess
     * 输入文法，将文法中"|"展开，存放在map中，从1开始，key:产生式左边，value:list 产生式右
     * 在0新增一条，形成拓广文法
     * 标记终结符，非终结符

     * 求first集:非终结符，终结符就是它本身
     * first(A)={a| A ==>+ B}
     * - if (A ==>+ ε) then add ε to first(A)
     * - 有产生式A ==> αβ if (α ∈ VT) then add α to first(A)
     * - 有产生式A ==> Bβ add first(B) to first(A)

 */

public class Generate {
  public List<Production>productions=new ArrayList<>();
 // private static Map<Integer, List<Var>> productions = new HashMap<>();
  private static Set<String> terminals = new HashSet<>();
  private static Set<String> nonTerminals = new HashSet<>();
  private static Map<String,Set<String>>  first = new HashMap<>();
  private static Map<String,Set<String>>  follow = new HashMap<>();

  public void inputGrammar(){
    String path="/Users/yezizhi/Desktop/compiler/src/grammar.txt";
    try {
      String ori=readFromTxt(path);
      for(String line:ori.split("\n")){
        String left=line.split(" -> ")[0];
        List<String>right=new ArrayList<>(Arrays.asList(line.split(" -> ")[1].split(" ")));;
        Production p =new Production(left,right);
        productions.add(p);
        nonTerminals.add(left);
        terminals.addAll(right);
      }
      terminals.removeIf(s -> nonTerminals.contains(s));


      System.out.println("================Terminal===============");
      for(String s:terminals){
        System.out.println(s);
      }
      System.out.println("================Non-Terminal===============");
      for(String s:nonTerminals){
        System.out.println(s);
      }



    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void generateFirstCollection(){

  }

  public void generateFollowCollection(){

  }

//  public Map<Integer, List<Var>> getProductions(){return productions;}
//  public Set<Var> getTerminals(){return terminalSet;}
//  public Set<Var>getNonTerminals(){return nonTerminalSet;}
//  public Map<String,Set<Var>> getFirst(){return  first;}
//  public Map<String,Set<Var>> getFollow(){return  follow;}

  private String readFromTxt(String filename) throws Exception {
    Reader reader = null;
    try {
      StringBuffer buf = new StringBuffer();
      char[] chars = new char[1024];
      // InputStream in=new FileInputStream(filename);

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

  private void showMap( Map<Integer, List<Var>> map){
    for (Map.Entry<Integer, List<Var>> entry : map.entrySet()){
      System.out.print(entry.getKey()+"\t:");
      for (Var v: entry.getValue()){
        System.out.print(v.toString()+" ");
      }
      System.out.println();
    }

  }
}
