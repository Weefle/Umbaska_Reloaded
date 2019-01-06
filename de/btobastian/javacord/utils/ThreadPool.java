package de.btobastian.javacord.utils;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool
{
  private static final int CORE_POOL_SIZE = 1;
  private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
  private static final int KEEP_ALIVE_TIME = 60;
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
  private ExecutorService executorService = null;
  private ListeningExecutorService listeningExecutorService = null;
  private final ConcurrentHashMap<String, ExecutorService> executorServiceSingeThreads = new ConcurrentHashMap();
  
  public ThreadPool()
  {
    this.executorService = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60L, TIME_UNIT, new SynchronousQueue());
    
    this.listeningExecutorService = MoreExecutors.listeningDecorator(this.executorService);
  }
  
  public ExecutorService getExecutorService()
  {
    return this.executorService;
  }
  
  public ExecutorService getSingleThreadExecutorService(String id)
  {
    synchronized (this.executorServiceSingeThreads)
    {
      ExecutorService service = (ExecutorService)this.executorServiceSingeThreads.get(id);
      if (service == null)
      {
        service = Executors.newSingleThreadExecutor();
        this.executorServiceSingeThreads.put(id, service);
      }
      return service;
    }
  }
  
  public ListeningExecutorService getListeningExecutorService()
  {
    return this.listeningExecutorService;
  }
}
