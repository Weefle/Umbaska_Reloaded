package uk.co.umbaska;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

import ch.njol.skript.variables.Variables;

public class ClientThread extends Thread
{
  private Main plugin;
  private InetAddress ip;
  private Integer port;
  private Boolean connected = Boolean.valueOf(false);
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private Integer heartbeat = Integer.valueOf(0);
  private String name;
  private String pass;
  private Boolean checking;
  
  public ClientThread(Main plugin, InetAddress ip, Integer port, Integer heartbeat, String name, String pass) {
    this.plugin = plugin;
    this.ip = ip;
    this.port = port;
    this.heartbeat = heartbeat;
    this.name = name;
    this.pass = pass;
    connect(Boolean.valueOf(false));
    this.checking = Boolean.valueOf(true);
  }
  
  public void run() {
    while (this.checking.booleanValue()) {
      if (this.connected.booleanValue()) {
        this.out.println("heartbeat");
        if (this.out.checkError()) {
          this.connected = Boolean.valueOf(false);
          this.plugin.debugger.debug("Lost connection to the server.");
        } else {
          try {
            Integer size = Integer.valueOf(this.plugin.oq.size());
            Integer count = this.plugin.qc;
            if (size.intValue() > count.intValue()) {
              for (int i = count.intValue(); i < size.intValue(); i++) {
               // Integer localInteger1 = count;
                //Integer localInteger2 = count = Integer.valueOf(count.intValue() + 1);
                String output = (String)this.plugin.oq.get(i);
                this.out.println(output);
                output.replace("�", "@@COLORCODE@@");
                String[] splitValues = output.split("@@UMB@@");
                if (splitValues[0] == "setvar") {
                  Main.tokenTracker.put(UUID.fromString(splitValues[1]), null);
                }
                if (Main.umbCordDebug.booleanValue()) {
                  this.plugin.debugger.debug("[" + this.socket.getInetAddress().getHostName() + ":" + this.socket.getPort() + "] " + "Sent output - " + output);
                }
              }
              this.plugin.qc = count;
            }
            while (this.in.ready()) {
              String input = this.in.readLine();
              if (!input.equals("heartbeat")) {
                if (Main.umbCordDebug.booleanValue()) {
                  this.plugin.debugger.debug("[" + this.socket.getInetAddress().getHostName() + ":" + this.socket.getPort() + "] " + "Received input - " + input);
                }
                String[] data = input.split(this.plugin.spacer);
                if ((data[0].equals("updateglobalvariable")) && 
                  (Main.getInstance().variableCache.definedGlobalVariables.contains(data[1]))) {
                  Variables.setVariable(data[1], data[2], new DummyEvent(), false);
                }
                
                if (data[0].equals("getvar")) {
                  String uuid = data[1];
                  String value = data[2].replace("@@COLORCODE@@", "�");
                  Main.tokenTracker.put(UUID.fromString(uuid), value);
                } else if (data[0].equals("newconnection")) {
                  String uuid = data[1];
                  String value = data[2].replace("@@COLORCODE@@", "�");
                  Main.globalKeyCache.put(uuid, value);
                }
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else {
        connect(Boolean.valueOf(true));
      }
      try {
        sleep(this.heartbeat.intValue());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  private void connect(Boolean sleep) {
    if (sleep.booleanValue()) {
      try {
        sleep(10000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    try {
      this.socket = new Socket(this.ip, this.port.intValue());
      this.out = new PrintWriter(this.socket.getOutputStream(), true);
      this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      this.out.println(this.name);
      if (this.in.readLine().equals("n")) {
        this.plugin.debugger.debug("The name " + this.name + " is already connected.");
        this.socket.close();
        return;
      }
      this.out.println(this.pass);
      if (this.in.readLine().equals("n")) {
        this.plugin.debugger.debug("The password you provided is invalid.");
        this.socket.close();
        return;
      }
      this.connected = Boolean.valueOf(true);
      this.plugin.debugger.debug("Connected to " + this.ip.getHostName() + ":" + String.valueOf(this.port) + " under name " + this.name + ".");
    } catch (IOException e) {
      this.plugin.debugger.debug("Could not connect to the server.");
    }
  }
}
