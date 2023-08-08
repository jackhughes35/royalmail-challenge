# royalmail-challenge
Challenge for RoyalMail Interview Process


Your Task
Build an API that will validate an S10 barcode. The expected deliverable should be Java-based with one endpoint (/validate).
The endpoint must take as input a single 1D barcode value (e.g. AA473124829GB) and return a boolean response.
Your service should validate the four parts of the barcode:
1.	Prefix in the range AA to ZZ (only uppercase alpha characters).
2.	Eight digit serial number in the range 00 000 000 to 99 999 999 (only digits (0-9), but values could be left padded with zero’s, e.g. 00000001 = 1, etc.)
3.	Check digit as per the check digit validation described above.
4.	Country code is GB. We only validating locally produced barcodes, anything is not recognised.
valid barcode example: AA473124829GB
invalid barcode example: AA473124828GB – incorrect check digit
Use your own discretion to decide how best to build and test the service. Document how to run the service locally and optionally any assumptions you made.

