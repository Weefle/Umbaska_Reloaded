package uk.co.umbaska.ImageManager;





public enum ImageChar
{
  BLOCK('\u2013', Integer.valueOf(1)),
  DARK_SHADE('\u201c', Integer.valueOf(2)),
  MEDIUM_SHADE('\u2019', Integer.valueOf(3)),
  LIGHT_SHADE('\u2018', Integer.valueOf(4));
  
  private char c;
  private Integer id;
  
  private ImageChar(char c, Integer i) { this.c = c;
    this.id = i;
  }
  
  public char getChar() {
    return this.c;
  }
  
  public Integer getId() {
    return this.id;
  }
  
  public ImageChar getCharById(Integer i) {
    if ((i.intValue() > values().length) || (i.intValue() == 0)) {
      return defaultChar();
    }
    for (ImageChar c : values()) {
      if (c.getId() == i) {
        return c;
      }
    }
    
    return defaultChar();
  }
  
  public ImageChar defaultChar() {
    return BLOCK;
  }
}
