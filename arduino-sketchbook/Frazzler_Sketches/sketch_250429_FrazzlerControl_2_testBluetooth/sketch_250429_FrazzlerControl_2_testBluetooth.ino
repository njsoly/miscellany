/* sketch 250429
Frazzler controller #2 - testBluetooth
Test bluetooth connection and communication

Echo bytes typed into serial console to the Bluetooth line
Print to serial when received from bluetooth

DOES NOT WORK
*/

#include <Arduino.h>
#include <Adafruit_GFX.h>
#include <Adafruit_GrayOLED.h>
#include <Adafruit_SPITFT.h>
#include <Adafruit_SPITFT_Macros.h>
#include <gfxfont.h>

#include <RingBuffer.h>
#include <SoftwareSerial.h>


#include <Frazzler.h>

#include <Adafruit_SSD1306.h>
#include <splash.h>


// SoftwareSerial serialToReceiver(BT_RX, BT_TX);
Adafruit_SSD1306 oled(128, 64, &Wire, -1);
SoftwareSerial swSerial(10, 11);

#define btSerial swSerial

String sendMessage = "";
String receivedMessage = "";

void setup() {
  Serial.begin(BT_BAUD);
  btSerial.begin(BT_BAUD,SERIAL_8N1, true);

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


void bluetoothTest() {
 while (btSerial.available() > 0) {
    Serial.println("a character is available: " + String(btSerial.peek(), 2));
    Serial.print("Received message so far: \"");
    Serial.print(receivedMessage);
    Serial.println("\"");
    char receivedChar = btSerial.read();
    if (receivedChar == '\n') {
      receivedMessage += '\r';
      receivedMessage += '\n';
      Serial.println("Received the message " + receivedMessage);  // Print the received message in the Serial monitor
      receivedMessage = "";  // Reset the received message
    } else {
      receivedMessage += receivedChar;  // Append characters to the received message
    }
  }


  if (Serial.available() > 0) {
    char inputChar = Serial.read();
    if (inputChar == '\n') {
      sendMessage += '\r';
      sendMessage += '\n';

      Serial.println("Sending the message \"" + sendMessage + "\"");
      btSerial.println(sendMessage);  // Send the message through btSerial with a newline character
      sendMessage = "";  // Reset the message
    } else {
      sendMessage += inputChar;  // Append characters to the message
    }
  }
}

void checkControllerUrgentActions() {
  
  // checkMotorKill();
}
