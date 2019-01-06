package com.neovisionaries.ws.client;

class StateManager
{
  private WebSocketState mState;
  
  static enum CloseInitiator
  {
    NONE,  SERVER,  CLIENT;
    
    private CloseInitiator() {}
  }
  
  private CloseInitiator mCloseInitiator = CloseInitiator.NONE;
  
  public StateManager()
  {
    this.mState = WebSocketState.CREATED;
  }
  
  public WebSocketState getState()
  {
    return this.mState;
  }
  
  public void setState(WebSocketState state)
  {
    this.mState = state;
  }
  
  public void changeToClosing(CloseInitiator closeInitiator)
  {
    this.mState = WebSocketState.CLOSING;
    if (this.mCloseInitiator == CloseInitiator.NONE) {
      this.mCloseInitiator = closeInitiator;
    }
  }
  
  public boolean getClosedByServer()
  {
    return this.mCloseInitiator == CloseInitiator.SERVER;
  }
}
