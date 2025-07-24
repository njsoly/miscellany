/*
  Rui Santos & Sara Santos - Random Nerd Tutorials
  Complete project details at https://RandomNerdTutorials.com/esp-now-two-way-communication-esp32/
  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files.
  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/
/**
  njsoly: 
    This file takes the extras out, so that only the ESP-NOW communication remains.
    master device is the Frazzler remote control, Nano-ESP32 [1]
          slave is [0], the central device itself.
    MACs:
      [0]: 3c:84:27:c3:e4:a4
      [1]: 3c:84:27:c7:0e:10

  This side is for the main device, nano-esp32 [0].
*/

#include <esp_now.h>
#include <WiFi.h>
#include <FrazzlerEspNow.h>

// REPLACE WITH THE MAC Address of your receiver 
uint8_t broadcastAddress[] = {0x3c, 0x84, 0x27, 0xc7, 0x0e, 0x10};

// Variable to store if sending data was successful
String success;

// Create a struct_message called outgoingStruct to hold sensor readings
struct_message outgoingStruct;

// Create a struct_message to hold incoming sensor readings
struct_message incomingStruct;

esp_now_peer_info_t peerInfo;

// Callback when data is sent
void OnDataSent(const uint8_t *mac_addr, esp_now_send_status_t status) {
  Serial.print(F("\r\nLast Packet Send Status:\t"));
  Serial.println(status == ESP_NOW_SEND_SUCCESS ? "Delivery Success" : "Delivery Fail");
  if (status == 0){
    success = "Delivery Success :)";
  }
  else{
    success = "Delivery Fail :(";
  }
}

// Callback when data is received
void OnDataRecv(const uint8_t * mac, const uint8_t *incomingData, int len) {
  memcpy(&incomingStruct, incomingData, sizeof(incomingStruct));
  Serial.print(F("Bytes received: "));
  Serial.println(len);

  Serial.print(F("message received: "));
  Serial.println(incomingStruct.msg);
}

void setup() {
  // Init Serial Monitor
  Serial.begin(SERIAL_BAUD);
  while(!Serial) {} // wait for Serial
 
  // Set device as a Wi-Fi Station
  WiFi.mode(WIFI_STA);

  // Init ESP-NOW
  if (esp_now_init() != ESP_OK) {
    Serial.println(F("Error initializing ESP-NOW"));
    return;
  }

  // Once ESPNow is successfully Init, we will register for Send CB to
  // get the status of Transmitted packet
  esp_now_register_send_cb(OnDataSent);
  
  // Register peer
  memcpy(peerInfo.peer_addr, broadcastAddress, 6);
  peerInfo.channel = 0;  
  peerInfo.encrypt = false;
  
  // Add peer        
  if (esp_now_add_peer(&peerInfo) != ESP_OK){
    Serial.println(F("Failed to add peer"));
    return;
  }
  // Register for a callback function that will be called when data is received
  esp_now_register_recv_cb(esp_now_recv_cb_t(OnDataRecv));
}

void loop() {
  outgoingStruct.msg[0] = '\0';

  // Send message via ESP-NOW
  outgoingStruct.count++;
  strcat(outgoingStruct.msg, "frazzler "); 
  char countStr[6];
  itoa(outgoingStruct.count, countStr, 10); 
  strcat(outgoingStruct.msg, countStr);
  esp_err_t result = esp_now_send(broadcastAddress, (uint8_t *) &outgofingStruct, sizeof(outgoingStruct));
   
  if (result == ESP_OK) {
    Serial.println(F("Sent with success"));
  }
  else {
    Serial.println(F("Error sending the data"));
  }
  delay(10000);
}

