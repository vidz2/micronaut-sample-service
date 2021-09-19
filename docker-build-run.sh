#!/bin/zsh
if [[ "$(docker images -q app/user-register:latest 2> /dev/null)" == "" ]]; then
  docker build -t app/user-register:latest .
fi
if [[ "$(docker ps -f name=user-register | grep -w user-register)" != "" ]]; then
  echo "stopping container..."
  docker stop user-register
fi
echo "starting container..."
docker run --rm -p 8080:8080 -v $HOME/tmp/electricdb:/db -e JDBC_URL=jdbc:h2:file:/db --name user-register app/user-register:latest
