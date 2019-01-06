package com.neovisionaries.ws.client;

class PingSender
  extends PeriodicalFrameSender
{
  private static final String TIMER_NAME = "PingSender";
  
  public PingSender(WebSocket webSocket, PayloadGenerator generator)
  {
    super(webSocket, "PingSender", generator);
  }
  
  protected WebSocketFrame createFrame(byte[] payload)
  {
    return WebSocketFrame.createPingFrame(payload);
  }
}
