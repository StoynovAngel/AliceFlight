# Feature: 
Get all routes by coordination allows Alice to find all flights that she can get on.
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
<p><b>maxFlights</b> its optional parameter</p>

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

## Test case 7:
### Scenario:
Find flights from two points. Example SOF to MLE.
### Given: The available flights (above):
### When: Alice sends an INCORRECT POST request which does not contain "destination"
```json
{
"origin": "SOF"
}
```
### Then: The response is:
<li>Error: response status is 404</li>
<li>No routes</li>

### And: Alice receives no routes.

## Test case 7:
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
    "route": ["SOF", "MLE"],
    "price": 70
  }
]
```
### And: Alice receives ALL available 1 route flights (In our case just one).

