package com.mashape.unirest.http.utils;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.HttpClientConnectionManager;

public class SyncIdleConnectionMonitorThread
  extends Thread
{
  private final HttpClientConnectionManager connMgr;
  
  public SyncIdleConnectionMonitorThread(HttpClientConnectionManager connMgr)
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
