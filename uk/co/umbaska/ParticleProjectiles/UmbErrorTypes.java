package uk.co.umbaska.ParticleProjectiles;

public enum UmbErrorTypes
{
  NO_START_LOCATION("No Start Location"), 
  NO_RADIUS("No Radius"), 
  NO_PARTICLE_TYPE("No particle defined");
  
  private String msg;
  
  private UmbErrorTypes(String s)
  {
    this.setMsg(s);
  }

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}
}
