package de.btobastian.javacord.exceptions;

public class RateLimitedException
  extends Exception
{
  private final long retryAfter;
  private final long retryAt;
  
  public RateLimitedException(String message, long retryAfter)
  {
    super(message);
    this.retryAfter = retryAfter;
    this.retryAt = (System.currentTimeMillis() + retryAfter);
  }
  
  public long getRetryAfter()
  {
    return this.retryAfter;
  }
  
  public long getRetryAt()
  {
    return this.retryAt;
  }
  
  public void waitTillRetry()
  {
    long time = getRetryAt() - System.currentTimeMillis();
    if (time < 1L) {
      return;
    }
    try
    {
      Thread.sleep(time);
    }
    catch (InterruptedException ignored) {}
  }
}
