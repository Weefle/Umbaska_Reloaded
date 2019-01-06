package uk.co.umbaska.Registration;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.util.SimpleExpression;

 enum SyntaxElement
{
  CONDITION,  EFFECT,  SIMPLE_EXPRESSION,  PROPERTY_EXPRESSION,  SIMPLE_PROPERTY_EXPRESSION;
  
  @SuppressWarnings("rawtypes")
private static ArrayList<Class> umbaskaClasses = new ArrayList<>();
  
  private SyntaxElement() {}
  
  @SuppressWarnings("rawtypes")
public static ArrayList<Class> getUmbaskaExtensionClasses()
  {
    if (umbaskaClasses.isEmpty())
    {
      umbaskaClasses.add(UmbaskaEffect.class);
      umbaskaClasses.add(UmbaskaCondition.class);
      umbaskaClasses.add(SimpleUmbaskaExpression.class);
      umbaskaClasses.add(SimpleUmbaskaPropertyExpression.class);
      umbaskaClasses.add(UmbaskaPropertyExpression.class);
    }
    return umbaskaClasses;
  }
  
  public static SyntaxElement parseClass(Class<?> clazz)
  {
    if (clazz == UmbaskaCondition.class) {
      return CONDITION;
    }
    if (clazz == UmbaskaEffect.class) {
      return EFFECT;
    }
    if (clazz == SimpleExpression.class) {
      return SIMPLE_EXPRESSION;
    }
    if (clazz == SimplePropertyExpression.class) {
      return SIMPLE_PROPERTY_EXPRESSION;
    }
    if (clazz == PropertyExpression.class) {
      return PROPERTY_EXPRESSION;
    }
    return null;
  }
  
  public String toString()
  {
    switch (this)
    {
    case CONDITION: 
      return "Condition";
    case EFFECT: 
      return "Effect";
    case SIMPLE_EXPRESSION: 
      return "SimpleExpression";
    case PROPERTY_EXPRESSION: 
      return "PropertyExpression";
    case SIMPLE_PROPERTY_EXPRESSION: 
      return "SimplePropertyExpression";
    }
    return "UnknownSyntaxElement";
  }
  
  public String getDocsURLName()
  {
    if (StringUtils.containsIgnoreCase(name(), "expression")) {
      return "expression";
    }
    return name().toLowerCase();
  }
  
  public String getName(Class<?> clazz)
  {
    if (clazz == null) {
      return null;
    }
    Name name = (Name)getClass().getAnnotation(Name.class);
    if ((name == null) || (name.value().isEmpty())) {
      return "Umbaska " + toString() + " at " + clazz.getCanonicalName();
    }
    return name.value();
  }
}
