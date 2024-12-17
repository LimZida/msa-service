# msa-gateway-server
gateway server

# circuit breaker url
http://localhost:9998/actuator/health/circuitBreakers
```
현황 확인 예시

{
  "status": "UP",
  "details": {
    "apiServiceCircuitBreaker": {
      "status": "CIRCUIT_OPEN",
      "details": {
        "failureRate": "80.0%",
        "failureRateThreshold": "50.0%",
        "slowCallRate": "0.0%",
        "slowCallRateThreshold": "100.0%",
        "bufferedCalls": 10,
        "slowCalls": 0,
        "slowFailedCalls": 0,
        "failedCalls": 8,
        "notPermittedCalls": 2,
        "state": "OPEN"
      }
    },
    "chatServiceCircuitBreaker": {
      "status": "UP",
      "details": {
        "failureRate": "-1.0%",
        "failureRateThreshold": "50.0%",
        "slowCallRate": "-1.0%",
        "slowCallRateThreshold": "100.0%",
        "bufferedCalls": 0,
        "slowCalls": 0,
        "slowFailedCalls": 0,
        "failedCalls": 0,
        "notPermittedCalls": 0,
        "state": "CLOSED"
      }
    }
  }
}
```
