# api-gateway

[![Docker Image CI](https://github.com/creative-hub-taass/api-gateway/actions/workflows/docker-image.yml/badge.svg)](https://github.com/creative-hub-taass/api-gateway/actions/workflows/docker-image.yml)

API Gateway

## Linux / Mac (bash)

```shell
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --renew-anon-volumes --force-recreate --build
```

## Windows (Powershell)

```powershell
$env:COMPOSE_DOCKER_CLI_BUILD=1; $env:DOCKER_BUILDKIT=1; docker-compose up --renew-anon-volumes --force-recreate --build
```

## Kubernetes (local)

### Linux / Mac (bash)

```shell
minikube start
for f in ./orchestration/*.yaml; do cat $f | envsubst | kubectl apply -f -; done
minikube tunnel
```

### Windows (Powershell)

```powershell
minikube start
Resolve-Path .\orchestration\*.yaml | Select -ExpandProperty Path | %{Get-Content $_ | envsubst | kubectl apply -f -}
minikube tunnel
```

## Kubernetes (Okteto)

### Linux / Mac (bash)

```shell
okteto kubeconfig
for f in ./orchestration/*.yaml; do cat $f | envsubst | kubectl apply -f -; done
```

### Windows (Powershell)

```powershell
okteto kubeconfig
Resolve-Path .\orchestration\*.yaml | Select -ExpandProperty Path | %{Get-Content $_ | envsubst | kubectl apply -f -}
```
