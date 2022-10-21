package syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item implements Comparable<Item> {
  /**
   * 表示当前pos位置对应的字符。
   * 例:产生式 S -> bBB
   * 它有四个项目         •bBB b•BB  bB•B  bBB•
   * pos 位置是            0    1     2    3
   * 对应的posSymbol是     b    B     B    Symbol.END
   * 对应的label是        •bBB  b•BB  bB•B   bBB•
   *
   */
  public static final String ITEM_SYMBOL = "•";//圆点
 // public static final String ITEM_SYMBOL = "•";//圆点
  private final Production production;//对应产生式
  private final int pos;//圆点位置
  private static final String END=";";
  private  final String posVar;
  private final List<String> label;

  private Item(Production production, int pos, String posVar, List<String> label) {
    this.production = production;
    this.pos = pos;
    this.posVar = posVar;
    this.label = label;
  }

  /**
   * 创建产生式对应的移进项目
   * @param production
   * @return
   */
  public static Item createProduction(Production production) {
    return create(production, 0);
  }

  /**
   * 创建当前项目 item 的后继项目
   * @param item
   * @return
   */
  public static Item nextByItem(Item item) {
    return create(item.production, item.pos + 1);
  }

  private static Item create(Production production, int pos) {
    List<String>list = new ArrayList<>();
    list.add(production.getLeft());
    String posSymbol = null;
    for (int index = 0; index < production.getRights().size(); index++) {
      String symbol = production.getRights().get(index);
      if (index == pos) {
        posSymbol = symbol;
        list.add(ITEM_SYMBOL);
      }
      list.add(symbol);
    }
    if (pos == production.getRights().size()) {
      posSymbol = END;
      list.add(ITEM_SYMBOL);
    }
    return new Item(production, pos, posSymbol, list);
  }

  public Production getProduction() {
    return production;
  }

  public int getPos() {
    return pos;
  }

  public String getPosVar() {
    return posVar;
  }

  public List<String> getLabel() {
    return label;
  }

  @Override
  public int hashCode() {
    return Objects.hash(label);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item that = (Item) o;
    return this.toString().equals(that.toString());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(String l:label){
      sb.append(l);
    }
    return sb.toString();
  }


  @Override
  public int compareTo(Item o) {
    return this.toString().compareTo(o.toString());
  }
}
