package syntax;

import java.util.List;
import java.util.Objects;

public class Item {
 static int idPool=0;
 private int seq;//项目编号
 List<List<Var>> groups;

 public Item(){
  //this.productions = productions;
  seq=idPool++;
 }

 public void setGroups(List<List<Var>> groups){
  this.groups = groups;
 }
 @Override
 public boolean equals(Object o) {
  if (this == o) {
   return true;
  }
  if (o == null || getClass() != o.getClass()) {
   return false;
  }
  Item item = (Item) o;
  return seq == item.seq;
 }

 @Override
 public int hashCode() {
  return Objects.hash(seq);
 }
}
