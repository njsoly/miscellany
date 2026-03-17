
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
  spinnerServo.attach(SERVO_SPINNER, 1250, 1750);
  flickerServo.attach(SERVO_FLICKER, 1250, 1750);
  Serial.begin(BT_BAUD);
  serialToController.begin(BT_BAUD);

  while (!Serial) {  } // wait for 

  clearServoSpeeds();

  Serial.println("Frazzler finished setup");
}

void loop() {
  // checkReceiverUrgentActions();

  testRunFlicker();
  testRunSpinner();

  clearServoSpeeds();

  delay(500);
}

void clearServoSpeeds(){
  flickerServo.writeMicroseconds(1500);
  spinnerServo.writeMicroseconds(1500);
}

void testRunFlicker(){
  clearServoSpeeds();

  for(int i = 0; i < 10; i++){
    Serial.println("Flicker positive");
    flickerServo.write(120);
    delay(2000);
    Serial.println("Flicker negative");
    flickerServo.write(60);
    delay(2000);
    Serial.println("Flicker stopped");
    flickerServo.write(90);
    delay(2000);

  }
}

void testRunSpinner(){
  clearServoSpeeds();

  for(int i = 0; i < 10; i++){
    Serial.println("Spinner positive");
    spinnerServo.write(120);
    delay(2000);
    Serial.println("Spinner negative");
    spinnerServo.write(60);
    delay(2000);
    Serial.println("Spinner stopped");
    spinnerServo.write(90));
    delay(2000);

    clearServoSpeeds();
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