
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

const uint16_t REED_DEBOUNCE_MS = 10;
const bool DEBUG_REED_SWITCHES = true;

Servo spinnerServo;  // create servo object to control a servo
Servo flickerServo;
SoftwareSerial serialToController(BT_RX, BT_TX);

bool BT_STATE = false;
bool SERVOS_ENABLED = true;

int reedStates[2] = {0, 0};
int reedHistoryPos[3] = {0, 0, 0};
int reedHistoryNeg[3] = {0, 0, 0};

void setup() {
  spinnerServo.attach(SERVO_SPINNER, 1250, 1750);
  flickerServo.attach(SERVO_FLICKER, 1250, 1750);
  clearServoSpeeds();

  pinMode(REED_SWITCH_NEG, INPUT);
  pinMode(REED_SWITCH_POS, INPUT);

  Serial.begin(BT_BAUD);
  serialToController.begin(BT_BAUD);

  while (!Serial) {  } // wait for 

  printServoSpeeds();
  Serial.println("Frazzler finished setup");
}

enum testStep { NEG, POS };
void loop() {
  checkReceiverUrgentActions();

  // testRunFlicker();
  // testRunSpinner();

  // delay(500);
}

void printServoSpeeds() {
  Serial.print("Servo speeds: ");
  Serial.println("Spinner = " + String(spinnerServo.read()));
  Serial.println("Flicker = " + String(flickerServo.read()));
}

void clearServoSpeeds() {
  flickerServo.writeMicroseconds(1500);
  spinnerServo.writeMicroseconds(1500);
}

void clearFlicker() {
  flickerServo.writeMicroseconds(1500);
}

void testRunFlicker(){
  clearServoSpeeds();

  for(int i = 0; i < 10; i++){
    Serial.println("Flicker positive");
    flickerServo.write(180);
    delay(2000);
    Serial.println("Flicker negative");
    flickerServo.write(0);
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
    spinnerServo.writeMicroseconds(1700);
    delay(2000);
    Serial.println("Spinner negative");
    spinnerServo.writeMicroseconds(1200);
    delay(2000);
    Serial.println("Spinner stopped");
    spinnerServo.writeMicroseconds(1500);
    delay(2000);

    clearServoSpeeds();
  }

}


void checkReceiverUrgentActions() {
  checkBluetoothState();
  checkReedSwitches();
  // checkMotorKill();
}

// if bluetooth is not connected, disable everything
void checkBluetoothState() {

}

void checkReedSwitches() {
  // first off, clear flicker servo if it's driving against a reed switch
  int reedNegative = digitalRead(REED_SWITCH_NEG);
  if (reedStates[0] == HIGH || reedNegative == HIGH) {
    if (flickerServo.readMicroseconds() < 1500) {
      if (DEBUG_REED_SWITCHES) {
        Serial.println("Disabling flicker in the negative direction");
      }
      clearFlicker();
    }
  }
  int reedPositive = digitalRead(REED_SWITCH_POS);
  if (reedStates[1] == HIGH || reedPositive == HIGH) {
    if (flickerServo.readMicroseconds() > 1500) {
      if (DEBUG_REED_SWITCHES) {
        Serial.println("Disabling flicker in the positive direction");
      }
      clearFlicker();
    }
  }

  // debounce and set new reed state
  if (reedNegative != reedStates[0]) {
      for(int i = 0; i < 3; i++){
        reedHistoryNeg[i] = 0;
      }
      do {
          reedHistoryNeg[0] = digitalRead(REED_SWITCH_NEG);
          delay(REED_DEBOUNCE_MS);
          reedHistoryNeg[1] = digitalRead(REED_SWITCH_NEG);
          delay(REED_DEBOUNCE_MS);
          reedHistoryNeg[2] = digitalRead(REED_SWITCH_NEG);
      } while (reedHistoryNeg[0] != reedHistoryNeg[1] || reedHistoryNeg[1] != reedHistoryNeg[2]);
  }
  // reedHistoryNeg should be all consistent now
  if (DEBUG_REED_SWITCHES && reedStates[0] != reedHistoryNeg[0]) {
    Serial.println("Reed switch negative changed from " + getHighOrLow(reedStates[0]) + " to " + getHighOrLow(reedHistoryNeg[0]));
  }
  reedStates[0] = reedHistoryNeg[0];

  if (reedPositive != reedStates[1]) {
      for(int i = 0; i < 3; i++){
        reedHistoryPos[i] = 0;
      }
      do {
          reedHistoryPos[0] = digitalRead(REED_SWITCH_POS);
          delay(REED_DEBOUNCE_MS);
          reedHistoryPos[1] = digitalRead(REED_SWITCH_POS);
          delay(REED_DEBOUNCE_MS);
          reedHistoryPos[2] = digitalRead(REED_SWITCH_POS);
      } while (reedHistoryPos[0] != reedHistoryPos[1] || reedHistoryPos[1] != reedHistoryPos[2]);
  }
  // reedHistoryPos should be all consistent now
  if (DEBUG_REED_SWITCHES && reedStates[1] != reedHistoryPos[0]) {
    Serial.println("Reed switch positive changed from " + getHighOrLow(reedStates[1]) + " to " + getHighOrLow(reedHistoryPos[0]));
  }
  reedStates[1] = reedHistoryPos[0];
  
}

String getHighOrLow(int i){
  if (i == HIGH){
    return String("HIGH");
  } else if (i == LOW) {
    return String("LOW");
  } else {
    return String("NEITHER");
  }
}
