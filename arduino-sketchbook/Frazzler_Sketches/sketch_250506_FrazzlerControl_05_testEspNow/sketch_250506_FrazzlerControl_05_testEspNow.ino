/* sketch 250506
Frazzler controller #05 - testEspNow
Try out ESP-NOW protocol.

Echo bytes typed into serial console to the other device
Print to serial when received from other device

Goes with Receiver #10 sketch.

*/

#include <Arduino.h>
#include <Adafruit_GFX.h>
#include <Adafruit_GrayOLED.h>
#include <Adafruit_SPITFT.h>
#include <Adafruit_SPITFT_Macros.h>
#include <gfxfont.h>

#include <Frazzler.h>
#include <RingBuffer.h>
#include <SoftwareSerial.h>


#include <Adafruit_SSD1306.h>
#include <splash.h>


Adafruit_SSD1306 oled(128, 64, &Wire, -1);

SoftwareSerial swSerial(BT_RX, BT_TX);
#define wifiSerial swSerial
// #define wifiSerial Serial1

String sendMessage = "";
String receivedMessage = "";

void setup() {
  Serial.begin(BT_BAUD);
  btSerial.begin(BT_BAUD, BT_UART_CONFIG);

  while(!Serial) {} // wait for Serial

  oled.begin(SSD1306_SWITCHCAPVCC, OLED_I2C_ADDR, false, true);
  oled.clearDisplay();
  oled.display();
  Serial.println(F("Frazzler controller finished setup"));
}

void loop() {
  // checkControllerUrgentActions();
  bluetoothTest();
}

void screenTest() {
  for(int i = 0; i < 64; i += 8){
    oled.drawFastHLine(10, i, 108, 0x1);
    oled.display();
    delay(100);
  }
  oled.clearDisplay();
  oled.display();
  delay(100);
}

const char LF = '\n';
const char CR = '\r';
const bool DEBUG_CHARS_IN = false;

void bluetoothTest() {
  if (btSerial.available() > 0){
    Serial.println("There are " + String(btSerial.available()) + " chars available.");
  }

 while (btSerial.available() > 0) {
    if (DEBUG_CHARS_IN == true) {
      Serial.print("Received message so far: \"");
      Serial.print(receivedMessage);
      Serial.println("\"");
    }    
    char receivedChar = btSerial.read();
        switch(receivedChar) {
      case CR: 
      case LF:
        break;
      default: receivedMessage += receivedChar;  // Append characters to the received message
        break;
    }

    if (receivedMessage.endsWith(";")){
      Serial.println("Received the message \"" + receivedMessage + "\"");  // Print the received message in the Serial monitor
      receivedMessage = "";  // Reset the received message
    } 
  }

  while (Serial.available() > 0) {
    char inputChar = Serial.read();
    sendMessage += inputChar;  // Append characters to the message

    if (sendMessage.endsWith(";")) {
      Serial.println("Sending the message \"" + sendMessage + "\"");
      btSerial.println(sendMessage);  // Send the message through btSerial with a newline character
      sendMessage = "";  // Reset the message
    } 
  }
}

void checkControllerUrgentActions() {
  
  // checkMotorKill();
}
