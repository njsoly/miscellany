
/* sketch 250428
Frazzler receiver #6 - testHalls
Tests Hall sensors on pins A2 and A3.
Checks sensor values against a given variance threshold, and
prints when a sensor enters or leaves it.
*/

#include <Arduino.h>
#include <pins_arduino.h>
#include <Servo.h>
#include <Frazzler.h>
#include <RingBuffer.h>
#include <SoftwareSerial.h>

// const uint16_t REED_DEBOUNCE_MS = 10;
// const bool DEBUG_REED_SWITCHES = true;

Servo spinnerServo;  // create servo object to control a servo
Servo flickerServo;
SoftwareSerial serialToController(BT_RX, BT_TX);

bool BT_CONNECTED = false;
bool SERVOS_ENABLED = true;
const uint8_t NEG = 0, POS = 1;

bool limitStates[2] = {false, false};
uint16_t limitBaselines[2] = {496, 500};
const uint8_t LIMIT_THRESHOLD = 7;

void setup() {
  spinnerServo.attach(SERVO_SPINNER, 1250, 1750);
  flickerServo.attach(SERVO_FLICKER, 1250, 1750);
  clearServoSpeeds();

  pinMode(HALL_NEG, INPUT);
  pinMode(HALL_POS, INPUT);

  Serial.begin(BT_BAUD);
  serialToController.begin(BT_BAUD);
  while (!Serial) {  } // wait for serial


  printServoSpeeds();
  Serial.println("Frazzler finished setup");
}

void loop() {
  checkReceiverUrgentActions();

}

void printServoSpeeds() {
  Serial.println("Servo speeds: ");
  Serial.println("\tSpinner = " + String(spinnerServo.read()));
  Serial.println("\tFlicker = " + String(flickerServo.read()));
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
  // checkMotorKill();
  checkLimitSensors();
}

// if bluetooth is not connected, disable everything
void checkBluetoothState() {

}

void checkLimitSensors() {
  // long startMillis = millis();
  long posHall = getLimitSensorValue(POS);
  long negHall = getLimitSensorValue(NEG);
  // long endMillis = millis();
  // Serial.print("Sensors: (-) = " + String(negHall) + ", (+) = " + String(posHall));
  // Serial.println(" Reading took: " + String(endMillis - startMillis) + "ms");
  
  bool posHallOutside = abs(limitBaselines[POS] - posHall) >= LIMIT_THRESHOLD;
  bool negHallOutside = abs(limitBaselines[NEG] - negHall) >= LIMIT_THRESHOLD;

  if (limitStates[NEG] != negHallOutside) {
    limitStates[NEG] = negHallOutside;
    Serial.println("NEG sensor is now " + String(limitStates[NEG]));
  }
  if (limitStates[POS] != posHallOutside) {
    limitStates[POS] = posHallOutside;
    Serial.println("POS sensor is now " + String(limitStates[POS]));
  }
}

long getLimitSensorValue(uint8_t sensor){
  if (sensor != NEG && sensor != POS){
    Serial.println("ERROR: bad pin value");
    return 0;
  }

  int pin = -1;
  if (sensor == NEG){
    pin = HALL_NEG;
  } else if (sensor == POS) {
    pin = HALL_POS;
  }

  long valueSum = 0;
  for(int i = 0; i < 16; i++){
    valueSum += analogRead(pin);
  }
  return valueSum >> 4;
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
