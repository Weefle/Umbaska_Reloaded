package com.neovisionaries.ws.client;

import java.util.Timer;
import java.util.TimerTask;

abstract class PeriodicalFrameSender
{
  private final WebSocket mWebSocket;
  private final String mTimerName;
  private Timer mTimer;
  private boolean mScheduled;
  private long mInterval;
  private PayloadGenerator mGenerator;
  
  public PeriodicalFrameSender(WebSocket webSocket, String timerName, PayloadGenerator generator)
  {
    this.mWebSocket = webSocket;
    this.mTimerName = timerName;
    this.mGenerator = generator;
  }
  
  public void start()
  {
    setInterval(getInterval());
  }
  
  public void stop()
  {
    synchronized (this)
    {
      if (this.mTimer == null) {
        return;
      }
      this.mScheduled = false;
      this.mTimer.cancel();
    }
  }
  
  /* Error */
  public long getInterval()
  {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: astore_1
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 11	com/neovisionaries/ws/client/PeriodicalFrameSender:mInterval	J
    //   8: aload_1
    //   9: monitorexit
    //   10: lreturn
    //   11: astore_2
    //   12: aload_1
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Line number table:
    //   Java source line #65	-> byte code offset #0
    //   Java source line #67	-> byte code offset #4
    //   Java source line #68	-> byte code offset #11
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	16	0	this	PeriodicalFrameSender
    //   2	11	1	Ljava/lang/Object;	Object
    //   11	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   4	10	11	finally
    //   11	14	11	finally
  }
  
  public void setInterval(long interval)
  {
    if (interval < 0L) {
      interval = 0L;
    }
    synchronized (this)
    {
      this.mInterval = interval;
    }
    if (interval == 0L) {
      return;
    }
    if (!this.mWebSocket.isOpen()) {
      return;
    }
    synchronized (this)
    {
      if (this.mTimer == null) {
        this.mTimer = new Timer(this.mTimerName);
      }
      if (!this.mScheduled)
      {
        this.mScheduled = true;
        this.mTimer.schedule(new Task(null), interval);
      }
    }
  }
  
  /* Error */
  public PayloadGenerator getPayloadGenerator()
  {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: astore_1
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 5	com/neovisionaries/ws/client/PeriodicalFrameSender:mGenerator	Lcom/neovisionaries/ws/client/PayloadGenerator;
    //   8: aload_1
    //   9: monitorexit
    //   10: areturn
    //   11: astore_2
    //   12: aload_1
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Line number table:
    //   Java source line #112	-> byte code offset #0
    //   Java source line #114	-> byte code offset #4
    //   Java source line #115	-> byte code offset #11
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	16	0	this	PeriodicalFrameSender
    //   2	11	1	Ljava/lang/Object;	Object
    //   11	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   4	10	11	finally
    //   11	14	11	finally
  }
  
  public void setPayloadGenerator(PayloadGenerator generator)
  {
    synchronized (this)
    {
      this.mGenerator = generator;
    }
  }
  
  private final class Task
    extends TimerTask
  {
    private Task() {}
    
    public void run()
    {
      PeriodicalFrameSender.this.doTask();
    }
  }
  
  private void doTask()
  {
    synchronized (this)
    {
      if ((this.mInterval == 0L) || (!this.mWebSocket.isOpen()))
      {
        this.mScheduled = false;
        
        return;
      }
      this.mWebSocket.sendFrame(createFrame());
      
      this.mTimer.schedule(new Task(null), this.mInterval);
    }
  }
  
  private WebSocketFrame createFrame()
  {
    byte[] payload = generatePayload();
    
    return createFrame(payload);
  }
  
  private byte[] generatePayload()
  {
    if (this.mGenerator == null) {
      return null;
    }
    try
    {
      return this.mGenerator.generate();
    }
    catch (Throwable t) {}
    return null;
  }
  
  protected abstract WebSocketFrame createFrame(byte[] paramArrayOfByte);
}
