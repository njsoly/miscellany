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
	_ser = serPin;
	_srclk = srclkPin;
	_rclk = rclkPin;
	_srclrNot = srclrNotPin;
	_outputDisable = oeNotPin;
	_dataLength = dataLengthBits;
}

void ShiftRegHC595::initPins() {
	pinMode(_srclk, OUTPUT);
	digitalWrite(_srclk, LOW);
	pinMode(_rclk, OUTPUT);
	digitalWrite(_rclk, LOW);
	pinMode(_ser, OUTPUT);
	digitalWrite(_ser, LOW);

	if (_outputDisable != NOT_A_PIN) {
		pinMode(_outputDisable, OUTPUT);
		digitalWrite(_outputDisable, HIGH);
		digitalWrite(_outputDisable, LOW);
	}

	if (_srclrNot != NOT_A_PIN) {
		pinMode(_srclrNot, OUTPUT);
		digitalWrite(_srclrNot, LOW);
		digitalWrite(_srclrNot, HIGH);
	}
}

void ShiftRegHC595::shiftOut() {
	shiftOut(_data);
}

void ShiftRegHC595::shiftOut(uint8_t data[]) {
	shiftOut(_data, _dataLength);
}

void ShiftRegHC595::shiftOut(uint8_t data[], uint16_t dataLength) {
	if (_outputDisable != NOT_A_PIN) {
		digitalWrite(_outputDisable, HIGH);
	}
	digitalWrite(_rclk, LOW);

    for (uint8_t i = 0; i < _dataLength; i++) {
		digitalWrite(_srclk, LOW);
		digitalWrite(_ser, _data[i]);
		digitalWrite(_srclk, HIGH);
	}

	digitalWrite(_rclk, HIGH);
	if (_outputDisable != NOT_A_PIN) {
		digitalWrite(_outputDisable, LOW);
	}
}

void ShiftRegHC595::clearRegister() {
	for (uint8_t i = 0; i < _dataLength; i++) {
		_data[i] = 0;
	}

	// clear with the SRCLR! input, if configured.
	// otherwise, clear by pushing out all zeros.
	if (_srclk != NOT_A_PIN) {
		digitalWrite(_srclrNot, LOW);
		digitalWrite(_srclrNot, HIGH);
	} else {
		shiftOut(_data);
	}

}