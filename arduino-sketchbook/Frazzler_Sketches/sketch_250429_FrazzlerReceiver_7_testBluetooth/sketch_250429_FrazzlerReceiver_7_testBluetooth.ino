
/* sketch 250429
Frazzler receiver #7 - testBluetooth
Test bluetooth connection and communication

Echo bytes typed into serial console to the Bluetooth line
Print to serial when received from bluetooth

DOES NOT WORK
*/

#include <Arduino.h>
#include <pins_arduino.h>
#include <Servo.h>
#include <Frazzler.h>
#include <RingBuffer.h>
#include <SoftwareSerial.h>

Servo spinnerServo;  // create servo object to control a servo
Servo flickerServo;
SoftwareSerial swSerial(10, 11);

#define btSerial swSerial

String sendMessage = "";
String receivedMessage = "";

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
  btSerial.begin(BT_BAUD);
  while (!Serial) {  } // wait for serial


  printServoSpeeds();
  Serial.println(F("Frazzler finished setup"));

  char buffer[8] = {0};
  buffer[0] = int('A');
  buffer[1] = (int) ('7');
  buffer[2] = NULL;
  Serial.println(buffer);
}

void loop() {
  // checkReceiverUrgentActions();
  bluetoothTest();
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
