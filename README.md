How to build and run:
1. Clone the repository
2. Create a PostgreSQL database
3. Modify src\main\resources\application.properties based on your PostgreSQL database settings
4. Go to the root folder of the cloned repository in the Command Prompt
5. Run "mvnw clean install -Dmaven.test.skip=true" command
6. Go to folder called "target"
7. Run "java -jar BankSystem-0.0.1-SNAPSHOT.jar" command

Endpoints:
POST /transaction/import
- "file":yourAttachedFile.csv

GET /transaction/export
- "from":"2022-03-01" (OPTIONAL)
- "to":"2022-03-05" (OPTIONAL)

GET /transaction/getBalance
- "accountNumber":"someString"
- "from":"2022-03-01" (OPTIONAL)
- "to":"2022-03-05" (OPTIONAL)

Use "Postman" or some other application to access these endpoints.

CSV File Structure:
| accountNumber | amount        |operation date   |currency       |comment        |
| ------------- |:-------------:| ---------------:| ------------- |:-------------:|
