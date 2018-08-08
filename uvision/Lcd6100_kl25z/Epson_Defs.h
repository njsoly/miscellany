#ifndef EPSON_DEFS_H_
#define EPSON_DEFS_H_


//********************************************************************
//
//					EPSON Controller Definitions
//
//********************************************************************
#define DISON       0xAF	// Display on
#define DISOFF      0xAE	// Display off
#define DISNOR      0xA6	// Normal display
#define DISINV      0xA7	// Inverse display
#define SLPIN       0x95	// Sleep in
#define SLPOUT      0x94	// Sleep out
#define COMSCN      0xBB	// Common scan direction
#define DISCTL      0xCA	// Display control
#define PASET       0x75	// Page address set
#define CASET       0x15	// Column address set
#define DATCTL      0xBC	// Data scan direction, etc.
#define RGBSET8     0xCE	// 256-color position set
#define RAMWR       0x5C	// Writing to memory
#define RAMRD       0x5D	// Reading from memory
#define PTLIN       0xA8	// Partial display in
#define PTLOUT      0xA9	// Partial display out
#define RMWIN       0xE0	// Read and modify write
#define RMWOUT      0xEE	// End
#define ASCSET      0xAA	// Area scroll set
#define SCSTART     0xAB	// Scroll start set
#define OSCON       0xD1	// Internal oscillation on
#define OSCOFF      0xD2	// Internal osciallation off
#define PWRCTR      0x20	// Power control
#define VOLCTR      0x81	// Electronic volume control
#define VOLUP       0xD6	// Increment electronic control by 1
#define VOLDOWN     0xD7	// Decrement electronic control by 1
#define TMPGRD      0x82	// Temperature gradient set
#define EPCTIN      0xCD	// Control EEPROM
#define EPCOUT      0xCC	// Cancel EEPROM control
#define EPMWR       0xFC	// Write into EEPROM
#define EPMRD       0xFD	// Read from EEPROM
#define EPSRRD1     0x7C	// Read register 1
#define EPSRRD2     0x7D	// Read register 2
#define NOP         0x25	// No op

#endif
