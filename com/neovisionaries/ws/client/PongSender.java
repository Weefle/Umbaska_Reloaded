package com.neovisionaries.ws.client;

class PongSender
  extends PeriodicalFrameSender
{
  private static final String TIMER_NAME = "PongSender";
  
  public PongSender(WebSocket webSocket, PayloadGenerator generator)
  {
    super(webSocket, "PongSender", generator);
  }
  
  protected WebSocketFrame createFrame(byte[] payload)
  {
    return WebSocketFrame.createPongFrame(payload);
  }
}
