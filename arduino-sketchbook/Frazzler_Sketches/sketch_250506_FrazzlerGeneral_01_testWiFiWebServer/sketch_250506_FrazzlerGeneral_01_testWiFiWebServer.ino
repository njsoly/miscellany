/*
 sketch 250506 - Frazzler General #01: test WiFiWebServer

From example: 

  WiFi Web Server

 A simple web server that shows the value of the analog input pins.

 This example is written for a network using WPA encryption. For
 WEP or WPA, change the WiFi.begin() call accordingly.

 Circuit:
 * Analog inputs attached to pins A0 through A5 (optional)

 created 13 July 2010
 by dlf (Metodo2 srl)
 modified 31 May 2012
 by Tom Igoe


  Find the full UNO R4 WiFi Network documentation here:
  https://docs.arduino.cc/tutorials/uno-r4-wifi/wifi-examples#wi-fi-web-server
 */

#include "WiFiS3.h"
#include "StringHelpers.h"

#include "arduino_secrets.h" 
///////please enter your sensitive data in the Secret tab/arduino_secrets.h
char ssid[] = SECRET_SSID;        // your network SSID (name)
char pass[] = SECRET_PASS;    // your network password (use for WPA, or use as key for WEP)
int keyIndex = 0;                 // your network key index number (needed only for WEP)

int status = WL_IDLE_STATUS;
// const String favicon = "<img src=\"data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAC9SURBVDhPnVJJEsAgCOPpPM2f2QEMQrQ99JBxgSzYisw5/0BVY88FwxjDYU0G2x9k3N+IdgBZRf0MESeLbGEWqFb1jGZzBlKgOt+AyCKxtrFuzowg7xWGXjY1JgDeVCKjFwJRF3lNYPc7fozCdcGL4rUb2Wb+GDEThEhPAnfcsUiYOncJOPp3Z1ITX7wUSCG15z4du3P8TCmQY+S6X/xwXn9i6acEJUlNAVH/nJ1zJsAecevMRO4JuHgj8d0DywFqj1ysR9cAAAAASUVORK5CYII=\"/>";
const String favicon = "HTTP/1.1 200 OK\nContent-Type: image/png;base64\n\niVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAC9SURBVDhPnVJJEsAgCOPpPM2f2QEMQrQ99JBxgSzYisw5/0BVY88FwxjDYU0G2x9k3N+IdgBZRf0MESeLbGEWqFb1jGZzBlKgOt+AyCKxtrFuzowg7xWGXjY1JgDeVCKjFwJRF3lNYPc7fozCdcGL4rUb2Wb+GDEThEhPAnfcsUiYOncJOPp3Z1ITX7wUSCG15z4du3P8TCmQY+S6X/xwXn9i6acEJUlNAVH/nJ1zJsAecevMRO4JuHgj8d0DywFqj1ysR9cAAAAASUVORK5CYII=";

WiFiServer server(80);

void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(250000);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // check for the WiFi module:
  if (WiFi.status() == WL_NO_MODULE) {
    Serial.println("Communication with WiFi module failed!");
    // don't continue
    while (true);
  }

  String fv = WiFi.firmwareVersion();
  if (fv < WIFI_FIRMWARE_LATEST_VERSION) {
    Serial.println("Please upgrade the firmware");
  }

  // attempt to connect to WiFi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);

    // wait 10 seconds for connection:
    delay(10000);
    Serial.println("wifi status: " + String(status));
  }
  server.begin();
  // you're connected now, so print out the status:
  printWifiStatus();
}

char request[256] = "";
uint16_t requestPtr = 0;
bool firstLineFinished = false;
const bool DEBUG_FULL_REQUEST = false;

void loop() {
  // listen for incoming clients
  WiFiClient client = server.available();
  if (client) {
    Serial.println("new client");
    // an HTTP request ends with a blank line
    boolean currentLineIsBlank = true;
    requestPtr = 0;
    firstLineFinished = false;

    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        if (firstLineFinished == false) {
          request[requestPtr++] = c;
          request[requestPtr] = NULL;
        }
        if (DEBUG_FULL_REQUEST == true || firstLineFinished == false) {
          Serial.write(c);
        }
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the HTTP request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {
          handleResponse(client, request);
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
          firstLineFinished = true;
        } else if (c == '\r') {
        } else {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);

    // close the connection:
    client.stop();
    Serial.println("client disconnected");
  }
}

void handleResponse(WiFiClient& client, char* request) {
  Serial.println("Current request: " + String(request));
  
  String fullReq = String(request);
  
  uint16_t startUrl = fullReq.indexOf(' ') + 1;
  uint16_t endUrl = fullReq.lastIndexOf(' ');
  String realRequest = fullReq.substring(startUrl, endUrl);
  Serial.println("Real request is: \"" + realRequest + "\".");

  // send a standard HTTP response header
  if (realRequest.equals("/")){
    doAnalogSensorDemo(client);
  } else if (realRequest.startsWith("/favicon")) {
    client.println(favicon);
  } else if (realRequest.equals("/x")) {
    doX(client);
  }
}

void doX(WiFiClient& client) {
  String x = "xX.";
  client.write(x.c_str(), x.length());
}

/** This is essentially the original code of the example sketch.*/
void doAnalogSensorDemo(WiFiClient& client) {
    startHtmlOk(client);
    printAnalogValues(client);
    endHtml(client);
}

void startHtmlOk(WiFiClient& client) {
  startHtmlOk(client, "text/html", "Disconnect", 5);
}

void startHtmlOk(WiFiClient& client, String contentType, String connectionHeader, uint16_t refreshTime) {
  client.println(F("HTTP/1.1 200 OK"));
  client.println("Content-Type: " + contentType);
  client.println("Connection: " + connectionHeader);  // the connection will be closed after completion of the response
  if (refreshTime > 0) {
    client.println(F("Refresh: 5"));  // refresh the page automatically every 5 sec
  }
  client.println();
  client.println(F("<!DOCTYPE HTML>"));
  client.println(F("<html>"));
}

void endHtml(WiFiClient& client) {
   client.println(F("</html>"));
}

void printAnalogValues(WiFiClient& client) {
  // output the value of each analog input pin
  for (int analogChannel = 0; analogChannel < 6; analogChannel++) {
    int sensorReading = analogRead(analogChannel);
    client.print(F("analog input "));
    client.print(analogChannel);
    client.print(F(" is "));
    client.print(sensorReading);
    client.println(F("<br />"));
  }
}


void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print(F("SSID: "));
  Serial.println(WiFi.SSID());

  // print your board's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print(F("IP Address: "));
  Serial.println(ip);

  uint8_t macAddress[WL_MAC_ADDR_LENGTH];
  WiFi.macAddress(macAddress);
  Serial.print(F("Mac: "));
  for(int i = 0; i < WL_MAC_ADDR_LENGTH; i++){
    Serial.print(macAddress[i], HEX);
    if (i < WL_MAC_ADDR_LENGTH - 1) {
      Serial.print(":");
    }
  }
  Serial.println();

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
