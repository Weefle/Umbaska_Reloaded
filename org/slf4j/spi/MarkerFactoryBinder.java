package org.slf4j.spi;

import org.slf4j.IMarkerFactory;

public abstract interface MarkerFactoryBinder
{
  public abstract IMarkerFactory getMarkerFactory();
  
  public abstract String getMarkerFactoryClassStr();
}
