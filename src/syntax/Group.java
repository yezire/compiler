package syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Group {

 static int idPool = 0;
 private int id;//状态编号
 private final Set<Item> itemSet;//项目集合
 private final String label;
 public static final Map<String, Group> allGroups = new HashMap<>();//记录所有规范项目族
 public  Production reProduction;//归约产生式，只有归约group才有


 private Group(int id, Set<Item> itemSet, String label) {
  this.id = id;
  this.itemSet = itemSet;
  this.label = label;
 }

 public static Group create(Set<Item> items) {
  StringBuilder sb = new StringBuilder();
  // 这里必须进行排序，保证不同Set 集合有相同顺序，这样才能比较两个项目集
  items.stream().sorted().forEach(item -> sb.append(item.getLabel()).append(","));
  if (sb.length() != 0) {
   sb.deleteCharAt(sb.length() - 1);
  }
  String label = sb.toString();
  /**
   * 如果 label 相同，说明是同一个项目集，那么返回之前创建的；
   * 保证相同 items 的是返回的是同一个 ProductionItemSet
   */
  Group itemSet = allGroups.get(label);
  if (itemSet == null) {
   itemSet = new Group(idPool++, items, sb.toString());
   allGroups.put(label, itemSet);
  }
  return itemSet;
 }

 public Set<Item> getGroup() {
  return itemSet;
 }

 public int getId() {
  return id;
 }


 public  boolean isReductionGroup() {
  for (Item item : itemSet) {
   if (item.getLabel().indexOf("•") != item.getLabel().size() - 1) {
    return false;
   }
  }
  reProduction=new ArrayList<>(itemSet).get(0).getProduction();
  return true;
 }



 @Override
 public boolean equals(Object o) {
  if (this == o)
   return true;
  if (o == null || getClass() != o.getClass())
   return false;
  Group that = (Group) o;
  return label.equals(that.label);
 }

 @Override
 public int hashCode() {
  return Objects.hash(label);
 }

 @Override
 public String toString() {
  return id + "==" + label;
 }
}
