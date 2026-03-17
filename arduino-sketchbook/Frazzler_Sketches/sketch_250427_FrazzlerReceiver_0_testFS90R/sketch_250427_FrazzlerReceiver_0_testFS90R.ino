
/* Sweep
 by BARRAGAN <http://barraganstudio.com>
 This example code is in the public domain.

 modified 8 Nov 2013
 by Scott Fitzgerald
 https://www.arduino.cc/en/Tutorial/LibraryExamples/Sweep
*/

#include <Arduino.h>
#include <pins_arduino.h>
#include <Servo.h>
#include <Frazzler.h>
#include <RingBuffer.h>
#include <SoftwareSerial.h>

Servo spinnerServo;  // create servo object to control a servo
Servo flickerServo;
SoftwareSerial serialToController(BT_RX, BT_TX);

bool BT_STATE = false;
bool SERVOS_ENABLED = true;

int pos = 0;    // variable to store the servo position

void setup() {
  spinnerServo.attach(SERVO_SPINNER);  // attaches the servo on pin 9 to the servo object
  flickerServo.attach(SERVO_FLICKER);
  Serial.begin(BT_BAUD);
  serialToController.begin(BT_BAUD);

  while (!Serial) {  } // wait for 

  Serial.println("Frazzler finished setup");
}

void loop() {
  // checkReceiverUrgentActions();

  testRunFlicker();

  delay(500);
}

void testRunFlicker(){
  for(int i = 0; i < 10; i++){
    Serial.println("Servo positive");
    flickerServo.write(179);
    delay(1000);
    Serial.println("Servo negative");
    flickerServo.write(-179);
    delay(1000);

    flickerServo.write(0);
  }
}

void checkReceiverUrgentActions() {
  checkBluetoothState();
  // checkReedSwitches();
  // checkMotorKill();
}

// if bluetooth is not connected, disable everything
void checkBluetoothState() {

}

// if a reed switch is on, 