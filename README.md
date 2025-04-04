# Tech Stack:
<li>Java SDK 23</li>
<li>Spring Boot</li>

# Maven Packages:
`backend/pom.xml`

# Installation:
No steps are required.

# Swagger:
### You can access the API here on:
Start the server and go to this url: http://localhost:8080/swagger-ui/index.html#

# Feature: 
<p>
    Alice want to book a flight. She does it by specifying origin and destination. It allows Alice to find all flights that she can get on. 
    She will get all possible routes sorted in ascending order by price.
</p>

# Rules:
<li>Prices must be positive numbers and constant.</li>
<li>It must include starting position.</li>
<li>It must include destination.</li>
<li>It is one-way flight.</li>
<li>A route cannot contain the same city twice or more times.</li>

# Schema:
### Example:
```json
{
"origin": "SOF",
"destination": "MLE",
"maxFlights": 1
}
```

#### maxFlights its optional parameter. It specifies the number of layovers.
#### Example: maxFlights = 1 means direct. maxFlights = 2 would be 1 layover.</p>

### Example without maxFlights:
```json
{
"origin": "SOF",
"destination": "MLE"
}
```

# Quality Assurance(QA)
## Flights used in the task:
```json
{
  "flights": [
    { "from": "SOF", "to": "MLE", "price": 70 },
    { "from": "SOF", "to": "LON", "price": 10 },
    { "from": "LON", "to": "MLE", "price": 20 },
    { "from": "SOF", "to": "PAZ", "price": 30 },
    { "from": "PAZ", "to": "VIT", "price": 50 },
    { "from": "VIT", "to": "MLE", "price": 20 }
  ]
}
```
## Test case 1:
### Scenario:
Find a direct flight from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends a POST request with maxFlights:
```json
{
  "origin": "SOF",
  "destination": "MLE",
  "maxFlights": 1
}
```
### Then: The response is:
```json
[
  {
    "route": ["SOF", "MLE"],
    "price": 70
  }
]
```
### And: Alice receives only 1 route.

## Test case 2:
### Scenario:
Find a direct flight from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends a POST request WITHOUT maxFlights:
```json
{
  "origin": "SOF",
  "destination": "MLE"
}
```
### Then: The response is:
```json
[
  {
    "route": [
      "SOF",
      "LON",
      "MLE"
    ],
    "price": 30
  },
  {
    "route": [
      "SOF",
      "MLE"
    ],
    "price": 70
  },
  {
    "route": [
      "SOF",
      "PAZ",
      "VIT",
      "MLE"
    ],
    "price": 100
  }
]
```
### And: Alice receives ALL available flights.

## Test case 3:
### Scenario:
Find a direct flight from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends an POST request which looks for a destination that it is not included:
```json
{
"origin": "SOF",
"destination": "HAI",
"maxFlights": 1
}
```
### Then: The response is:
<li>Error: response status is 404</li>
<li>Response body: No routes</li>

### And: Alice receives no routes.

## Test case 4:
### Scenario:
Find a direct flight from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends an INCORRECT POST request which has maxFlights = 0:
```json
{
"origin": "SOF",
"destination": "MLE",
"maxFlights": 0
}
```
### Then: The response is:
<li>Error: response status is 400</li>
<li>Response body: Max flight should be at least 1</li>

### And: Alice receives no routes.

## Test case 5:
### Scenario:
Find flights from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends an POST request which has maxFlights = 2:
```json
{
"origin": "SOF",
"destination": "MLE",
"maxFlights": 2
}
```
### Then: The response is:
```json
[
  {
    "route": [
      "SOF",
      "LON",
      "MLE"
    ],
    "price": 30
  },
  {
    "route": [
      "SOF",
      "MLE"
    ],
    "price": 70
  }
]
```

### And: Alice receives all routes that are direct or have only 1 layover.

## Test case 6:
### Scenario:
Find flights from two points. Example SOF to MLE.
### Given: Flights are NOT loaded correctly:
### When: Alice sends an POST request
```json
{
"origin": "SOF",
"destination": "MLE"
}
```
### Then: The response is:
<li>Error: response status is 500</li>
<li>Cannot read json file. Message: File does not exist.</li>

### And: Alice receives no routes.

## Test case 7-8:
### Scenario:
Find flights from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends an INCORRECT POST request which does not contain "destination" or "origin"
```json
{
"origin": "SOF"
}
```
### Then: The response is:
<li>Error: response status is 404</li>
<li>No routes</li>

### And: Alice receives no routes.

## Test case 9:
### Scenario:
Find a direct flight from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends a INCORRECT POST request containing the same city:
```json
{
"origin": "SOF",
"destination": "SOF"
}
```
### Then: The response is:

<li>Error: response status is 400</li>
<li>Cannot have the same origin as a destination: SOF SOF</li>

### And: Receives no routes

## Test case 10:
### Scenario:
Find a direct flight from two points. Example PRO to 2R3.
### Given: The available flights (above):
### When: Alice sends a INCORRECT POST request containing digits or any other special character:
```json
{
"origin": "PRO",
"destination": "#R3"
}
```
### Then: The response is:

<li>Error: response status is 400</li>
<li>Origin and destination must contain only UPPERCASE alphabetical characters and be 3 characters long.</li>

### And: Receives no routes

## Test case 11:
### Scenario:
Find a direct flight from two points. Example SOFIA to MIAMI.
### Given: The available flights (above):
### When: Alice sends a INCORRECT POST request containing origin or destination longer than 3 characters:
```json
{
"origin": "SOFIA",
"destination": "MIAMI"
}
```
### Then: The response is:

<li>Error: response status is 400</li>
<li>Origin and destination must contain only UPPERCASE alphabetical characters and be 3 characters long.</li>

### And: Receives no routes

## Test case 12:
### Scenario:
Find a direct flight from two points. Example sof to mle.
### Given: The available flights (above):
### When: Alice sends a INCORRECT POST request containing origin or destination longer than 3 characters:
```json
{
"origin": "sof",
"destination": "mle"
}
```
### Then: The response is:

<li>Error: response status is 400</li>
<li>Origin and destination must contain only UPPERCASE alphabetical characters and be 3 characters long.</li>

### And: Receives no routes

## Test case 13:
### Scenario:
Find a direct flight from two points. Example "" to "".
### Given: The available flights (above):
### When: Alice sends a INCORRECT POST request containing empty parameters: origin or destination
```json
{
"origin": "",
"destination": ""
}
```
### Then: The response is:

<li>Error: response status is 400</li>
<li>Origin or destination cannot be empty.</li>

### And: Receives no routes

## Test case 14:
### Scenario:
Find a direct flight from two points. Example null to null.
### Given: The available flights (above):
### When: Alice sends a INCORRECT POST request containing NULL parameters: origin or destination
```json
{
}
```
### Then: The response is:

<li>Error: response status is 400</li>
<li>Origin or destination cannot be empty.</li>

### And: Receives no routes
