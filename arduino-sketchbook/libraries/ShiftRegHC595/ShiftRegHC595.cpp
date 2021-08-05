#include "Arduino.h"
#include "Stream.h"
#include "USBAPI.h"

#include "ShiftRegHC595.h"

ShiftRegHC595::ShiftRegHC595(uint8_t serPin,
							 uint8_t srclkPin,
							 uint8_t rclkPin,
							 uint8_t dataLengthBits,
							 uint8_t srclrNotPin,
							 uint8_t oeNotPin) {
	ser = serPin;
	srclk = srclkPin;
	rclk = rclkPin;
	srclrNot = srclrNotPin;
	outputDisable = oeNotPin;
	dataLength = dataLengthBits;
}
