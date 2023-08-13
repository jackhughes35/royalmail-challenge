# royalmail-challenge
Challenge for RoyalMail Interview Process

TODO: Decouple so the means of validating is easily changed from S10 to another validation method.
TODO: Build carries out unit test, pit test, int test. 
## Your Task
Build an API that will validate an S10 barcode. The expected deliverable should be Java-based with one endpoint (/validate). (Done)
The endpoint must take as input a single 1D barcode value (e.g. AA473124829GB) and return a boolean response. (Done)
Your service should validate the four parts of the barcode:
1.	Prefix in the range AA to ZZ (only uppercase alpha characters). (TODO)
2.	Eight digit serial number in the range 00 000 000 to 99 999 999 (only digits (0-9), but values could be left padded with zero’s, e.g. 00000001 = 1, etc.)
3.	Check digit as per the check digit validation described above.
4.	Country code is GB. We are only validating locally produced barcodes, anything is not recognised.
valid barcode example: AA473124829GB
invalid barcode example: AA473124828GB – incorrect check digit
Use your own discretion to decide how best to build and test the service. Document how to run the service locally and optionally any assumptions you made.

# Considerations:
- Logging:
  - Use of interceptors adds overhead, but would be useful when logging all request/responses. Particularly when paired with tracking Header. 
- Means of handling validation errors
  - Could have taken a few approaches. Chose not to use Spring/Javax Annotation driven validation, as these would throw exceptions whic would need to be caught in a ResponseExceptionHandlerAdvice and mapped to a 'false'
  - Could have separated out the 
  - As there is only one endpoint, currently a custom E

# Distributed/performance:
- Could have split the methods out in to Async ones, if any of the methods fail, the whole thread returns false. Make the barcode immutable object and this therefore is thread safe for a distributed system

# Assumptions:
- Assuming the front-end doesn't need to know WHY validation fails, just that it does. Therefore, no error message is propagated along with the 'false'
- HTTP400 Bad Request is not 
- There may be different Countries added
- There may be different means of validating barcodes, i.e. different algorithms used, other than the S10 one. 

# Steps to run:
- mvn clean install
- run dockerbuild to create image
- run pipeline to build container
- 