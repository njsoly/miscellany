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

#include "NjsolyUtil.h"

class ShiftRegHC595 {
	public:
		ShiftRegHC595(
			uint8_t serPin,
			uint8_t rclkPin,
			uint8_t srclkPin,
			uint8_t dataLength,
			Stream &stream,
			uint8_t srclrNotPin = NOT_A_PIN,
			uint8_t oeNotPin = NOT_A_PIN
		);
		ShiftRegHC595(
			uint8_t serPin,
			uint8_t rclkPin,
			uint8_t srclkPin,
			uint8_t dataLength,
			uint8_t srclrNotPin = NOT_A_PIN,
			uint8_t oeNotPin = NOT_A_PIN
		);
		// pin and data setup
		void init();
		void shiftOut(uint8_t value);
		void shiftOut(uint8_t data[], uint16_t dataLength);
		void clearRegister();
	private:
		uint8_t _ser;
		uint8_t _rclk;
		uint8_t _srclk;
		Stream* _stream;
		uint8_t _srclrNot;
		uint8_t _outputDisable;
		// how many output values to track
		uint8_t _dataLength;
};




#endif /* ShiftRegHC595_h */
