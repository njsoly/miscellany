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


SoftwareSerial serialToReceiver(BT_RX, BT_TX);
Adafruit_SSD1306 oled(128, 64, &Wire, -1);

void setup() {
  Serial.begin(BT_BAUD);

  while(!Serial) {} // wait for Serial
    Serial.println("Frazzler controller finished setup");

  oled.begin(SSD1306_SWITCHCAPVCC, OLED_I2C_ADDR, false, true);
  oled.clearDisplay();
}

void loop() {
  // checkControllerUrgentActions();

  for(int i = 0; i < 64; i += 8){
    oled.drawFastHLine(10, i, 108, 0x1);
    oled.display();
    delay(1000);
  }
  oled.clearDisplay();
  oled.display();
  delay(1000);

}

void checkControllerUrgentActions() {
  
  // checkMotorKill();
}
