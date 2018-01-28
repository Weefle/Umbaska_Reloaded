package uk.co.umbaska.Enums;

public enum Operation {
  ADD_NUMBER(0), 
  MULTIPLY_PERCENTAGE(1), 
  ADD_PERCENTAGE(2);
  
  private int id;
  
  private Operation(int id) { this.id = id; }
  
  public int getId()
  {
    return this.id;
  }
  
  public static Operation fromId(int id)
  {
    for (Operation op : ) {
      if (op.getId() == id) {
        return op;
      }
    }
    throw new IllegalArgumentException("Corrupt operation ID " + id + " detected.");
  }
}
