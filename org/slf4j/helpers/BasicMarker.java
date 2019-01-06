package org.slf4j.helpers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.slf4j.Marker;

public class BasicMarker
  implements Marker
{
  private static final long serialVersionUID = 1803952589649545191L;
  private final String name;
  private List<Marker> referenceList;
  
  BasicMarker(String name)
  {
    if (name == null) {
      throw new IllegalArgumentException("A marker name cannot be null");
    }
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public synchronized void add(Marker reference)
  {
    if (reference == null) {
      throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
    }
    if (contains(reference)) {
      return;
    }
    if (reference.contains(this)) {
      return;
    }
    if (this.referenceList == null) {
      this.referenceList = new Vector();
    }
    this.referenceList.add(reference);
  }
  
  public synchronized boolean hasReferences()
  {
    return (this.referenceList != null) && (this.referenceList.size() > 0);
  }
  
  public boolean hasChildren()
  {
    return hasReferences();
  }
  
  public synchronized Iterator<Marker> iterator()
  {
    if (this.referenceList != null) {
      return this.referenceList.iterator();
    }
    List<Marker> emptyList = Collections.emptyList();
    return emptyList.iterator();
  }
  
  public synchronized boolean remove(Marker referenceToRemove)
  {
    if (this.referenceList == null) {
      return false;
    }
    int size = this.referenceList.size();
    for (int i = 0; i < size; i++)
    {
      Marker m = (Marker)this.referenceList.get(i);
      if (referenceToRemove.equals(m))
      {
        this.referenceList.remove(i);
        return true;
      }
    }
    return false;
  }
  
  public boolean contains(Marker other)
  {
    if (other == null) {
      throw new IllegalArgumentException("Other cannot be null");
    }
    if (equals(other)) {
      return true;
    }
    if (hasReferences()) {
      for (Marker ref : this.referenceList) {
        if (ref.contains(other)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean contains(String name)
  {
    if (name == null) {
      throw new IllegalArgumentException("Other cannot be null");
    }
    if (this.name.equals(name)) {
      return true;
    }
    if (hasReferences()) {
      for (Marker ref : this.referenceList) {
        if (ref.contains(name)) {
          return true;
        }
      }
    }
    return false;
  }
  
  private static String OPEN = "[ ";
  private static String CLOSE = " ]";
  private static String SEP = ", ";
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Marker)) {
      return false;
    }
    Marker other = (Marker)obj;
    return this.name.equals(other.getName());
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
  
  public String toString()
  {
    if (!hasReferences()) {
      return getName();
    }
    Iterator<Marker> it = iterator();
    
    StringBuilder sb = new StringBuilder(getName());
    sb.append(' ').append(OPEN);
    while (it.hasNext())
    {
      Marker reference = (Marker)it.next();
      sb.append(reference.getName());
      if (it.hasNext()) {
        sb.append(SEP);
      }
    }
    sb.append(CLOSE);
    
    return sb.toString();
  }
}
