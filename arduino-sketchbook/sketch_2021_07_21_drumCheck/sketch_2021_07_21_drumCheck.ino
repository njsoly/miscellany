/* sketch_2021_07_21_drumCheck
 * @author Nate Solyntjes
 * 
 * This is a small program to tell the arduino how long to send a signal  ...
 * 
 * Looks like the serial monitor sends LF (10) if you pick "Newline".
 */

const int SERIAL_MAX = 128;
const int NEWLINE = 10;
const int LED_PIN = 13;

char serialInArray[SERIAL_MAX + 1]; // one extra for \0
int serialInPointer = 0;
bool finishedRx = false;  // track whether the last character read was the end of a received message (newline)

void setup() {
  Serial.begin(9600); // opens serial port, sets data rate to 9600 bps
  Serial.println("Welcome to sketch_2021_07_21_drumCheck.");
  pinMode(LED_PIN, OUTPUT);
}

void loop() {
  // reply only when you receive data:
  if (Serial.available() > 0) {
    readFromSerial();
  }
  
  // say what you got:
  if (finishedRx) {
    digitalWrite(LED_PIN, HIGH);
    Serial.print("I received: \"");
    Serial.print(serialInArray);
    Serial.println("\"");
    finishedRx = false;
    delay(2000);
    digitalWrite(LED_PIN, LOW);
  }

}

void readFromSerial() {
  int availableBytes = Serial.available();
  Serial.print("there are ");
  Serial.print(availableBytes);
  Serial.println(" available bytes.");
 
  if (availableBytes > 0) {
    int bytesToRead = availableBytes;

    // read in the bytes
    for (int i = 0; i < bytesToRead; i++) {
      int inByte = (int) Serial.read();

      if (inByte == NEWLINE) {
        finishedRx = true;
        serialInArray[serialInPointer] = 0; // NUL to end line for printing
        serialInPointer = 0; // start it back over for the next incoming message
      } else {
        finishedRx = false;
        serialInArray[serialInPointer] = inByte;
        serialInPointer++;
      }
    }

  }
}
