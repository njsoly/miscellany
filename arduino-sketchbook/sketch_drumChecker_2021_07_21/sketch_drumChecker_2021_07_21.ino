/* sketch_drumCheck_2021_07_21
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

  // if we have a complete message, process it and move on
    if (finishedRx) {
        finishedRx = false;
        processReceivedMessage(serialInArray);
    
        flashASecond();
    }

}

void processReceivedMessage (char message[]) {
    logReceivedMessage();

    logWhetherNumeric(message);

    if (isNumber(message)) {
      int drumStrobe = charsToInt(message);
      
    }
}

int charsToInt(char number[]) {
  int n = 0;
  int i = 0;
  for (i = 0; number[i] != 0; i++){
    n *= 10;
    n += number[i] - '0';
  }
  return n;
}

void logWhetherNumeric(char message[]) {
  bool isNumeric = isNumber(message);

  Serial.print("\""); Serial.print(message); Serial.print("\" is ");
  if (isNumeric == false) {
    Serial.print("NOT ");
  }
  Serial.print("a number.\n");
}

bool isNumber(char message[]) {
  char zero = '0', nine = '9';
  int i = 0;
  bool isNumeric = true;
  for (i = 0; message[i] != 0 && i <= SERIAL_MAX; i++) {
    if (message[i] < '0' || message[i] > '9') {
      return false;
    }
  }
  if (i == 0) {
    return false;
  }
  return isNumeric;
}

void readFromSerial() {
  int availableBytes = Serial.available();
 
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
} // readFromSerial

// print back to serial what message we received
void logReceivedMessage () {
    Serial.print("received message: \"");
    Serial.print(serialInArray); // should print correctly if \0 was correctly added
    Serial.println("\"");
}

void flashASecond () {
    digitalWrite(LED_PIN, HIGH);
    delay(1000);
    digitalWrite(LED_PIN, LOW);
}
