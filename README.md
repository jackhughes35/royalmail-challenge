# royalmail-challenge
Challenge for RoyalMail Interview Process

# Considerations:
- Logging:
  - Use of interceptors adds overhead, but would be useful when logging all request/responses. Particularly when paired with tracking Header. 
- Means of handling validation errors
  - Could have taken a few approaches. Chose not to use Spring/Javax Annotation driven validation, as these might cause the solution to be closely coupled to a data model
- Could have improved with the use ofSpring cloud config to externalise configuration. 

# Distributed/performance:
- Could have split the methods out in to Async ones, if any of the methods fail, the whole thread returns false. Make the barcode immutable object and this therefore is thread safe for a distributed system

# Assumptions:
- Assuming the front-end doesn't need to know WHY validation fails, just that it does. Therefore, no error message is propagated along with the 'false'
- HTTP400 Bad Request is not returned with the boolean. This is business needs. Perhaps malformed barcode may in future return a HTTP400
- There may be different Countries added
- There may be different means of validating barcodes, i.e. different algorithms used, other than the S10 one. 

# Steps to run:
- mvn clean install
- run dockerbuild to create image
- run pipeline to build container
- Locally can simply run a build to create an executable jar file. can also change the packaging in the pom from 'jar' to 'war' so the war file can be deployed on a shared tomcat container. 