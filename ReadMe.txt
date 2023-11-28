Steps to execute:
Steps:
1. git clone the repo and execute: cd  > npm install.
    - It will install dependencies stored in package.json file.

2. All secrets are stored at "/testing_api/testData/envSec.json".
    - Get this file from your buddy and create it to required path.

3. Start the test suites: npx mocha testing_api/Growth_POD/login_spec.js


# FanCode Assignment

# Description
Scenario :- All the users of City `FanCode` should have more than half of their todos task completed.
Given User has the todo tasks
And User belongs to the city FanCode
Then User Completed task percentage should be greater than 50%

# Prerequisites
- Java
- Maven
- Testng
- RestAssured

## Getting Started

Provide instructions on how to get a copy of your project and how to install the necessary dependencies.

### Building the Project

```bash
mvn clean install

