
#include <Arduino.h>
#include <RingBuffer.h>
#include <SoftwareSerial.h>

#include <Frazzler.h>

SoftwareSerial serialToReceiver(BT_RX, BT_TX);

void setup() {
  Serial.begin(BT_BAUD);

  while(!Serial) {} // wait for Serial
    Serial.println("Frazzler controller finished setup")

}

void loop() {
  // checkControllerUrgentActions();
  

}

void checkControllerUrgentActions() {
  
  checkMotorKill();
}
