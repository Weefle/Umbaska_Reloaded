package uk.co.umbaska.ParticleProjectiles;

public enum UmbErrorTypes
{
  NO_START_LOCATION("No Start Location"), 
  NO_RADIUS("No Radius"), 
  NO_PARTICLE_TYPE("No particle defined");
  
  private String msg;
  
  private UmbErrorTypes(String s)
  {
    this.msg = s;
  }
}
