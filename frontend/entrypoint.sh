#!/bin/sh
echo "Backend'in hazır olmasını bekliyorum..."

while ! nc -z backend 8080; do
  sleep 2
  echo "Backend hala çalışmıyor, tekrar deniyorum..."
done

echo "Backend çalışıyor, frontend başlatılıyor..."
exec "$@"
