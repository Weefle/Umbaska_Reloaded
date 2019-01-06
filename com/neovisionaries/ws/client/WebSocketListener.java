package com.neovisionaries.ws.client;

import java.util.List;
import java.util.Map;

public abstract interface WebSocketListener
{
  public abstract void onStateChanged(WebSocket paramWebSocket, WebSocketState paramWebSocketState)
    throws Exception;
  
  public abstract void onConnected(WebSocket paramWebSocket, Map<String, List<String>> paramMap)
    throws Exception;
  
  public abstract void onConnectError(WebSocket paramWebSocket, WebSocketException paramWebSocketException)
    throws Exception;
  
  public abstract void onDisconnected(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame1, WebSocketFrame paramWebSocketFrame2, boolean paramBoolean)
    throws Exception;
  
  public abstract void onFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onContinuationFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onTextFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onBinaryFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onCloseFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onPingFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onPongFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onTextMessage(WebSocket paramWebSocket, String paramString)
    throws Exception;
  
  public abstract void onBinaryMessage(WebSocket paramWebSocket, byte[] paramArrayOfByte)
    throws Exception;
  
  public abstract void onSendingFrame(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onFrameSent(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onFrameUnsent(WebSocket paramWebSocket, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onError(WebSocket paramWebSocket, WebSocketException paramWebSocketException)
    throws Exception;
  
  public abstract void onFrameError(WebSocket paramWebSocket, WebSocketException paramWebSocketException, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onMessageError(WebSocket paramWebSocket, WebSocketException paramWebSocketException, List<WebSocketFrame> paramList)
    throws Exception;
  
  public abstract void onMessageDecompressionError(WebSocket paramWebSocket, WebSocketException paramWebSocketException, byte[] paramArrayOfByte)
    throws Exception;
  
  public abstract void onTextMessageError(WebSocket paramWebSocket, WebSocketException paramWebSocketException, byte[] paramArrayOfByte)
    throws Exception;
  
  public abstract void onSendError(WebSocket paramWebSocket, WebSocketException paramWebSocketException, WebSocketFrame paramWebSocketFrame)
    throws Exception;
  
  public abstract void onUnexpectedError(WebSocket paramWebSocket, WebSocketException paramWebSocketException)
    throws Exception;
  
  public abstract void handleCallbackError(WebSocket paramWebSocket, Throwable paramThrowable)
    throws Exception;
  
  public abstract void onSendingHandshake(WebSocket paramWebSocket, String paramString, List<String[]> paramList)
    throws Exception;
}
