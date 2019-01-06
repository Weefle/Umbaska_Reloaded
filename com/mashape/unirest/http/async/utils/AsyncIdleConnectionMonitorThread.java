package com.mashape.unirest.http.async.utils;

import java.util.concurrent.TimeUnit;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;

public class AsyncIdleConnectionMonitorThread
  extends Thread
{
  private final PoolingNHttpClientConnectionManager connMgr;
  
  public AsyncIdleConnectionMonitorThread(PoolingNHttpClientConnectionManager connMgr)
  {
    super.setDaemon(true);
    this.connMgr = connMgr;
  }
  
  public void run()
  {
    try
    {
      while (!Thread.currentThread().isInterrupted()) {
        synchronized (this)
        {
          wait(5000L);
          
          this.connMgr.closeExpiredConnections();
          
          this.connMgr.closeIdleConnections(30L, TimeUnit.SECONDS);
        }
      }
    }
    catch (InterruptedException ex) {}
  }
}
