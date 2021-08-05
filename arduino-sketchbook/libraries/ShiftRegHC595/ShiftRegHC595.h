/* NjsolyUtil.h - Arduino Library for controlling a shift register,
 * or several daisy-chained together.
 *
 * by Nate Solyntjes
 * 2021-08-04
 */
#ifndef ShiftRegHC595_h
#define ShiftRegHC595_h

#include "Arduino.h"
#include "Stream.h"
#include "USBAPI.h"

class ShiftRegHC595 {
	public:
		ShiftRegHC595(uint8_t serPin,
			uint8_t srclkPin,
			uint8_t rclkPin,
			uint8_t dataLength,
			uint8_t srclrNotPin = -1,
			uint8_t oeNotPin = -1);
	private:
		uint8_t ser;
		uint8_t srclk;
		uint8_t rclk;
		uint8_t srclrNot;
		uint8_t outputDisable;
		// how many output values to track
		uint8_t dataLength;
};

#endif