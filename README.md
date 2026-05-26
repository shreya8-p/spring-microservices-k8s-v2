# Spring Boot Microservices on Kubernetes

A production-grade microservices architecture built with Spring Boot, deployed on Kubernetes (Kind), with full CI/CD pipeline, distributed tracing, and DevSecOps practices.

## Architecture
Client ? API Gateway (8080) ? Organization Service (8081) ? MongoDB
? Department Service (8082)  ? MongoDB
? Employee Service (8083)    ? MongoDB

## Services

| Service | Port | Description |
|---|---|---|
| gateway | 8080 | API Gateway — routes requests to services |
| organization | 8081 | Manages organizations |
| department | 8082 | Manages departments |
| employee | 8083 | Manages employees, calls department via @HttpExchange |

## Tech Stack

- **Java 17** + **Spring Boot 3.5**
- **Spring Cloud Kubernetes** — native K8s service discovery
- **Spring Cloud Gateway** — API routing
- **@HttpExchange** + **Spring Cloud LoadBalancer** — inter-service REST communication
- **MongoDB** — database per service pattern
- **Micrometer + OpenTelemetry + Jaeger** — distributed tracing
- **Kubernetes (Kind)** — local K8s cluster
- **Docker** — multi-arch images (amd64 + arm64)
- **GitHub Actions** — CI/CD pipeline
- **Trivy** — CVE scanning
- **cosign** — keyless image signing
- **SBOM** — software bill of materials
- **SLSA provenance** — supply chain security
- **Testcontainers** — integration tests with real MongoDB
- **OpenAPI/Swagger** — API documentation

## CI/CD Pipeline

The GitHub Actions pipeline runs on every push to main:

1. Build JARs for all 4 services
2. Build and push multi-arch Docker images (amd64 + arm64) to Docker Hub
3. Trivy CVE scanning
4. cosign keyless signing
5. SBOM generation
6. SLSA provenance attestation

## Running Locally

### Prerequisites
- Docker Desktop
- Kind
- kubectl
- Java 17
- Maven

### Start the cluster
```bash
kind create cluster --name microservices
```

### Build and deploy
```bash
# Build JARs
mvn package -DskipTests -f organization/pom.xml
mvn package -DskipTests -f department/pom.xml
mvn package -DskipTests -f employee/pom.xml
mvn package -DskipTests -f gateway/pom.xml

# Build Docker images
docker build -t organization:latest ./organization
docker build -t department:latest ./department
docker build -t employee:latest ./employee
docker build -t gateway:latest ./gateway

# Load into Kind
kind load docker-image organization:latest --name microservices
kind load docker-image department:latest --name microservices
kind load docker-image employee:latest --name microservices
kind load docker-image gateway:latest --name microservices

# Deploy
kubectl apply -f k8s/
```

### Test the API
```bash
kubectl port-forward service/gateway 9191:8080

# Create organization
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Tech Corp","address":"Mumbai"}' \
  http://localhost:9191/organization

# Get all organizations
curl http://localhost:9191/organization

# Get employee with department (inter-service communication)
curl http://localhost:9191/employee/{id}/with-department
```

### View distributed traces
```bash
kubectl port-forward service/jaeger 16686:16686
# Open http://localhost:16686
```

### View API documentation
```bash
kubectl port-forward service/organization 8081:8081
# Open http://localhost:8081/swagger-ui/index.html
```

## Docker Hub

Images available at:
- `patilshreya08/organization:latest`
- `patilshreya08/department:latest`
- `patilshreya08/employee:latest`
- `patilshreya08/gateway:latest`
